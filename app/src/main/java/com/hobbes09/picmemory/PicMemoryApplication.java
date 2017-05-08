package com.hobbes09.picmemory;

import android.app.Application;

import com.hobbes09.picmemory.inject.component.DaggerNetComponent;
import com.hobbes09.picmemory.inject.component.NetComponent;
import com.hobbes09.picmemory.inject.module.AppModule;
import com.hobbes09.picmemory.inject.module.NetModule;
import com.hobbes09.picmemory.utils.GlobalConfig;

/**
 * Created by hobbes09 on 08/05/17.
 */

public class PicMemoryApplication extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(GlobalConfig.BASE_API))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

}
