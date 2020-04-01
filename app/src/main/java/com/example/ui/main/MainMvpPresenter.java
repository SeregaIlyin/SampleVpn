package com.example.ui.main;

import android.view.View;

import com.example.model.VpnServer;
import com.example.ui.base.MvpPresenter;

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void setUp();

    void onSettingsWasClicked();

    void onConnectWasClicked(View view);

    void onSettingsChanged(VpnServer vpnServer);


}

