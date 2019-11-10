package com.sparoj.intercept;

import com.sparoj.literouter.EmptyAction;
import com.sparoj.literouter.RouterAction;
import com.sparoj.literouter.RouterModule;
import com.sparoj.literouter.RouterRequest;
import com.sparoj.literouter.RouterResponse;

import java.util.Map;

/**
 * description: 发起路由点
 * Created by sdh on 2019/3/16
 */

public class RealRouterActInterceptor implements RouterInterceptor {
    @Override
    public RouterResponse intercept(Chain chain) {
        RouterInterceptorChain routerChain = (RouterInterceptorChain) chain;
        RouterRequest request = routerChain.request();
       return router(routerChain.moduleMap(), request);
    }

    /**
     * 发起行为
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public RouterResponse router(Map<String, RouterModule> routerMap, RouterRequest request){
        if (routerMap == null || routerMap.size()==0) {
            return new EmptyAction().routerError("404", "relative register router module should not be null or empty");
        }
        RouterAction action = findRouterAction(routerMap, request);
        return action.router(request);
    }



    private RouterAction findRouterAction(Map<String, RouterModule> routerMap, RouterRequest request) {

        RouterModule module = routerMap.get(request.getTargetModule());
        if (module != null) {
            return module.findRouterAction(request.getRouterAction());
        }
        return new EmptyAction();
    }
}
