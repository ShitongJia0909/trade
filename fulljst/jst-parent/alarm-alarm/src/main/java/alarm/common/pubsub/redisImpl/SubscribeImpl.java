package alarm.common.pubsub.redisImpl;

import alarm.common.exception.NormalTerminateException;
import alarm.common.pubsub.IMessageConsumer;
import alarm.common.pubsub.IPublishMessage;
import alarm.common.pubsub.ISubscribe;
import com.jst.common.thread.ThreadPoolMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class SubscribeImpl implements ISubscribe, ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(SubscribeImpl.class);

    private ApplicationContext applicationContext;

    private List<IMessageConsumer> consumerList;

    @Resource
    ThreadPoolMgr threadPoolMgr;


    @Override
    public void subscribeMessage(IPublishMessage message) {
        logger.debug("subscribeMessage={}", message);
        for(IMessageConsumer consumer : consumerList){
            if(consumer.apply(message)){
                threadPoolMgr.submit(() ->{
                    try{
                        consumer.handleMessage(message);
                    }catch (NormalTerminateException | DataIntegrityViolationException  e){
                        logger.warn(e.getMessage());
                    }catch (Exception e){
                        logger.error("subscribe message has an error. message={}", message, e);
                    }
                });
                break;
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init(){
        consumerList = new ArrayList<>();
        String[] beanNames = applicationContext.getBeanNamesForType(IMessageConsumer.class);
        logger.info("SubscribeImpl load IMessageConsumers, beanNames = {}", beanNames);
        for(String beanName : beanNames){
            consumerList.add(applicationContext.getBean(beanName, IMessageConsumer.class));
        }
    }




}
