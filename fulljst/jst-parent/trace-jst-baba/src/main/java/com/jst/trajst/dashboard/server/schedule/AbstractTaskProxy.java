package com.jst.trajst.dashboard.server.schedule;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.jst.jstdao.model.TaskHistory;
import com.jst.jstdao.model.TaskInfo;
import com.jst.trajst.dashboard.enums.EnumBooleanResult;
import com.jst.trajst.dashboard.persistance.database.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.sql.Timestamp;

public class AbstractTaskProxy implements Runnable{

    private final static Logger logger = LoggerFactory.getLogger(AbstractTaskProxy.class);

    private TaskInfo taskInfo;
    private TaskService taskService;
    private ITask realTask;
    private Timestamp tBegin;

    public AbstractTaskProxy(TaskInfo taskInfo, TaskService taskService, ITask realTask) {
        this.taskInfo = taskInfo;
        this.taskService = taskService;
        this.realTask = realTask;
    }


    @Override
    public void run() {
        tBegin = new Timestamp(System.currentTimeMillis());
        if(canPerform()){
            int restPerformTimes = taskInfo.getRetryTime() + 1;
            long costTime = -1;
            EnumBooleanResult result = EnumBooleanResult.FALSE;

            do{
                try{
                    restPerformTimes--;
                    EnumBooleanResult restTmp = realTask.doPerform();
                    result = (restTmp==null ? EnumBooleanResult.FALSE : restTmp);

                }catch (Exception e){
                    logger.error("doPerform failed. {}", taskInfo.toString(), e);
                }
            } while (restPerformTimes > 0 && result == EnumBooleanResult.FALSE && costTime < taskInfo.getTotalExeTime());

            doFinish(result, (System.currentTimeMillis()-tBegin.getTime()), taskInfo.getRetryTime() + 1 - restPerformTimes);


        }



    }

    private boolean canPerform(){
        try{
            taskInfo = taskService.lockForTaskStart(taskInfo, tBegin);
            logger.info("lock success. {}", taskInfo.toString());

        }catch (Exception e){
            logger.warn("this task cannot perform. {} e={}", taskInfo.toString(),e.getMessage());
            return false;
        }
        return true;
    }


    private void doFinish(EnumBooleanResult result, long costTime, int performTimes){
        try{
            TaskHistory taskHistory = new TaskHistory();
            taskHistory.setCost(costTime);
            taskHistory.setPerformTimes(performTimes);
            taskHistory.setResult(result.getValue());
            taskHistory.setStartTime(tBegin);
            taskHistory.setEndTime(new Timestamp(tBegin.getTime() + costTime));
            taskHistory.setHostname(InetAddress.getLocalHost().getHostName());
            taskHistory.setThreadName(Thread.currentThread().getName());
            taskService.lockForTaskEnd(taskInfo,taskHistory,result);
        }catch (Exception e){
            logger.error("The task is not yet complete. {}", taskInfo.toString(), e);
        }
    }



}
