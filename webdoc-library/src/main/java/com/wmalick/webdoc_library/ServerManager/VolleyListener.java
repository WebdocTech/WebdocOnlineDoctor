package com.wmalick.webdoc_library.ServerManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 7/3/2019.
 */

public interface VolleyListener {

    /* These methods will be called when you implement interface on activity at top */
    public void getRequestResponse(JSONObject response, String requestedApi) throws JSONException;
}
