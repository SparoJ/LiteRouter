package com.sparoj.face;

import com.sparoj.annotation.Module;
import com.sparoj.literouter.RouterModule;

/**
 * description:
 * Created by sdh on 2019/3/13
 */

@Module(providePackageName = "com.sparoj.literouter", moduleName = "face")
public class FaceApi extends RouterModule{


    @Override
    protected String getModuleName() {
        return "face";
    }
}
