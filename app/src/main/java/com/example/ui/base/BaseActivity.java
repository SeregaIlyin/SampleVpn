package com.example.ui.base;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.example.ResourceProvider;
import com.example.SampleVpnApplication;
import com.example.di.components.ActivityComponent;
import com.example.di.components.DaggerActivityComponent;
import com.example.di.modules.ActivityModule;
import com.example.utils.DialogUtils;

import java.util.List;

import butterknife.Unbinder;
import lombok.Getter;

/**
 * Родительская активити для всех активити в проекте
 */

@Getter
public abstract class BaseActivity extends AppCompatActivity
        implements MvpView, BaseFragment.Callback, BaseViewHolder.BaseAdapterListener {

    private Dialog progressDialog;

    private ActivityComponent mActivityComponent;

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    private ResourceProvider resourceProvider;

    private Unbinder unBinder;

    private boolean keyBoardVisible = false;


    /**
     * Название экрана для сохранения в Google Analytics
     */
    protected String screenName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(SampleVpnApplication.get(this).getApplicationComponent())
                .build();

        resourceProvider = SampleVpnApplication.get(this).getResourceProvider();
    }


    @Override
    public void onClick(Object o) {
        //
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void showProgress() {
        showProgress("");
    }

    @Override
    public void showProgress(String message) {
        hideProgress();
        progressDialog = DialogUtils.showLoadingDialog(this, message);
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int res) {
        showMessage(getResources().getString(res));
    }


    @Override
    public ResourceProvider getResourceProvider() {
        return resourceProvider;
    }

    public boolean isKeyboardShown(View view) {
        View rootView = view.getRootView();
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        int heightDiff = rootView.getBottom() - r.bottom;
        Log.i("heightDiff", "rv.bot=" + rootView.getBottom() + "; r.bot=" + r.bottom + "; diff=" + heightDiff);
        return heightDiff > softKeyboardHeight * SampleVpnApplication.metrics.density;
    }

    @Override
    public void showSoftKeyboard(View view) {
        if (view == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    @Override
    public void hideKeyboard(TextView v, int actionId, List<EditText> editTexts) {
        if ((actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            v.clearFocus();

            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                for (EditText editText : editTexts) {
                    editText.clearFocus();
                }
            }

            keyBoardVisible = false;

        }

    }

    public void setUnBinder(Unbinder unBinder) {
        this.unBinder = unBinder;
    }

    @Override
    protected void onDestroy() {

        hideProgress();

        if (unBinder != null) {
            unBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public boolean isKeyBoardVisible() {
        return keyBoardVisible;
    }

    public void setKeyBoardVisible(boolean keyBoardVisible) {
        this.keyBoardVisible = keyBoardVisible;
    }

    @Override
    public void onActionPerformed(Object object) {

    }

    @Override
    public void copyToClipboard(String label, String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText(label, text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
    }

    @Override
    public void setActions() {

    }

    @Override
    public void clearFocus() {

    }

    protected Bundle makeAnimation(){
        return ActivityOptionsCompat.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent, makeAnimation());
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode, makeAnimation());
    }
}