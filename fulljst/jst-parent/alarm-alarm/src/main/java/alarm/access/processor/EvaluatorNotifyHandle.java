package alarm.access.processor;

import alarm.common.delay.DelayTaskMgr;
import alarm.common.pubsub.IMessageConsumer;
import alarm.common.pubsub.IPublishMessage;
import alarm.common.pubsub.msgImpl.EvaluatorExpMessage;
import alarm.common.pubsub.msgImpl.EvaluatorNotifyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EvaluatorNotifyHandle implements IMessageConsumer {

    Logger logger = LoggerFactory.getLogger(EvaluatorNotifyHandle.class);

    @Resource
    DelayTaskMgr delayTaskMgr;

    @Override
    public boolean apply(IPublishMessage message) {
        return message instanceof EvaluatorNotifyMessage;
    }

    @Override
    public void handleMessage(IPublishMessage message) throws Exception {
        EvaluatorNotifyMessage message1 = (EvaluatorNotifyMessage) message;
        EvaluatorExpMessage evaluatorExpMessage = new EvaluatorExpMessage(message1);
        delayTaskMgr.add(evaluatorExpMessage.getEvalStartTime().getTime(), evaluatorExpMessage);
    }


}
