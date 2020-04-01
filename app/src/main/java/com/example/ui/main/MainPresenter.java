package com.example.ui.main;

import android.graphics.Bitmap;
import android.graphics.drawable.VectorDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.example.SampleVpnApplication;
import com.example.model.VpnServer;
import com.example.samplevpn.R;
import com.example.ui.base.BasePresenter;
import com.example.utils.CommonUtils;
import com.example.utils.Constants;
import com.example.utils.ResizeAnimation;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    @Inject
    MainPresenter(CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
    }

    private VpnServer currentVpnServer;
    private Disposable timerDisposable;
    private int initialHeight;
    private int initialWidth;
    private View animatedView;
    private boolean connected;

    @Override
    public void setUp() {
        getMvpView().setBtnConnectEnabled(false);
        setConnected(false);
    }

    @Override
    public void onSettingsWasClicked() {
        getMvpView().showSettingsScreen();
    }

    @Override
    public void onConnectWasClicked(View view) {
        if (isConnected()) {
            disconnect();
        } else {
            connect(view);
        }
    }

    private void connect(View view){
        getMvpView().setBtnConnectEnabled(false);
        getMvpView().setBtnConnectTitle(SampleVpnApplication.getContext().getString(R.string.activity_main_btn_connecting));
        setAnimatedView(view);
        setInitialHeight(view.getHeight());
        setInitialWidth(view.getWidth());

        setTimerDisposable(Observable
                .interval(0, 305, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::animate));

        getCompositeDisposable().add(getTimerDisposable());
    }

    private void disconnect(){
        setConnected(false);
        getMvpView().setBtnConnectTitle(SampleVpnApplication.getContext().getString(R.string.activity_main_btn_connect));
        getMvpView().showMessage("Disconnected");
    }

    private void animate(final Long aLong){
        ResizeAnimation animation;
        if (aLong == 16) {
            setConnected(true);
            timerDisposable.dispose();
            animation = new ResizeAnimation(getAnimatedView(),
                    getAnimatedView().getWidth(),
                    getAnimatedView().getHeight(),
                    getInitialWidth(),
                    getInitialHeight());
            animation.setDuration(Constants.ANIMATION_TIME);
            animation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    getMvpView().setBtnConnectEnabled(true);
                    getMvpView().setBtnConnectTitle(SampleVpnApplication.getContext().getString(R.string.activity_main_btn_disconnect));
                    getMvpView().showMessage(String.format("Connected to %s", getCurrentVpnServer().getIp()));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            getMvpView().startAnimation(animation);
        }else{
            boolean f = aLong % 2 == 0;
            animation = new ResizeAnimation(getAnimatedView(),
                    getAnimatedView().getWidth(),
                    getAnimatedView().getHeight(),
                    f ? getAnimatedView().getWidth() >> 1 : getAnimatedView().getWidth() << 1,
                    f ? getAnimatedView().getHeight() >> 1 : getAnimatedView().getHeight() << 1);
            animation.setDuration(Constants.ANIMATION_TIME);
            getMvpView().startAnimation(animation);
        }
    }

    @Override
    public void onSettingsChanged(VpnServer vpnServer) {
        if(vpnServer != null){
            setCurrentVpnServer(vpnServer);
            Bitmap res = CommonUtils.getBitmap((VectorDrawable)SampleVpnApplication.getContext().getResources().getDrawable(getCurrentVpnServer().getFlag()));
            Bitmap flag = CommonUtils.getCircleMaskedBitmapUsingClip(res,24);
            getMvpView().setCountry(flag);
            getMvpView().setServer(getCurrentVpnServer().getIp());
            getMvpView().setBtnConnectEnabled(true);
        }
    }
}
