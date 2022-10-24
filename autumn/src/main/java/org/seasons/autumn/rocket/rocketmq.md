

nameserver先启动，broker向nameserver注册，同一个namespace下的broker组成一个集群，主broker和从broker之间根据broker的id来进行识别。



生产 

同步发送（异步转同步） 异步发送 单向发送

事务消息



消费 

pull模式 
主动拉取消息进行消费，RocketMQ内部处理了再平衡，只是拉取的控制权在开发者手中，可以主动选择某一个或者多个队列进行消费，消费后要进行ACK，
如果遇到消费组的重平衡，需要考虑消息是否失效。
旧的Pull API标识为废弃，太过复杂，新的Pull API在使用上更加简单些


push模式
基于pull模式，RocketMQ负责消息的拉取，拉取后回调开发者编写的回调逻辑，消费结果ACK，重平衡后的无效消息处理 都是由RocketMQ内部在负责维护。
推荐使用Push模式，使用比较简单方便省心