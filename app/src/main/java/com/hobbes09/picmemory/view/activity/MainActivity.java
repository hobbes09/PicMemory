package com.hobbes09.picmemory.view.activity;

import android.os.Bundle;

import com.hobbes09.picmemory.R;
import com.hobbes09.picmemory.view.base.BaseActivity;
import com.hobbes09.picmemory.view.fragment.PlayFragment;


public class MainActivity extends BaseActivity implements
        PlayFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            replaceFragment(R.id.flContainer, PlayFragment.newInstance());
        }
    }

    @Override
    public void onPlayFragmentInteraction() {
    }
}
