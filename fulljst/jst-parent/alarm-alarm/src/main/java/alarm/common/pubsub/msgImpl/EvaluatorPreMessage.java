package alarm.common.pubsub.msgImpl;

import alarm.common.pubsub.IPublishMessage;
import com.jst.jstdao.model.TEvaluator;

public class EvaluatorPreMessage extends AbstractMessage implements IPublishMessage {

    private TEvaluator tEvaluator;

    public EvaluatorPreMessage(String rawDate, String timeAtt, TEvaluator tEvaluator) {
        super(rawDate, timeAtt);
        this.tEvaluator = tEvaluator;
    }

    public TEvaluator gettEvaluator() {
        return tEvaluator;
    }
}
