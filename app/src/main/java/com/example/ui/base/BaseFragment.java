package com.example.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ResourceProvider;
import com.example.SampleVpnApplication;
import com.example.di.components.ActivityComponent;
import com.example.di.components.DaggerFragmentComponent;
import com.example.di.components.FragmentComponent;
import com.example.di.modules.FragmentModule;

import java.util.List;

import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements MvpView {
    private BaseActivity mActivity;
    private Unbinder mUnBinder;
    private FragmentComponent mFragmentComponent;


    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }

    @Override
    public void showProgress() {
        showProgress("");
    }

    @Override
    public void showProgress(String message) {
        if (mActivity != null) {
            mActivity.showProgress();
        }
    }

    @Override
    public void hideProgress() {
        if (mActivity != null) {
            mActivity.hideProgress();
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(int res) {
        if (mActivity != null) {
            mActivity.showMessage(res);
        }
    }

    @Override
    public ResourceProvider getResourceProvider() {
        return mActivity != null ? SampleVpnApplication.get(mActivity).getResourceProvider() : null;
    }

    @Override
    public boolean isKeyBoardVisible() {
        return mActivity != null && mActivity.isKeyBoardVisible();
    }

    @Override
    public void setKeyBoardVisible(boolean keyBoardVisible) {
        if (mActivity != null) {
            mActivity.setKeyBoardVisible(keyBoardVisible);
        }
    }

    @Override
    public boolean isKeyboardShown(View view) {
        return mActivity != null && mActivity.isKeyboardShown(view);
    }

    @Override
    public void showSoftKeyboard(View view) {
        if (mActivity != null) {
            mActivity.showSoftKeyboard(view);
        }
    }

    @Override
    public void hideKeyboard(TextView v, int actionId, List<EditText> editTexts) {
        if (mActivity != null) {
            mActivity.hideKeyboard(v, actionId, editTexts);
        }
    }

    @Override
    public void onActionPerformed(Object object) {
        if (mActivity != null) {
            mActivity.onActionPerformed(object);
        }
    }

    @Override
    public boolean hasPermission(String permission) {
        boolean result = false;
        if (mActivity != null) {
            result = mActivity.hasPermission(permission);
        }

        return result;
    }

    @Override
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (mActivity != null) {
            mActivity.requestPermissionsSafely(permissions,requestCode);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        mFragmentComponent = DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .applicationComponent(SampleVpnApplication.get(this.requireContext()).getApplicationComponent())
                .build();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }


    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void copyToClipboard(String label, String text) {
        if (mActivity != null) {
            mActivity.copyToClipboard(label,text);
        }
    }

    @Override
    public void setActions() {
        if (mActivity != null) {
            mActivity.setActions();
        }
    }

    @Override
    public void clearFocus() {
        if (mActivity != null) {
            mActivity.clearFocus();
        }
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    protected abstract void setUp(View view);

    @Override
    public void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }


}
