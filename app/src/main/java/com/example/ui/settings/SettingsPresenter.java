package com.example.ui.settings;

import com.example.SampleVpnApplication;
import com.example.model.VpnServer;
import com.example.samplevpn.R;
import com.example.ui.base.BasePresenter;
import com.example.utils.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SettingsPresenter<V extends SettingsMvpView> extends BasePresenter<V> implements SettingsMvpPresenter<V> {

    private ArrayList<VpnServer> resultList = new ArrayList<>();

    @Inject
    SettingsPresenter(CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
    }

    @Override
    public void setUp() {
        resultList.clear();

        VpnServer vpnServer = new VpnServer();
        vpnServer.setFlag(R.drawable.ic_australia);
        vpnServer.setCountry("Australia");
        vpnServer.setIp("1.0.0.193");
        vpnServer.setPort("80");
        vpnServer.setType(Constants.SERVER_ITEM);
        resultList.add(vpnServer);

        vpnServer = new VpnServer();
        vpnServer.setFlag(R.drawable.ic_france);
        vpnServer.setCountry("France");
        vpnServer.setIp("163.172.189.32");
        vpnServer.setPort("8811");
        vpnServer.setType(Constants.SERVER_ITEM);
        resultList.add(vpnServer);

        vpnServer = new VpnServer();
        vpnServer.setFlag(R.drawable.ic_germany);
        vpnServer.setCountry("Germany");
        vpnServer.setIp("154.16.202.22");
        vpnServer.setPort("8080");
        vpnServer.setType(Constants.SERVER_ITEM);
        resultList.add(vpnServer);

        vpnServer = new VpnServer();
        vpnServer.setFlag(R.drawable.ic_russia);
        vpnServer.setCountry("Russia");
        vpnServer.setIp("62.33.207.196");
        vpnServer.setPort("80");
        vpnServer.setType(Constants.SERVER_ITEM);
        resultList.add(vpnServer);
        getMvpView().refreshList(resultList);
    }

    @Override
    public void onItemClick(Object o) {
        if(o instanceof VpnServer) {
            getMvpView().selectServer((VpnServer) o);
        }
    }
}
