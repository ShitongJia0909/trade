package alarm.common.delay;

import alarm.common.pubsub.IPublish;
import alarm.common.pubsub.IPublishMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@Component
public class DelayTaskMgr {

    Logger logger = LoggerFactory.getLogger(DelayTaskMgr.class);

    @Resource
    IPublish publish;

    private LinkedList<Map.Entry<Long, IPublishMessage>> messageList;

    private class DelayTaskDistributor extends TimerTask {

        @Override
        public void run() {
            try{
                checkAndRun();
            }catch (Exception e){
                logger.error("TaskDistributor has an inner error.", e);
            }
        }
    }

    @PostConstruct
    public void init() {
        messageList = new LinkedList<>();
        Timer flushTimer = new Timer("Delay Task Manager", true);
        flushTimer.schedule(new DelayTaskDistributor(), 20000, 1000);
        logger.info("Delay Task Manager start ...");
    }

    public synchronized void add(Long executeTime, IPublishMessage message){
        messageList.add(new AbstractMap.SimpleEntry<>(executeTime, message));
    }

    public synchronized void checkAndRun(){
        long current = System.currentTimeMillis();
        Iterator<Map.Entry<Long, IPublishMessage>> iter = messageList.iterator();
        while(iter.hasNext()){
            Map.Entry<Long, IPublishMessage> entry = iter.next();
            if(entry.getKey() <= current){
                publish.publishLocalMessage(entry.getValue());
                iter.remove();
            }
        }
    }




}
