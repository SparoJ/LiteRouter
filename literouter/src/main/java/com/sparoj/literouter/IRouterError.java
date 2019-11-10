package com.sparoj.literouter;

/**
 * description:
 * Created by sdh on 2019/3/14
 */

public interface IRouterError {
    RouterResponse routerError(String code, String msg);
}
