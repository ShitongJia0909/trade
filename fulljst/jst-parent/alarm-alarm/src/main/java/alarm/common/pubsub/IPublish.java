package alarm.common.pubsub;

public interface IPublish {

    String PUBLISH_TOPIC = "FULL_CHAIN_MONITOR_TOPIC";

    void publishMessage(IPublishMessage subject);


    void publishLocalMessage(IPublishMessage subject);


}
