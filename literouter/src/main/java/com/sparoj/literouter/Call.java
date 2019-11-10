package com.sparoj.literouter;

/**
 * description:
 * Created by sdh on 2019/3/16
 */

public interface Call<T> {

    /**
     * 同步
     * @return
     */
    T execute();

    void enqueue(RouterCallback callback);
}
