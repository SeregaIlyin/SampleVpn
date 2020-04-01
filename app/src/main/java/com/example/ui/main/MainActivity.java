package com.example.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.model.VpnServer;
import com.example.samplevpn.R;
import com.example.ui.base.BaseActivity;
import com.example.ui.settings.SettingsActivity;
import com.example.utils.Constants;
import com.example.utils.ResizeAnimation;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainPresenter<MainMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.img_country)
    ImageView imgCountry;

    @BindView(R.id.txt_server)
    TextView txtServer;

    @BindView(R.id.btn_connect)
    Button btnConect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);
        setUnBinder(ButterKnife.bind(this));
        setSupportActionBar(toolbar);
        mPresenter.setUp();
    }

    @Override
    public void onDestroy() {
        if(mPresenter != null) {
            mPresenter.onDetach();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0,R.id.action_settings, Menu.NONE,R.string.action_settings);
        if(!txtServer.getText().equals(getResources().getText(R.string.activity_main_txt_select))) {
            menu.add(0, R.id.action_settings + 1, Menu.NONE, txtServer.getText());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            mPresenter.onSettingsWasClicked();
            return true;
        }else if(id == R.id.action_settings+1){
            showMessage(item.getTitle().toString());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSettingsScreen() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivityForResult(intent, Constants.SETTINGS_RESULT);
    }

    @Override
    public void setCountry(Bitmap flag) {
        imgCountry.setImageBitmap(flag);
    }

    @Override
    public void startAnimation(Animation animation) {
        btnConect.startAnimation(animation);
    }

    @Override
    public void setServer(String value) {
        txtServer.setText(value);
    }

    @Override
    public void setBtnConnectEnabled(boolean enabled) {
        btnConect.setEnabled(enabled);
    }

    @Override
    public void setBtnConnectTitle(String title) {
        btnConect.setText(title);
    }

    @OnClick(R.id.btn_connect)
    void onConnectClick(View view){
        mPresenter.onConnectWasClicked(view);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SETTINGS_RESULT) {
            if (resultCode == RESULT_OK) {
                VpnServer vpnServer = (VpnServer)data.getSerializableExtra(Constants.VPN_SERVER);
                mPresenter.onSettingsChanged(vpnServer);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
