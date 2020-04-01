package com.example.ui.base;

import android.view.View;


public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void onGlobalLayout(View view);
}

