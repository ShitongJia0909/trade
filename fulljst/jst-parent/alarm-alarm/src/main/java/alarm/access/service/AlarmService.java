package alarm.access.service;

import com.jst.jstdao.model.TEvaluatorResult;
import com.jst.jstdao.repository.TAlarmHistoryRepository;
import com.jst.jstdao.repository.TEvaluatorResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AlarmService {
    Logger logger = LoggerFactory.getLogger(AlarmService.class);

    @Resource
    TEvaluatorResultRepository tEvaluatorResultRepository;

    @Resource
    TAlarmHistoryRepository tAlarmHistoryRepository;

    @Transactional()
    public TEvaluatorResult tryCreateEvaluatorResult(TEvaluatorResult result){
        if(null == tEvaluatorResultRepository.findDistinctByFieldsExpAndEvalNameAndLogTime(result.getFieldsExp(),result.getEvalName(),result.getLogTime())){
            try {
                return tEvaluatorResultRepository.save(result);
            }catch (Throwable e){
                logger.debug("tryCreateEvaluatorResult TEvaluatorResult failed.", e);
            }
        }
        return null;
    }




}
