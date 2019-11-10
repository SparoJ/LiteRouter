package com.sparoj.literouter;

/**
 * description:
 * Created by sdh on 2019/3/13
 */

public class EmptyAction implements RouterAction, IRouterError {


    @Override
    public RouterResponse router(RouterRequest request) {
        return routerError("404", "error without trigger action");
    }

    @Override
    public String getActionName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public RouterResponse routerError(String code, String msg) {
       return new RouterResponse().code(code).msg(msg);
    }
}
