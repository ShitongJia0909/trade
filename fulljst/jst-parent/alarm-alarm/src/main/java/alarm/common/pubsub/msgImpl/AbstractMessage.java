package alarm.common.pubsub.msgImpl;

import alarm.common.pubsub.IPublishMessage;

import java.io.Serializable;

public class AbstractMessage implements IPublishMessage, Serializable {
    protected String rawDate;
    protected String timeAtt;

    public AbstractMessage(String rawDate, String timeAtt) {
        this.rawDate = rawDate;
        this.timeAtt = timeAtt;
    }


    @Override
    public String getRawData() {
        return rawDate;
    }

    @Override
    public String getTimeAtt() {
        return timeAtt;
    }
}
