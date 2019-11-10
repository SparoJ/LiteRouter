package com.sparoj.literouter;

/**
 * description:
 * Created by sdh on 2019/3/13
 */

public interface RouterAction<T> {


    RouterResponse router(RouterRequest<T> request);

    String getActionName();
}
