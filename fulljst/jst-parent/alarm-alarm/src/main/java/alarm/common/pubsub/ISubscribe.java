package alarm.common.pubsub;

import alarm.common.pubsub.IPublishMessage;

public interface ISubscribe {

    void subscribeMessage(IPublishMessage message);
}
