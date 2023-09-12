package alarm.access.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TraceBizMinuteConsumer extends AbstractKafkaConsumer{

    Logger logger = LoggerFactory.getLogger(TraceBizMinuteConsumer.class);

    @Value("${trace.consumer.topics.topicTraceBizMinute:topicTraceBizMinute}")
    private String topicTraceMinute;

    @Override
    protected String getTopic() {
        logger.info("topicTraceBizMinute topic={}", topicTraceMinute);
        return topicTraceMinute;
    }


    @Override
    @KafkaListener(topics = {"${trace.consumer.topics.topicTraceBizMinute}"}, groupId = "${trace.consumer.groupId}")
    public void listen(ConsumerRecord<?,?> record){
        super.listen(record);
    }


}
