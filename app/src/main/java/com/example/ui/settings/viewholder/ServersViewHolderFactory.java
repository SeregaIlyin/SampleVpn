package com.example.ui.settings.viewholder;

import android.view.ViewGroup;

import com.example.ui.base.BaseViewHolder;
import com.example.ui.settings.ServersListAdapter;

public interface ServersViewHolderFactory {
    BaseViewHolder createViewHolder(ServersListAdapter serversListAdapter, ViewGroup parent);
}
