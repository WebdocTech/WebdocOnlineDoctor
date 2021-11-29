package com.wmalick.webdoc_library.InitiateSDK.modelclasses.KK_modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllocateDoctorResult {

    @SerializedName("doctorDeviceToken")
    @Expose
    private String doctorDeviceToken;
    @SerializedName("doctorEmail")
    @Expose
    private String doctorEmail;
    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;

    public String getDoctorDeviceToken() {
        return doctorDeviceToken;
    }

    public void setDoctorDeviceToken(String doctorDeviceToken) {
        this.doctorDeviceToken = doctorDeviceToken;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}