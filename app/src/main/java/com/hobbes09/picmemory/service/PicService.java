package com.hobbes09.picmemory.service;

import com.hobbes09.picmemory.model.pojos.PicFeed;
import com.hobbes09.picmemory.presenter.interfaces.IFetchFinishedPresenter;
import com.hobbes09.picmemory.service.interfaces.Getters;
import com.hobbes09.picmemory.utils.GlobalConfig;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by hobbes09 on 08/05/17.
 */

public class PicService implements IPicService {

    private PicFeed mPicFeed;

    // TODO : To be injected
    private  Retrofit retrofit;

    private IFetchFinishedPresenter mIFetchFinishedPresenter;

    private Callback<PicFeed> mPicFeedCallback =
            new Callback<PicFeed>() {
                @Override
                public void onResponse(Response<PicFeed> response) {

                    if (response.isSuccess()) {
                        mPicFeed = (PicFeed)response.body();
                        mIFetchFinishedPresenter.onSuccess(mPicFeed);
                    } else {
                        mIFetchFinishedPresenter.onError();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    mIFetchFinishedPresenter.onFailure();
                }
            };

    public PicService (IFetchFinishedPresenter mIFetchFinishedPresenter){
        this.mIFetchFinishedPresenter = mIFetchFinishedPresenter;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(GlobalConfig.BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void fetchPicsAsync(int page) {
        Getters getters = this.retrofit.create(Getters.class);
        Call<PicFeed> picFeedCall = getters.getPicsFeed("comedy", page+"");
        picFeedCall.enqueue(mPicFeedCallback);
    }
}
