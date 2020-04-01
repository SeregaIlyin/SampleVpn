package com.example.ui.settings;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.VpnServer;
import com.example.ui.base.BaseViewHolder;
import com.example.ui.settings.viewholder.ServersViewHolderFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServersListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<VpnServer> serverObjects;
    private BaseViewHolder.BaseAdapterListener listener;
    private Map<Integer, ServersViewHolderFactory> viewHolderFactories;
    private String editedItemId;

    public ServersListAdapter(BaseViewHolder.BaseAdapterListener listener,
                              Map<Integer, ServersViewHolderFactory> viewHolderFactories) {
        setServerObjects(new ArrayList<>());
        setListener(listener);
        setViewHolderFactories(viewHolderFactories);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return Objects.requireNonNull(getViewHolderFactories().get(i)).createViewHolder(this, viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemViewType(int position) {
        return getServerObjects().get(position).getType();
    }

    @Override
    public int getItemCount() {
        return getServerObjects().size();
    }

    void updateItems(ArrayList<VpnServer> serverObjects) {
        getServerObjects().clear();
        getServerObjects().addAll(serverObjects);
        notifyDataSetChanged();
    }

}
