package com.jst.common.thread;

import com.sun.org.apache.xalan.internal.xsltc.runtime.InternalRuntimeError;
import org.apache.lucene.util.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolMgr {

    Logger logger = LoggerFactory.getLogger(ThreadPoolMgr.class);

    private LinkedBlockingQueue linkedBlockingQueue;
    private ThreadPoolExecutor executor;

    private volatile boolean hasRejected = false;

    public ThreadPoolMgr(int corePoolSize, int maximumPoolSize, int queueCapacity){
        linkedBlockingQueue = new LinkedBlockingQueue<>(queueCapacity);
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 5L, TimeUnit.SECONDS,
                linkedBlockingQueue, new NamedThreadFactory("threadPoolMgr"));

        //executor.setRejectedExecutionHandler(super.rejected);
    }


    public synchronized void submit(Runnable task){
        isOverLoad();
        executor.submit(task);
    }

    private void isOverLoad(){
        if(hasRejected){
            hasRejected = false;
            logger.error("ThreadPool is over load. getActiveCount={}", executor.getActiveCount());

            try{
                Thread.sleep(3000);
            }catch (InterruptedException e){
                logger.error("thread sleep error", e);
            }
        }
    }


}
