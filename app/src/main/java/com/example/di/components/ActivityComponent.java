package com.example.di.components;


import com.example.di.annotation.PerActivity;
import com.example.di.modules.ActivityModule;
import com.example.ui.main.MainActivity;
import com.example.ui.settings.SettingsActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(SettingsActivity activity);

}
