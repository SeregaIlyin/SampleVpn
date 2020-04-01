package com.example.utils;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.samplevpn.R;
import com.example.ui.components.circularprogressbar.CircularProgressBar;

public class DialogUtils {

    private DialogUtils() {
        // This utility class is not publicly instantiable
    }

    public static AlertDialog showLoadingDialog(Context context, String message) {

        try {
            //AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.Bitlish_NoActionBar));
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            View dialogView = View.inflate(context, R.layout.progress_dialog, null);
            dialogBuilder.setView(dialogView);
            CircularProgressBar circularProgressBar = dialogView.findViewById(R.id.pb_loading);
            TextView txtMessage = dialogView.findViewById(R.id.txt_message);
            txtMessage.setText(message);

            circularProgressBar.setProgressWithAnimation(65);
            AlertDialog progressDialog = dialogBuilder.create();

            Window window = progressDialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.TOP;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                wlp.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                window.setAttributes(wlp);
                //window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            }
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.show();
            return progressDialog;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
