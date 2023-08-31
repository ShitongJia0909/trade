package com.jst.trajst.dashboard.server.schedule;

import com.jst.jstdao.model.TaskInfo;
import com.jst.trajst.dashboard.persistance.database.services.TaskService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TaskFactory implements ApplicationContextAware {

    @Resource
    TaskService taskService;

    private ApplicationContext applicationContext;

    public AbstractTaskProxy create(TaskInfo taskInfo){
        ITask realTask = applicationContext.getBean(taskInfo.getTaskName(), ITask.class);
        AbstractTaskProxy abstractTaskProxy = new AbstractTaskProxy(taskInfo, taskService, realTask);
        return abstractTaskProxy;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
