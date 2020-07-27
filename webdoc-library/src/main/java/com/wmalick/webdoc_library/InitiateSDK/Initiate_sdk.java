package com.wmalick.webdoc_library.InitiateSDK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
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

    /*Initialize SDK*/
    public Initiate_sdk(Activity ctx, String MobileNumber, String Corporate, String themeColorHexCode) {
        if(TextUtils.isEmpty(themeColorHexCode)){
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        }else if(themeColorHexCode.charAt(0) != '#'){
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        }else if(themeColorHexCode.length() < 7){
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        }else{
            Global.THEME_COLOR_CODE = themeColorHexCode;
            Global.corporate = Corporate;
            this.ctx = ctx;
            this.UserMobileNumber = MobileNumber;
            this.Corporate = Corporate;
            serverManager = new ServerManager(ctx, this);
            InitiateConnection();
        }
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
