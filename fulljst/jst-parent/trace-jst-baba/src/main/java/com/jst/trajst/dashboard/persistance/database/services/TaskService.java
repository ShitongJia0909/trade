package com.jst.trajst.dashboard.persistance.database.services;

import com.jst.jstdao.model.TaskHistory;
import com.jst.jstdao.model.TaskInfo;
import com.jst.jstdao.repository.TaskHistoryRepository;
import com.jst.jstdao.repository.TaskInfoRepository;
import com.jst.trajst.dashboard.enums.EnumBooleanResult;
import com.jst.trajst.dashboard.enums.EnumTaskStatus;
import com.jst.trajst.dashboard.common.exceptions.DbOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;

@Component
public class TaskService {

    private final static Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Resource
    TaskInfoRepository taskInfoRepository;

    @Resource
    TaskHistoryRepository taskHistoryRepository;

    @Transactional(rollbackFor = Exception.class)
    public TaskInfo lockForTaskStart(TaskInfo taskInfo, Timestamp startTime){
        if(taskInfo.getEnbale() == EnumTaskStatus.ENABLE.getValue()
            && taskInfo.getStatus() == EnumTaskStatus.INACTIVE.getValue()){

            return lockForUpdate(taskInfo, EnumTaskStatus.RUNNING, startTime, startTime);

        }
        throw new DbOperationException("lockForTaskStart failed." + taskInfo.toString());

    }

    @Transactional
    public void lockForTaskEnd(TaskInfo taskInfo, TaskHistory taskHistory, EnumBooleanResult result){
        if(taskInfo.getStatus() == EnumTaskStatus.RUNNING.getValue()){
            try{
                lockForUpdate(taskInfo, EnumTaskStatus.INACTIVE,
                        result == EnumBooleanResult.TRUE ? taskHistory.getEndTime() : taskInfo.getStartTime(),
                        taskHistory.getEndTime());
            }catch(DbOperationException e){
                logger.error("Task is finished, but update task is failed. cause by taskInfo lock state is modified by other process.", e);
            }
            taskHistoryRepository.save(taskHistory);
            return;

        }
    }



    private TaskInfo lockForUpdate(TaskInfo taskInfo, EnumTaskStatus status, Timestamp startTime, Timestamp modifyTime){

        int iCnt = taskInfoRepository.lockForUpdate(taskInfo.getTaskName(),
                status.getValue(),
                startTime,
                modifyTime,
                taskInfo.getStatus(),
                taskInfo.getStartTime(),
                taskInfo.getModityTime());

        if(iCnt==1){
            taskInfo.setStatus(status.getValue());
            taskInfo.setStartTime(startTime);
            taskInfo.setModityTime(modifyTime);
            return taskInfo;
        }else{
            throw new DbOperationException("lockForUpdate failed. iCnt=" + iCnt+ " " + taskInfo.toString());
        }


    }

    @Transactional(rollbackFor = DbOperationException.class)
    public TaskInfo forceRunningToInactive(TaskInfo taskInfo, EnumTaskStatus newStatus, Timestamp modifyTime){
        if(taskInfo.getEnbale() == EnumTaskStatus.ENABLE.getValue()
        && taskInfo.getStatus() == EnumTaskStatus.RUNNING.getValue()
        && newStatus ==EnumTaskStatus.INACTIVE){
            return lockForUpdate(taskInfo, newStatus, taskInfo.getStartTime(), modifyTime);
        }
        throw new DbOperationException("forceRunningToInactive failed. " + taskInfo.toString());
    }



}
