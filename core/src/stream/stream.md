# 1
Stream 
支持串行或者并行聚合操作的一系列元素的集合，就是流。是数据操作的源头

StreamShape
Stream的类型，每一个枚举值对应一个BaseStream的子类

TerminalOp

Sink
译过来表示水槽下沉的意思，继承Consumer接口

TerminalSink 
Sink的子接口，同时由继承了Supplier接口，也就是既可以消费数据，又可以产生结果

AbstractPipeline 
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

是否是Sink的的编排？ 数据流 流经pipeline，然后结束？？？


Spliterator



