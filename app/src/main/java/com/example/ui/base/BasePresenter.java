package com.example.ui.base;

import android.view.View;

import com.example.ResourceProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import lombok.Data;

@Data
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private V mvpView;
    private ResourceProvider resourceProvider;
    private final CompositeDisposable compositeDisposable;
    private boolean startScreen = false;
    private boolean doStop = false;

    @Inject
    public BasePresenter(CompositeDisposable compositeDisposable) {
        super();
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;
        this.resourceProvider = getMvpView().getResourceProvider();
    }

    @Override
    public void onDetach() {
        mvpView = null;
        compositeDisposable.dispose();
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }


    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    @Override
    public void onGlobalLayout(View view) {
        if (getMvpView().isKeyboardShown(view)) {
            //keyboard up
            if (!getMvpView().isKeyBoardVisible()) {
                getMvpView().setActions();
                getMvpView().setKeyBoardVisible(true);
            }
        } else {
            //keyboard down
            if (getMvpView().isKeyBoardVisible()) {
                getMvpView().clearFocus();
            }
        }
    }
}
