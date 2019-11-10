package com.sparoj.intercept;

import com.sparoj.intercept.RouterInterceptor.Chain;
import com.sparoj.literouter.EmptyAction;
import com.sparoj.literouter.RouterModule;
import com.sparoj.literouter.RouterRequest;
import com.sparoj.literouter.RouterResponse;

import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by sdh on 2019/3/15
 */

public class RouterInterceptorChain implements Chain {

    private Map<String, RouterModule> moduleMap;
    private RouterRequest request;
    private List<RouterInterceptor> interceptorList;
    private int index;

    public RouterInterceptorChain(List<RouterInterceptor> interceptors, RouterRequest request, Map<String, RouterModule> moduleMap, int index) {
        this.index = index;
        this.interceptorList = interceptors;
        this.moduleMap = moduleMap;
        this.request = request;
    }

    @Override
    public RouterRequest request() {
        return request;
    }

    public Map<String, RouterModule> moduleMap(){
        return moduleMap;
    }

    /**
     * 链式触发锚点
     * @param request
     * @return RouterResponse
     */
    @Override
    public RouterResponse proceed(RouterRequest request) {
        return proceed(interceptorList, request, moduleMap, index);
    }

    private RouterResponse proceed(List<RouterInterceptor> interceptorList, RouterRequest request, Map<String, RouterModule> moduleMap, int index) {
        if (index>=interceptorList.size()) {
            return new EmptyAction().routerError("500", "check the size of the interceptor list you want to proceed");
        }
        RouterInterceptorChain next = new RouterInterceptorChain(interceptorList,  request, moduleMap, index+1);
        RouterInterceptor interceptor = interceptorList.get(index);
        RouterResponse response = interceptor.intercept(next);
        if (response ==null) {
            return new EmptyAction().routerError("500", "intercept with null response :(interceptor) " + interceptor);
        }
        return response;
    }
}
