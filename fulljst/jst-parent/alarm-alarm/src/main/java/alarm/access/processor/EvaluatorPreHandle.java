package alarm.access.processor;

import alarm.access.service.AlarmService;
import alarm.common.pubsub.IMessageConsumer;
import alarm.common.pubsub.IPublish;
import alarm.common.pubsub.IPublishMessage;
import alarm.common.pubsub.msgImpl.EvaluatorNotifyMessage;
import alarm.common.pubsub.msgImpl.EvaluatorPreMessage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jst.jstdao.model.TEvaluator;
import com.jst.jstdao.model.TEvaluatorResult;
import com.jst.jstdao.repository.TDataSourceRepository;
import com.jst.jstdao.repository.TEvaluatorResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Component
public class EvaluatorPreHandle implements IMessageConsumer {

    Logger logger = LoggerFactory.getLogger(EvaluatorPreHandle.class);

    @Resource
    AlarmService alarmService;

    @Resource
    TEvaluatorResultRepository evaluatorResultRepository;

    @Resource
    TDataSourceRepository dataSourceRepository;

    @Resource
    IPublish publish;

    private final static int FIXED_DELAY = 500;

    private final static int RANDOM_BOUND = 1000;


    @Override
    public boolean apply(IPublishMessage message) {
        return message instanceof EvaluatorPreMessage;
    }


    @Override
    public void handleMessage(IPublishMessage message) throws Exception{
        logger.debug("EvaluatorPreHandle call handleMessage.");
        EvaluatorPreMessage evaluatorPreMessage = (EvaluatorPreMessage) message;
        JSONObject rawJson = JSON.parseObject(evaluatorPreMessage.getRawData());
        TEvaluator evaluator = evaluatorPreMessage.gettEvaluator();
        Assert.notNull(evaluator, "evaluator is null");
        TEvaluatorResult result = new TEvaluatorResult();

        TEvaluatorResult dbResult = alarmService.tryCreateEvaluatorResult(result);

        if(null != dbResult){
            publish.publishMessage(new EvaluatorNotifyMessage(evaluatorPreMessage.getRawData(),evaluatorPreMessage.getTimeAtt(),result.getEvalName(),result.getLogTime()));
        }else{
            logger.debug("Duplicate entry. {},{},{}", result.getFieldsExp(),result.getEvalName(), result.getLogTime());
        }
    }
}
