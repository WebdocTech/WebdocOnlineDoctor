
package com.wmalick.webdoc_library.ResponseModels.CustomerConsultationResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerConsultationResult_ {

    @SerializedName("ConusltationList")
    @Expose
    private List<ConusltationList> conusltationList = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;

    public List<ConusltationList> getConusltationList() {
        return conusltationList;
    }

    public void setConusltationList(List<ConusltationList> conusltationList) {
        this.conusltationList = conusltationList;
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
