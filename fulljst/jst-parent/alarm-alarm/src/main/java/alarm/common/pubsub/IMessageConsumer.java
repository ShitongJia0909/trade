package alarm.common.pubsub;

public interface IMessageConsumer {

    boolean apply(IPublishMessage message);

    void handleMessage(IPublishMessage message) throws Exception;

}
