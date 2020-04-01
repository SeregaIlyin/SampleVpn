package com.example.di.components;

import com.example.di.annotation.PerActivity;
import com.example.di.modules.FragmentModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
}
