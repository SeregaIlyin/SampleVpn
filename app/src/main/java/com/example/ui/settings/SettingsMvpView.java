package com.example.ui.settings;

import com.example.model.VpnServer;
import com.example.ui.base.MvpView;

import java.util.ArrayList;

public interface SettingsMvpView extends MvpView {

    void selectServer(VpnServer value);

    void refreshList(ArrayList<VpnServer> servers);
}
