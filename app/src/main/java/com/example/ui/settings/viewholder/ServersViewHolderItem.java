package com.example.ui.settings.viewholder;

import android.graphics.Bitmap;
import android.graphics.drawable.VectorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ResourceProvider;
import com.example.SampleVpnApplication;
import com.example.model.VpnServer;
import com.example.samplevpn.R;
import com.example.ui.base.BaseViewHolder;
import com.example.ui.settings.ServersListAdapter;
import com.example.utils.CommonUtils;
import com.google.auto.factory.AutoFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;
import lombok.Setter;

@AutoFactory(implementing = ServersViewHolderFactory.class)
@Getter
@Setter
public class ServersViewHolderItem extends BaseViewHolder {

    @BindView(R.id.img_country)
    ImageView imgCountry;

    @BindView(R.id.txt_country)
    TextView txtCountry;

    @BindView(R.id.txt_ip)
    TextView txtIp;

    private ServersListAdapter serversListAdapter;
    private VpnServer currentVpnServer;
    private ResourceProvider resourceProvider;

    public ServersViewHolderItem(ServersListAdapter serversListAdapter, ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_server, parent, false));
        setResourceProvider(SampleVpnApplication.get(parent.getContext()).getResourceProvider());
        setServersListAdapter(serversListAdapter);

        ButterKnife.bind(this, itemView);
    }

    protected void clear() {
        imgCountry.setImageBitmap(null);
        txtCountry.setText("");
        txtIp.setText("");
    }

    public void onBind(int position) {
        super.onBind(position);
        setCurrentVpnServer(getServersListAdapter().getServerObjects().get(position));
        Bitmap res = CommonUtils.getBitmap((VectorDrawable)SampleVpnApplication.getContext().getResources().getDrawable(getCurrentVpnServer().getFlag()));
        Bitmap flag = CommonUtils.getCircleMaskedBitmapUsingClip(res,24);

        imgCountry.setImageBitmap(flag);
        txtCountry.setText(getCurrentVpnServer().getCountry());
        txtIp.setText(String.format("%s : %s",getCurrentVpnServer().getIp(),getCurrentVpnServer().getPort()));
    }

    @OnClick(R.id.view_server)
    public void onClick() {
        getServersListAdapter().getListener().onClick(getCurrentVpnServer());
    }

}
