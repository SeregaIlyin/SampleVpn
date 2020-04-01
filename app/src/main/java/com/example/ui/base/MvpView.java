package com.example.ui.base;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ResourceProvider;

import java.util.List;


public interface MvpView {

    void showProgress();

    void showProgress(String message);

    void hideProgress();

    void showMessage(String message);

    void showMessage(int res);

    ResourceProvider getResourceProvider();

    boolean isKeyBoardVisible();

    void setKeyBoardVisible(boolean keyBoardVisible);

    boolean isKeyboardShown(View view);

    void showSoftKeyboard(View view);

    void hideKeyboard(TextView v, int actionId, List<EditText> editTexts);

    void onActionPerformed(Object object);

    boolean hasPermission(String permission);

    void requestPermissionsSafely(String[] permissions, int requestCode);

    void copyToClipboard(String label, String text);

    void setActions();

    void clearFocus();
}
