package com.hobbes09.picmemory.presenter.interfaces;

import com.hobbes09.picmemory.model.pojos.PicFeed;

/**
 * Created by hobbes09 on 08/05/17.
 */

public interface IFetchFinishedPresenter {

    public void onSuccess(PicFeed picFeed);

    public void onError();

    public void onFailure();

}
