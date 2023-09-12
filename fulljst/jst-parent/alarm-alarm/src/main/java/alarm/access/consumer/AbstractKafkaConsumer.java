package alarm.access.consumer;

import alarm.access.processor.EvaluatorFieldsFilter;
import alarm.common.pubsub.IPublish;
import alarm.common.pubsub.msgImpl.EvaluatorPreMessage;
import com.alibaba.fastjson.JSON;
import com.jst.jstdao.model.TDatasource;
import com.jst.jstdao.model.TEvaluator;
import com.jst.jstdao.repository.TDataSourceRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

public abstract class AbstractKafkaConsumer {
     Logger logger = LoggerFactory.getLogger(AbstractKafkaConsumer.class);

     @Resource
     TDataSourceRepository tDataSourceRepository;

     @Resource
     EvaluatorFieldsFilter evaluatorFieldsFilter;

     @Resource
     IPublish publish;

     private List<TDatasource> dsList;

     protected abstract String getTopic();

     @PostConstruct
     public void init() {
          logger.info("TraceRetCodeMinuteConsumer do init");
          dsList = tDataSourceRepository.findAllByKafkaTopicEquals(getTopic());
          Assert.notEmpty(dsList, "dsList is empty. ");
     }

     protected void listen(ConsumerRecord<?,?> record){
          String strData = record.value().toString();

          for(TDatasource datasource : dsList){
               List<TEvaluator> evaluatorList = evaluatorFieldsFilter.doFilter(datasource, JSON.parseObject(strData));
               logger.debug("TraceRetCodeMinuteConsumer: evaluatorList.size={}", evaluatorList.size());
               // try to reduce Duplicate entry for key 'uk_eval_fel_result'
               try{
                    Thread.sleep(7);
               }catch (InterruptedException e){
                    logger.warn("Thread.sleep error. ");
               }

               for(TEvaluator evaluator : evaluatorList){
                    publish.publishLocalMessage(new EvaluatorPreMessage(strData, datasource.getTimeAtt(), evaluator));
               }
          }
     }


}
