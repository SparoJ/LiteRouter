package com.sparoj.literouter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description:
 * Created by sdh on 2019/3/14
 */

public class RouterTaskManager {


    public static ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(r, "router thread");
            thread.setDaemon(true);
            return thread;
        }
    };

//    public static RouterResponse submit(Callable<RouterResponse> callable) throws Exception{
//        return getThreadPoolExecutor().submit(callable).get();
//    }

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(0,
                Integer.MAX_VALUE,
                60,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                threadFactory);
    }



}
