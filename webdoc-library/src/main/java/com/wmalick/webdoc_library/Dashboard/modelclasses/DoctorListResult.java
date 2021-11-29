package com.wmalick.webdoc_library.Dashboard.modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DoctorListResult {

    @SerializedName("Doctorprofiles")
    @Expose
    private ArrayList<Doctorprofile> doctorprofiles = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;

    public ArrayList<Doctorprofile> getDoctorprofiles() {
        return doctorprofiles;
    }

    public void setDoctorprofiles(ArrayList<Doctorprofile> doctorprofiles) {
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