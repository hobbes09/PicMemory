package com.hobbes09.picmemory.view;

import java.util.List;

/**
 * Created by hobbes09 on 08/05/17.
 */

public interface IPlayView {

    public void refreshPlay();

    public void refeshAdapter(List<String> urls);

    public void onFetchFailed();

}
