package com.wmalick.webdoc_library.Essentials;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public ProgressDialog progressDialog;
    public MediaPlayer mediaPlayer;
    public AudioManager audioManager;

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

    public void sendNotification(Activity activity, String token, JSONObject params){
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
        mediaPlayer = MediaPlayer.create(activity, tone);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.start();
    }


    public void stopMediaPlayer()
    {
        mediaPlayer.stop();
    }

}
