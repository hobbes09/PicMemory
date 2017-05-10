package com.hobbes09.picmemory.model.envs;

import com.hobbes09.picmemory.utils.Contants;

/**
 * Created by hobbes09 on 09/05/17.
 */

public class BetaConfigManager implements ConfigInterface{

    @Override
    public String getBaseApi() {
        return Contants.CONST2;
    }

    @Override
    public int getNumColumns() {
        return Contants.NUM1;
    }
}
