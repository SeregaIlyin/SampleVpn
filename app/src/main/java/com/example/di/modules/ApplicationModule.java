package com.example.di.modules;

import android.app.Application;
import android.content.Context;

import com.example.ResourceProvider;
import com.example.di.annotation.ApplicationContext;
import com.xfinity.resourceprovider.RpApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@RpApplication(generateIdProvider = false)
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ResourceProvider provideResourceProvider() {
        return new ResourceProvider(provideContext());
    }
}