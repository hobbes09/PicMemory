package com.hobbes09.picmemory.presenter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.hobbes09.picmemory.PicMemoryApplication;
import com.hobbes09.picmemory.model.pojos.PicFeed;
import com.hobbes09.picmemory.presenter.interfaces.IFetchFinishedPresenter;
import com.hobbes09.picmemory.presenter.interfaces.IFetchPicsPresenter;
import com.hobbes09.picmemory.service.PicService;
import com.hobbes09.picmemory.view.IPlayView;

import java.util.stream.Collectors;

/**
 * Created by hobbes09 on 08/05/17.
 */

public class FetchPicsPresenter implements IFetchPicsPresenter, IFetchFinishedPresenter {

    private PicService mPicService;
    private IPlayView iPlayView;

    public FetchPicsPresenter(PicMemoryApplication mApplication, IPlayView iPlayView) {
        this.mPicService = new PicService(mApplication, this);
        this.iPlayView = iPlayView;
    }

    @Override
    public void fetchPics(int page) {
        // Perform validation and call api service
        // TODO : Maybe we can inject a validator for this
        if (page > 0 && page < 100){
            this.mPicService.fetchPicsAsync(page);
        } else {
            onError();
        }
    }

    @Override
    public void onSuccess(PicFeed picFeed) {
        iPlayView.refeshAdapter(picFeed.getSearch().stream()
                                    .map(d -> d.getPoster())
                                    .limit(9)
                                    .collect(Collectors.toList()));
    }

    @Override
    public void onError() {
        iPlayView.onFetchFailed();
    }

    @Override
    public void onFailure() {
        iPlayView.onFetchFailed();
    }
}
