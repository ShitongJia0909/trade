package alarm.common.pubsub.msgImpl;

import alarm.common.pubsub.IPublishMessage;

import java.util.Date;

public class EvaluatorExpMessage extends AbstractMessage implements IPublishMessage {
    private String evalResultId;

    private Date evalStartTime;

    public EvaluatorExpMessage(EvaluatorNotifyMessage notifyMessage) {
        super(notifyMessage.getRawData(), notifyMessage.getTimeAtt());
        this.evalResultId = evalResultId;
        this.evalStartTime = evalStartTime;
    }

    public String getEvalResultId() {
        return evalResultId;
    }

    public Date getEvalStartTime() {
        return evalStartTime;
    }
}
