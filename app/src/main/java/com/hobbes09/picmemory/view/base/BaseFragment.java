package com.hobbes09.picmemory.view.base;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

/**
 * Created by hobbes09 on 08/05/17.
 */

public class BaseFragment extends Fragment {

    protected Unbinder unbinder;

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
