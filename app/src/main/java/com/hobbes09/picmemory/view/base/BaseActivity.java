package com.hobbes09.picmemory.view.base;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hobbes09 on 08/05/17.
 */

public class BaseActivity extends AppCompatActivity {

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected void replaceFragment(int containerViewId, BaseFragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(containerViewId, fragment)
                .commit();
    }
}
