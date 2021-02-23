package com.wmalick.webdoc_library.Essentials;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.wmalick.webdoc_library.Agora.Constant;
import com.wmalick.webdoc_library.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public ProgressDialog progressDialog;
    public MediaPlayer mediaPlayer;
    public Boolean isProgressDialogShowing = false;

    public void showProgressDialog(Activity activity, String message)
    {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ProgressBar progressbar = (ProgressBar) progressDialog.findViewById(android.R.id.progress);
        progressbar.getIndeterminateDrawable().setColorFilter(Color.parseColor(Global.THEME_COLOR_CODE), android.graphics.PorterDuff.Mode.SRC_IN);
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

    public void sendNotification(Activity activity, JSONObject params){
        final RequestQueue requestQueue = Volley.newRequestQueue(activity);
        String url= Constants.FIREBASE_NOTIFICATION_URL;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Debugging", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Debugging", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.FIREBASE_SERVER_KEY);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(request);
    }

    public void startMediaPlayer(Activity activity, int tone)
    {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(activity, Uri.parse("android.resource://com.wmalick.webdocandroidlibrary/" + tone));
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void stopMediaPlayer()
    {
        mediaPlayer.stop();
    }

    public void callNotAnsweredDialog(Activity activity, String doctorName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        View v = activity.getLayoutInflater().inflate(R.layout.alert_call_not_answered, null);
        dialogBuilder.setView(v);

        AlertDialog callNotAnsweredAlertDialog = dialogBuilder.create();
        callNotAnsweredAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final TextView textView = v.findViewById(R.id.textView2);
        textView.setText(doctorName + " couldn't receive call at the moment.");


        Button submit = v.findViewById(R.id.btn_ok);
        submit.getBackground().setTint(Color.parseColor(Global.THEME_COLOR_CODE));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNotAnsweredAlertDialog.dismiss();
            }
        });

        callNotAnsweredAlertDialog.setCancelable(false);
        callNotAnsweredAlertDialog.show();
    }//alert

}
