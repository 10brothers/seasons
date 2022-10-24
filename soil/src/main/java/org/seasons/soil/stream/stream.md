# 关键接口

##Stream 
支持串行或者并行聚合操作的一系列元素的集合，就是流。是数据操作的源头

##StreamShape
Stream的类型，每一个枚举值对应一个BaseStream的子类

##Sink
译过来表示水槽下沉的意思，继承Consumer接口

##TerminalSink 
Sink的子接口，同时由继承了Supplier接口，也就是既可以消费数据，又可以产生结果。TerminalSink是一个流操作中最后一个Sink

## AbstractPipeline 
Stream接口的核心实现
直接子类有ReferencePipeline IntPipeline LongPipeline DoublePipeline

Pipeline描述的Stream的不同阶段，每一次Stream上的调用都会产生一个新的Pipeline，
新的Pipeline通过方法opWrapSink将此阶段操作包装成Sink。
所有的Pipeline通过previousStage字段来形成一个单向链表

Pipeline就是用于描述一个流整个生命周期中，每个阶段的状态以及操作是什么，通过Pipeline预定义好并最终串联起来。
而串行和并行的含义是，整个流里的元素，是可以并行操作还是只能串行操作。

重点以ReferencePipeline来理解

ReferencePipeline有三个子类
Head
Head用于描述Stream的最初状态，没有任何操作，是一个纯SourceStage，这个阶段仅支持foreach操作。

StatelessOp
描述流的无状态的中间阶段

StatefulOp
描述流的有状态中间阶段，有状态阶段需要重写opEvaluateParallel方法

每进入一个阶段（Stream上的方法调用，中间态的方法调用），都会生成一个新的Stream实例，而新的Stream实例持有上游的Stream，也就是ReferencePipeline
同时每个阶段都需要重写 opWrapSink方法，这个方法会将具体这个阶段的操作，封装成普通的Sink类，如果是一个终止类型的方法调用，那么会包装成TerminalSink
也有可能是一个普通的Sink，取决于终止方法是否需要有返回值。

中间态的Sink子类实现主要是Sink.ChainedReference Sink.ChainedInt Sink.ChainedLong Sink.ChainedDouble

ChainedReference实例中会保存下一个阶段的downstream（Sink），然后在本阶段的Sink执行后，根据执行到的结果决定是否调用下一个Sink


## Spliterator
流的数据源，

## TerminalOp
所有的流只有终止动作才会触发具体的数据流动，没有TerminalOp不会触发实际的数据流动。一般TerminalOp的实现类也需要继承TerminalSink接口


# 示例流程
```java
    Stream<String> stringStream = Stream.of("1", "2", "3");
    Stream<String> filterStream = stringStream.filter(a -> a.equals("2"));
    Stream<Integer> mapStream = filterStream.map(Integer::valueOf);
    long count = mapStream.count();
```

流刚创建时，类型为你ReferencePipeline.Head 仅仅引用Spliterator实例

调用filter后，在filter方法 创建一个 ReferencePipeline的子类实例StatelessOp，
使用上一个Stream实例作为构造参数保存在previousStage字段中，将上一个阶段的sourceStage作为本阶段的sourceStage保存下来，
同时会将此ReferencePipeline实例作为previousStage的nextStage保存下来（这样就形成了双向链表）
新创建的ReferencePipeline实例重写了opWrapSink方法，在opWrapSink方法中，有两个入参flag和Sink，方法体创建一个匿名的Sink.ChainedReference实例，以Sink作为构造参数
ChainedReference的匿名类中，重写了begin和accept方法，accept方法是关键，accept方法中会先根据filter的入参Predicate来判断是否需要执行下一个downstream。

filter方法返回后，调用map方法，整体同filter的描述，区别在于，accept方法中直接调用了downstream.accept，只不过每一个输出需要经过map方法的入参Function去处理下，Function的出参才能作为downstream.accept的入参。

map方法返回后，调用count方法，count方法是用于计数的，内部先调用了mapToLong，mapToLong返回一个LongPipeline.StatelessOp，内部重写的accept方法，在调用downstream.accept时，入参是ToLongFunction的出参。
mapToLong的目的是为了将ReferencePipeline转换成LongPipeline，方便调用LongPipeline的聚合函数 sum

sum函数，sum函数是一个流的终止调用，sum函数中调用reduce方法，这个方法会传入方法引用Long::sum，通过ReduceOps.makeLong方法，返回一个TerminalOp，具体实现为ReducingSink，ReducingSink是一个AccumulatorSink。
调用AbstractPipeline中的evaluate方法，传入TerminalOp实例，evaluate方法中判断是并行执行还是串行执行，并行执行的话需要特定的并行Spliterator来支持，需要做一个转换。
最后通过TerminalOp的evaluateSequential来处理，入参为PipelineHelper和Spliterator，之后通过PipelineHelper的wrapAndCopyInto来先处理Sink的链。
依此从最后一个AbstractPipeline开始，也就是map返回的Stream，逆向调用opWrapSink方法，在调用开始时的Sink作为接下来Sink的downstream，这样最终返回的Sink实例就是filter方法调用中返回的ReferencePipeline.StatelessOp匿名类所重写的Sink.ChainedReference实例
这样最终就形成了一条Sink调用链，链的结尾是ReducingSink，Spliterator中的元素逐个依此通过Sink链，根据TerminalOp中的Sink类型，来决定最终的行为。




