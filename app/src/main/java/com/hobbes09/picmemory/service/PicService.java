package com.hobbes09.picmemory.service;

import android.app.Application;
import android.content.Context;

import com.hobbes09.picmemory.PicMemoryApplication;
import com.hobbes09.picmemory.model.pojos.PicFeed;
import com.hobbes09.picmemory.presenter.interfaces.IFetchFinishedPresenter;
import com.hobbes09.picmemory.service.interfaces.Getters;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by hobbes09 on 08/05/17.
 */

public class PicService implements IPicService {

    private PicFeed mPicFeed;

    @Inject
    public Retrofit retrofit;

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

    public PicService (PicMemoryApplication mApplication, IFetchFinishedPresenter mIFetchFinishedPresenter){
        this.mIFetchFinishedPresenter = mIFetchFinishedPresenter;
        mApplication.getNetComponent().inject(this);
    }

    @Override
    public void fetchPicsAsync(int page) {
        Getters getters = this.retrofit.create(Getters.class);
        Call<PicFeed> picFeedCall = getters.getPicsFeed("comedy", page+"");
        picFeedCall.enqueue(mPicFeedCallback);
    }
}
