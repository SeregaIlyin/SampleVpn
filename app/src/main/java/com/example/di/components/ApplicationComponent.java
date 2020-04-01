package com.example.di.components;

import android.content.Context;

import com.example.SampleVpnApplication;
import com.example.di.annotation.ApplicationContext;
import com.example.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SampleVpnApplication sampleVpnApplication);

    @ApplicationContext
    Context context();


}
