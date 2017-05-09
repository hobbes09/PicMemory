package com.hobbes09.picmemory.utils;

import com.hobbes09.picmemory.BuildConfig;
import com.hobbes09.picmemory.model.envs.BetaConfigManager;
import com.hobbes09.picmemory.model.envs.ConfigInterface;
import com.hobbes09.picmemory.model.envs.ProdConfigManager;

/**
 * Created by hobbes09 on 08/05/17.
 */

public class GlobalConfig {

    private static final GlobalConfig sGlobalConfig = new GlobalConfig();

    public ConfigInterface mConfigInterface;

    public static GlobalConfig getInstance(){
        return sGlobalConfig;
    }

    private GlobalConfig(){

        switch (BuildConfig.ENVIRONMENT){
            case "PRODUCTION":
                mConfigInterface = new ProdConfigManager();
                break;
            case "BETA":
                mConfigInterface = new BetaConfigManager();
                break;
        }
    }

    public ConfigInterface getConfigInterface() {
        return mConfigInterface;
    }
}
