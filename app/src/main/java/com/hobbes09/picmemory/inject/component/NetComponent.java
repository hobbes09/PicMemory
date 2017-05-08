package com.hobbes09.picmemory.inject.component;

import com.hobbes09.picmemory.inject.module.AppModule;
import com.hobbes09.picmemory.inject.module.NetModule;
import com.hobbes09.picmemory.service.PicService;
import com.hobbes09.picmemory.view.fragment.PlayFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hobbes09 on 08/05/17.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(PicService picService);

}
