package com.example.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.VpnServer;
import com.example.samplevpn.R;
import com.example.ui.base.BaseActivity;
import com.example.utils.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity implements SettingsMvpView {

    @Inject
    SettingsPresenter<SettingsMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_servers)
    RecyclerView recyclerViewServers;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Inject
    ServersListAdapter serversListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);
        setUnBinder(ButterKnife.bind(this));
        setSupportActionBar(toolbar);
        mPresenter.setUp();
        recyclerViewServers.setHasFixedSize(true);
        recyclerViewServers.setLayoutManager(linearLayoutManager);
        recyclerViewServers.setAdapter(serversListAdapter);
    }


    @Override
    public void onDestroy() {
        if(mPresenter != null) {
            mPresenter.onDetach();
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Object o) {
        mPresenter.onItemClick(o);
    }

    @Override
    public void selectServer(VpnServer value) {

        Intent answerIntent = new Intent();
        answerIntent.putExtra(Constants.VPN_SERVER, value);
        setResult(Activity.RESULT_OK, answerIntent);
        finish();
    }

    @Override
    public void refreshList(ArrayList<VpnServer> servers) {
        serversListAdapter.updateItems(servers);
    }
}
