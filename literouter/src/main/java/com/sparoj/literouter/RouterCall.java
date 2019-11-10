package com.sparoj.literouter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.sparoj.intercept.RealRouterActInterceptor;
import com.sparoj.intercept.RouterInterceptor;
import com.sparoj.intercept.RouterInterceptorChain;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * Created by sdh on 2019/3/16
 */

public class RouterCall implements Call<RouterResponse> {

    private LiteRouter liteRouter;
    private RouterRequest request;
    private static Handler handler = new Handler(Looper.getMainLooper());


    public RouterCall(LiteRouter liteRouter, RouterRequest request) {
        this.liteRouter = liteRouter;
        this.request = request;
    }

    @Override
    public RouterResponse execute() {
        return getResponseWithInterceptorChain();
    }

    private RouterResponse getResponseWithInterceptorChain() {
        List<RouterInterceptor> interceptors = new ArrayList<>();
        interceptors.addAll(liteRouter.getInterceptors());
        interceptors.add(new RealRouterActInterceptor());
        RouterInterceptorChain chain = new RouterInterceptorChain(interceptors, request, liteRouter.getModuleMap(),0);
        return chain.proceed(request);
    }

    @Override
    public void enqueue(RouterCallback callback) {
        Log.e("RouterCall", "enqueue(RouterCall.java:45)" + Thread.currentThread());
        try {
            RouterTaskManager.getThreadPoolExecutor().submit(new RouterTask(callback));
        } catch (Exception e) {
            callback.routerBack(new EmptyAction().routerError("501", "exception:" + e.toString()));
        }
    }

    public class RouterTask implements Runnable {

        private RouterCallback callback;

        public RouterTask(RouterCallback callback) {
            this.callback = callback;
        }


        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            Log.e("RouterTask", "call(RouterTask.java:54)" + Thread.currentThread());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.routerBack(getResponseWithInterceptorChain());
                }
            });
        }
    }
}
