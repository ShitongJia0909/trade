package com.jst.common.config;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class CustomerExecutorConfig {

    public static Executor getAsyncExecutor(AsyncExeConfig asyncExeConfig){
        int corePoolSize = asyncExeConfig.getCorePoolSize();
        int maxPoolSize = asyncExeConfig.getMaxPoolSize();
        int queueCapacity = asyncExeConfig.getQueueCapacity();
        int keepAliveSeconds =asyncExeConfig.getKeepAliveSeconds();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize > 0 ? corePoolSize : 2 * Runtime.getRuntime().availableProcessors());
        executor.setKeepAliveSeconds(maxPoolSize > 0 ? maxPoolSize : 2 * Runtime.getRuntime().availableProcessors());
        executor.setQueueCapacity(queueCapacity > 0 ? queueCapacity : Integer.MAX_VALUE);
        executor.setKeepAliveSeconds(keepAliveSeconds > 0 ? keepAliveSeconds :0);
        executor.setThreadNamePrefix(asyncExeConfig.getThreadNamePrefix());
        return executor;
    }




    // 内部类
    public static class AsyncExeConfig {
        private int corePoolSize = 2 * Runtime.getRuntime().availableProcessors();
        private int maxPoolSize = 2 * Runtime.getRuntime().availableProcessors();
        private int queueCapacity = 300;
        private int keepAliveSeconds = 60;
        private String threadNamePrefix = "async-task-pool-";

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }

        public int getKeepAliveSeconds() {
            return keepAliveSeconds;
        }

        public void setKeepAliveSeconds(int keepAliveSeconds) {
            this.keepAliveSeconds = keepAliveSeconds;
        }

        public String getThreadNamePrefix() {
            return threadNamePrefix;
        }

        public void setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }
    }



}
