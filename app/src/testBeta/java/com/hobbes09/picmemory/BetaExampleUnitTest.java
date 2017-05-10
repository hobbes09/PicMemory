package com.hobbes09.picmemory;

import org.junit.Test;

import static org.junit.Assert.*;

import com.hobbes09.picmemory.utils.GlobalConfig;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BetaExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkBaseApi() throws Exception{

        String baseApi = GlobalConfig.getInstance().mConfigInterface.getBaseApi();

        if (BuildConfig.ENVIRONMENT == "PRODUCTION"){

            assertEquals ("http://www.omdbapi.com", baseApi);

        }else if (BuildConfig.ENVIRONMENT == "BETA"){

            assertEquals ("www.mydummywebsite.com", baseApi);

        }

    }


}