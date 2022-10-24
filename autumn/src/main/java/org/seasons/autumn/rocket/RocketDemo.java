package org.seasons.autumn.rocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class RocketDemo {

    public static void main(String[] args) throws MQClientException {

        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setNamesrvAddr("");
        Message message = new Message();
        message.setTopic("topic-a");
        message.setBody("abc".getBytes(StandardCharsets.UTF_8));

        try {
            defaultMQProducer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
        }

        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
        defaultMQPushConsumer.setNamesrvAddr("");
        defaultMQPushConsumer.subscribe("topic-a", "*");
        defaultMQPushConsumer.setMessageListener((MessageListenerOrderly) (list, consumeOrderlyContext) -> null);


        DefaultLitePullConsumer defaultLitePullConsumer = new DefaultLitePullConsumer();
        defaultLitePullConsumer.setNamesrvAddr("");
        try {
            defaultLitePullConsumer.subscribe("topic-a", "*");
            List<MessageExt> pollMsgList;

            while (true) {
                pollMsgList = defaultLitePullConsumer.poll();

                if (CollectionUtils.isEmpty(pollMsgList)) {
                    continue;
                }

                for (MessageExt messageExt : pollMsgList) {
                    String bodyStr = new String(messageExt.getBody(), StandardCharsets.UTF_8);
                    JSONObject object = JSON.parseObject(bodyStr);
                    //
                }
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

}
