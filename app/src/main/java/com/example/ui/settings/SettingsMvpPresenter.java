package com.example.ui.settings;

import com.example.ui.base.MvpPresenter;

public interface SettingsMvpPresenter<V extends SettingsMvpView> extends MvpPresenter<V> {

    void setUp();

    void onItemClick(Object o);
}
