package com.example.ui.main;

import android.graphics.Bitmap;
import android.view.animation.Animation;

import com.example.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showSettingsScreen();

    void setServer(String value);

    void setBtnConnectEnabled(boolean enabled);

    void setBtnConnectTitle(String title);

    void setCountry(Bitmap flag);

    void startAnimation(Animation animation);
}

