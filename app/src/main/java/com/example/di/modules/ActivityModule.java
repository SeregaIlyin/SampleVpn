package com.example.di.modules;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.di.annotation.ActivityContext;
import com.example.ui.base.BaseActivity;
import com.example.ui.base.BaseViewHolder;
import com.example.ui.settings.ServersListAdapter;
import com.example.ui.settings.viewholder.ServersViewHolderFactory;
import com.example.ui.settings.viewholder.ServersViewHolderItemFactory;
import com.example.utils.Constants;

import java.util.Map;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private BaseActivity baseActivity;
    private BaseViewHolder.BaseAdapterListener listener;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        this.listener = baseActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return baseActivity;
    }

    @Provides
    BaseViewHolder.BaseAdapterListener provideBaseAdapterListener() {
        return listener;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return baseActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(baseActivity);
    }

    @Provides
    ServersListAdapter provideServersListAdapter(BaseViewHolder.BaseAdapterListener listener,
                                                 Map<Integer, ServersViewHolderFactory> viewHolderFactories) {
        return new ServersListAdapter(listener, viewHolderFactories);
    }

    @Provides
    @IntoMap
    @IntKey(Constants.SERVER_ITEM)
    ServersViewHolderFactory provideServersListViewHolderItem() {
        return new ServersViewHolderItemFactory();
    }
}


