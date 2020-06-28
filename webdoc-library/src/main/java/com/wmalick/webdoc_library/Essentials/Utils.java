package com.wmalick.webdoc_library.Essentials;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.wmalick.webdoc_library.R;

public class Utils {

    public ProgressDialog progressDialog;

    public void showProgressDialog(Activity activity, String message)
    {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ProgressBar progressbar = (ProgressBar) progressDialog.findViewById(android.R.id.progress);
        progressbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#d32e33"), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public void hideProgressDialog()
    {
        if(progressDialog.isShowing())
        {
            progressDialog.hide();
        }
    }

    public void showSuccessSnakeBar(Activity activity, String msg) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setActionTextColor(activity.getResources().getColor(R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(activity.getResources().getColor(R.color.green));
        snackbar.show();
    }

    public void showErrorSnakeBar(Activity activity, String msg) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setActionTextColor(activity.getResources().getColor(R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(activity.getResources().getColor(R.color.red));
        snackbar.show();
    }

}
