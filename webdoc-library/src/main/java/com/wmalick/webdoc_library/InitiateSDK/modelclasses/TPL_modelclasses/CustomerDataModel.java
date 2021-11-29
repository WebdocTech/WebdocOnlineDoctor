package com.wmalick.webdoc_library.InitiateSDK.modelclasses.TPL_modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDataModel {

    @SerializedName("GetcustomerDataResult")
    @Expose
    private GetcustomerDataResult getcustomerDataResult;

    public GetcustomerDataResult getGetcustomerDataResult() {
        return getcustomerDataResult;
    }

    public void setGetcustomerDataResult(GetcustomerDataResult getcustomerDataResult) {
        this.getcustomerDataResult = getcustomerDataResult;
    }

}