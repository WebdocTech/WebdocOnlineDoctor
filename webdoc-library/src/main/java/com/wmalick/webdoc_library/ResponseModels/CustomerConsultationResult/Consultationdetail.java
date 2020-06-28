
package com.wmalick.webdoc_library.ResponseModels.CustomerConsultationResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Consultationdetail {

    @SerializedName("additionalNotes")
    @Expose
    private String additionalNotes;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("medicineName")
    @Expose
    private String medicineName;
    @SerializedName("morning")
    @Expose
    private String morning;
    @SerializedName("night")
    @Expose
    private String night;
    @SerializedName("noOfDays")
    @Expose
    private String noOfDays;

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

}
