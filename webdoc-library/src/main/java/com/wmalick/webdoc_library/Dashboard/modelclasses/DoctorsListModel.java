package com.wmalick.webdoc_library.Dashboard.modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorsListModel {
    @SerializedName("DoctorListResult")
    @Expose
    private DoctorListResult doctorListResult;

    public DoctorListResult getDoctorListResult() {
        return doctorListResult;
    }

    public void setDoctorListResult(DoctorListResult doctorListResult) {
        this.doctorListResult = doctorListResult;
    }

}
