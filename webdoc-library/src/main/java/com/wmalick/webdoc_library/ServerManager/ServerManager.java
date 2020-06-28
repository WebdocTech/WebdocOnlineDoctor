package com.wmalick.webdoc_library.ServerManager;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wmalick.webdoc_library.Essentials.Constants;
import com.wmalick.webdoc_library.FormModels.SubmitWebdocFeedbackFormModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 7/3/2019.
 */

public class ServerManager {
    
    public static Activity ctx;
    public static RequestQueue requestQueue;
    VolleyListener volleyListener;
    
    public ServerManager(Activity ctx, VolleyListener volleyListener) {
        this.ctx = ctx;
        this.volleyListener = volleyListener;
    }

    public void GetDoctorsList() {
        String url = Constants.BASE_URL+Constants.DOCTORS_LIST_API;
        JSONObject params = new JSONObject();
        try {
            params.put("Key", Constants.doctorsKey);
            params.put("City", Constants.patientCity);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error : GetDoctorsList "+e.toString());
        }

        jsonParse("abcjsdlkfjslajfkdsj", url, Constants.DOCTORS_LIST_API, params, Request.Method.POST);
    }

    public void GetCustomerData(String userNumber, String corporate) {
        String url = Constants.BASE_URL+Constants.GET_CUSTOMER_DATA;
        JSONObject params = new JSONObject();
        try {
            params.put("CustomerMobilenumber", userNumber);
            params.put("Corporate", corporate);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error : GetCustomerData "+e.toString());
        }

        jsonParse("abcjsdlkfjslajfkdsj", url, Constants.GET_CUSTOMER_DATA, params, Request.Method.POST);
    }

    public void GetPrescriptionHistory(String userId) {
        String url = Constants.BASE_URL+Constants.CUSTOMER_CONSULTATION_API;
        JSONObject params = new JSONObject();
        try {
            params.put("CustomerId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error : GetPrescriptionHistory "+e.toString());
        }

        jsonParse("abcjsdlkfjslajfkdsj", url, Constants.CUSTOMER_CONSULTATION_API, params, Request.Method.POST);
    }

    public void SubmitWebdocFeedBack(SubmitWebdocFeedbackFormModel dataModel) {
        String url = Constants.BASE_URL+Constants.WEBDOC_FEEDBACK;
        JSONObject params = new JSONObject();
        try {
            params.put("CustomerId", dataModel.getCustomerId());
            params.put("feeback", dataModel.getFeeback());
            params.put("rating", dataModel.getRating());
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error : SubmitWebdocFeedBack "+e.toString());
        }

        jsonParse("abcjsdlkfjslajfkdsj", url, Constants.WEBDOC_FEEDBACK, params, Request.Method.POST);
    }


    
    public void jsonParse(final String authToken, String url, final String requestedApi, final JSONObject obj, int post)
    {
        /* Callback interface to wait for response */


        /* SSL Verification Certificate */
        SSLVerification sslVerification = new SSLVerification(ctx);

        //Volley.newRequestQueue(ctx, sslVerification.Certificate());
        requestQueue = VolleySingleton.getInstance(ctx, sslVerification.Certificate()).getRequestQueue();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(post,
                url, obj,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            /* Calling VolleyListener interface method and returning responseCode */
                            volleyListener.getRequestResponse(response, requestedApi);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                //Log.e("ErrorResponse", error.getMessage());
                Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* Passing some request headers */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + authToken);
                return headers;
            }
        };

        // Adding request to request queue
        requestQueue.add(jsonObjReq);
    }
}
