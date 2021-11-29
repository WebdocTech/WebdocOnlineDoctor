package com.wmalick.webdoc_library.InitiateSDK.modelclasses.customerdoctor_modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetcustomerDataSdkResult {
    @SerializedName("CustomerProfile")
    @Expose
    private CustomerProfile customerProfile;
    @SerializedName("Doctorprofiles")
    @Expose
    private Doctorprofiles doctorprofiles;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;

    public CustomerProfile getCustomerProfile() {
        return customerProfile;
    }

    public void setCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
    }

    public Doctorprofiles getDoctorprofiles() {
        return doctorprofiles;
    }

    public void setDoctorprofiles(Doctorprofiles doctorprofiles) {
        this.doctorprofiles = doctorprofiles;
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