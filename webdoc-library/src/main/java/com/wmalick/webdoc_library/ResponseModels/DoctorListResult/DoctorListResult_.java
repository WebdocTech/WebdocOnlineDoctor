
package com.wmalick.webdoc_library.ResponseModels.DoctorListResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorListResult_ {

    @SerializedName("Doctorprofiles")
    @Expose
    private List<Doctorprofile> doctorprofiles = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;

    public List<Doctorprofile> getDoctorprofiles() {
        return doctorprofiles;
    }

    public void setDoctorprofiles(List<Doctorprofile> doctorprofiles) {
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
