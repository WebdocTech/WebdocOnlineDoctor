package com.wmalick.webdoc_library.ServerManager;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;

    private VolleySingleton(Context context, HurlStack certificate) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext(), certificate);
    }

    public static synchronized VolleySingleton getInstance(Context context, HurlStack certificate) {
        if(mInstance == null) {
            mInstance = new VolleySingleton(context, certificate);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
