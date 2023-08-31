package com.jst.trajst.dashboard.server.schedule;


import com.jst.common.thread.ThreadPoolMgr;
import com.jst.jstdao.model.TaskInfo;
import com.jst.jstdao.repository.TaskInfoRepository;
import com.jst.trajst.dashboard.enums.EnumTaskStatus;
import com.jst.trajst.dashboard.persistance.database.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.round;

@Component
public class TaskCenter {
    private final static Logger logger = LoggerFactory.getLogger(TaskCenter.class);
    private final static Long SECRUE_TIME = 600000L;

    @Resource
    TaskInfoRepository taskInfoRepository;

    @Resource
    TaskFactory taskFactory;

    @Resource
    ThreadPoolMgr threadPoolMgr;

    @Resource
    TaskService taskService;

    class TaskDistributorTask extends TimerTask {

        @Override
        public void run() {
            try{
                distribute();
            }catch (Exception e){
                logger.error("TaskDistributor has an inner error.", e);
            }
        }
    }

    @PostConstruct
    public void init(){
        Timer flushTimer = new Timer("TaskCenter distributor", true);
        flushTimer.schedule(new TaskDistributorTask(), 20000,1000);
        logger.info("TaskCenter start...");
    }


    private void distribute(){
        List<TaskInfo> taskList = taskInfoRepository.findAllByEnbaleAndStatus(EnumTaskStatus.ENABLE.getValue(), EnumTaskStatus.INACTIVE.getValue());
        taskList.stream().filter( task ->
                task.getIntervalTime() > 0
                &&  task.getEnbale() == EnumTaskStatus.ENABLE.getValue()
                &&  task.getStatus() == EnumTaskStatus.INACTIVE.getValue()
                &&  task.getStartTime() != null
                &&  task.getModityTime() != null
                &&  task.getRetryTime()  >= 0
                &&  System.currentTimeMillis() - task.getStartTime().getTime() > (task.getIntervalTime())
        ).forEach(task -> threadPoolMgr.submit(taskFactory.create(task)));


        // 2. 兜底一直running的task
        List<TaskInfo> taskRunningList = taskInfoRepository.findAllByEnbaleAndStatus(EnumTaskStatus.ENABLE.getValue(), EnumTaskStatus.RUNNING.getValue());
        taskRunningList.stream().filter( task ->
                task.getIntervalTime() > 0
                        &&  task.getEnbale() == EnumTaskStatus.ENABLE.getValue()
                        &&  task.getStatus() == EnumTaskStatus.RUNNING.getValue()
                        &&  task.getStartTime() != null
                        &&  task.getModityTime() != null
                        &&  task.getRetryTime()  >= 0
                        &&  task.getTotalExeTime()  >= 0
                        &&  System.currentTimeMillis() - task.getStartTime().getTime() > (180*100 + round(Math.log10(task.getIntervalTime())) + task.getTotalExeTime())
        ).forEach(task -> {
            taskService.forceRunningToInactive(task, EnumTaskStatus.INACTIVE, new Timestamp(System.currentTimeMillis()));
            logger.info("Task {} modify status from enable to inactive. ", task.getTaskName());
        }
        );
    }

}
