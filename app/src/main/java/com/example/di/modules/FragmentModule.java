package com.example.di.modules;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.di.annotation.ActivityContext;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mFragment.getContext();
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

}
