package com.hobbes09.picmemory.model.envs;

/**
 * Created by hobbes09 on 09/05/17.
 */

public class ProdConfigManager implements ConfigInterface {

    @Override
    public String getBaseApi() {
        return "http://www.omdbapi.com";
    }

    @Override
    public int getNumColumns() {
        return 3;
    }
}
