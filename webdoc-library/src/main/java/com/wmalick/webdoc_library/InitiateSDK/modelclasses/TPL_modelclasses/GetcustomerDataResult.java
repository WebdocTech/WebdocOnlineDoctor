package com.wmalick.webdoc_library.InitiateSDK.modelclasses.TPL_modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetcustomerDataResult {

    @SerializedName("CustomerData")
    @Expose
    private CustomerData customerData;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;

    public CustomerData getCustomerData() {
        return customerData;
    }

    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

}