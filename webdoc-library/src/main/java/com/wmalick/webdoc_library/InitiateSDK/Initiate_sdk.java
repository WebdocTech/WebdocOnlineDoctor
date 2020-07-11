package com.wmalick.webdoc_library.InitiateSDK;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wmalick.webdoc_library.Dashboard.WebdocDashboardActivity;
import com.wmalick.webdoc_library.Essentials.Constants;
import com.wmalick.webdoc_library.Essentials.Global;

import org.json.JSONException;
import org.json.JSONObject;

import com.wmalick.webdoc_library.ResponseModels.GetCustomerData.GetCustomerDataApiResponse;
import com.wmalick.webdoc_library.ServerManager.ServerManager;
import com.wmalick.webdoc_library.ServerManager.VolleyListener;

public class Initiate_sdk implements VolleyListener {
    Activity ctx;
    private String UserMobileNumber, Corporate;
    ServerManager serverManager;


    public Initiate_sdk(Activity ctx, String MobileNumber, String Corporate) {
        this.ctx = ctx;
        this.UserMobileNumber = MobileNumber;
        this.Corporate = Corporate;
        serverManager = new ServerManager(ctx, this);
        InitiateConnection();
    }

    private void InitiateConnection() {
        Global.utils.showProgressDialog(ctx, "Initiating Connection");
        serverManager.GetCustomerData(UserMobileNumber, Corporate);
    }


    @Override
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException {
        Global.utils.hideProgressDialog();
        Gson gson = new Gson();
        GetCustomerDataApiResponse responseModel = gson.fromJson(response.toString(), GetCustomerDataApiResponse.class);
        if(responseModel.getGetcustomerDataResult().getResponseCode().equals(Constants.SUCCESSCODE)){
            Global.getCustomerDataApiResponse = responseModel;
            ctx.startActivity(new Intent(ctx, WebdocDashboardActivity.class));
            /*ctx.finish();*/
        }else{
            Toast.makeText(ctx, responseModel.getGetcustomerDataResult().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
