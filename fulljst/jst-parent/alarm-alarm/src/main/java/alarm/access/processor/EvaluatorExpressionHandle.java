package alarm.access.processor;

import alarm.access.service.AlarmService;
import alarm.common.exception.ExecuteAuthException;
import alarm.common.pubsub.IMessageConsumer;
import alarm.common.pubsub.IPublish;
import alarm.common.pubsub.IPublishMessage;
import alarm.common.pubsub.msgImpl.EvaluatorExpMessage;
import com.jst.jstdao.model.TEvaluatorResult;
import com.jst.jstdao.repository.TEvaluatorRepository;
import com.jst.jstdao.repository.TEvaluatorResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class EvaluatorExpressionHandle implements IMessageConsumer, IExecuteAuthority<TEvaluatorResult> {

    Logger logger = LoggerFactory.getLogger(EvaluatorExpressionHandle.class);

    @Resource
    TEvaluatorResultRepository evaluatorResultRepository;

    @Resource
    AlarmService alarmService;

    @Resource
    TEvaluatorRepository evaluatorRepository;

    @Resource
    IPublish publish;


    @Override
    public void gainAccess() throws ExecuteAuthException {
    }

    @Override
    public boolean apply(IPublishMessage message) {
        return message instanceof EvaluatorExpMessage;
    }

    @Override
    public void handleMessage(IPublishMessage message) throws Exception {
        EvaluatorExpMessage evaluatorExpMessage = (EvaluatorExpMessage) message;
        String evalResultId = evaluatorExpMessage.getEvalResultId();
        String rawData = evaluatorExpMessage.getRawData();
        String timeAtt = evaluatorExpMessage.getTimeAtt();

    }
}
