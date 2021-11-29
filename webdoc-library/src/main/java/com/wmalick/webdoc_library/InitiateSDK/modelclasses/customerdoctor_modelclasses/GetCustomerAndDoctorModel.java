package com.wmalick.webdoc_library.InitiateSDK.modelclasses.customerdoctor_modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCustomerAndDoctorModel {

    @SerializedName("GetcustomerDataSdkResult")
    @Expose
    private GetcustomerDataSdkResult getcustomerDataSdkResult;

    public GetcustomerDataSdkResult getGetcustomerDataSdkResult() {
        return getcustomerDataSdkResult;
    }

    public void setGetcustomerDataSdkResult(GetcustomerDataSdkResult getcustomerDataSdkResult) {
        this.getcustomerDataSdkResult = getcustomerDataSdkResult;
    }

}