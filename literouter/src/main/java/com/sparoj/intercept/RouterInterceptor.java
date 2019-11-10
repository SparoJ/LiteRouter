package com.sparoj.intercept;

import com.sparoj.literouter.RouterRequest;
import com.sparoj.literouter.RouterResponse;

/**
 * description:按照ok拦截器逻辑完成router 的拦截
 * Created by sdh on 2019/3/15
 */

public interface RouterInterceptor {

    RouterResponse intercept(Chain chain);

    interface Chain {

        RouterRequest request();

        RouterResponse proceed(RouterRequest request);
    }
}
