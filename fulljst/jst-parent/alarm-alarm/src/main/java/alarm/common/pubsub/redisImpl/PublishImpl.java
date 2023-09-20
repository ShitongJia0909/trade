package alarm.common.pubsub.redisImpl;

import alarm.common.pubsub.IPublish;
import alarm.common.pubsub.IPublishMessage;
import alarm.common.pubsub.ISubscribe;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PublishImpl implements IPublish {

    private static Logger logger = LoggerFactory.getLogger(PublishImpl.class);

    @Resource
    RedissonClient redissonClient;

    @Resource
    ISubscribe subscribe;

    @Value(value = "${alarm.setting.mode:prod}")
    String mode;

    public void init(){
        postSwitch(this, "IPublish", redissonClient);
    }


    @Override
    public void publishMessage(IPublishMessage subject) {
        if("debug".equals(mode)){
            publishLocalMessage(subject);
            return;
        }

        try{
            RTopic topic = redissonClient.getTopic(PUBLISH_TOPIC);
            topic.publish(subject);
        }catch (Exception e){
            logger.warn("Redis, failed to publish message, publish local message");
            publishLocalMessage(subject);
        }
    }

    @Override
    public void publishLocalMessage(IPublishMessage subject) {
        subscribe.subscribeMessage(subject);
    }

    public void preSwitch(Object bean, String beanName, RedissonClient oldClient){
        if(this == bean && redissonClient == oldClient){
            logger.info("Redis, remove listen for publish and subscribe");
            RTopic topic = oldClient.getTopic(IPublish.PUBLISH_TOPIC);
            topic.removeAllListeners();
        }
    }

    public void postSwitch(Object bean, String beanName, RedissonClient newClient){
        if(this == bean && redissonClient == newClient){
            logger.info("Redis, and listener for publish and subscribe");
            RTopic topic = redissonClient.getTopic(IPublish.PUBLISH_TOPIC);
            topic.addListener(IPublishMessage.class, (charSequence, iPublishMessage) ->
            {subscribe.subscribeMessage(iPublishMessage);
            });
        }
    }

}
