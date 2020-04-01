package com.example;

import android.content.Context;
import android.os.StrictMode;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.example.di.components.ApplicationComponent;
import com.example.di.components.DaggerApplicationComponent;
import com.example.di.modules.ApplicationModule;
import com.xfinity.resourceprovider.RpApplication;

import javax.inject.Inject;


/**
 * Главный модуль приложения
 */

@RpApplication()
public class SampleVpnApplication extends MultiDexApplication {

    public static SampleVpnApplication getInstance() {
        return instance;
    }
    public static DisplayMetrics metrics;
    public static Context getContext(){
        return instance;
    }

    private static SampleVpnApplication instance;

    public static SampleVpnApplication get(Context context) {
        return (SampleVpnApplication) context.getApplicationContext();
    }

    ApplicationComponent applicationComponent;

    @Inject
    ResourceProvider resourceProvider;

    /**
     * Вызов при создании приложения
     */
    @Override
    public void onCreate() {
        super.onCreate();


        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationComponent.inject(this);
        //applicationComponent.currentUser().load();

        instance = this;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        metrics = getResources().getDisplayMetrics();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public ResourceProvider getResourceProvider() {
        return resourceProvider;
    }
}
