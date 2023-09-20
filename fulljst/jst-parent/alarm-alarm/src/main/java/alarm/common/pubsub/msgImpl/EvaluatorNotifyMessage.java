package alarm.common.pubsub.msgImpl;

import alarm.common.pubsub.IPublishMessage;

import java.util.Date;

public class EvaluatorNotifyMessage extends AbstractMessage implements IPublishMessage {

    private String evalResultId;
    private Date evalStartTime;

    public EvaluatorNotifyMessage(String rawDate, String timeAtt, String evalResultId, Date evalStartTime) {
        super(rawDate, timeAtt);
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
