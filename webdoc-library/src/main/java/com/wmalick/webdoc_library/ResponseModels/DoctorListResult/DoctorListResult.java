
package com.wmalick.webdoc_library.ResponseModels.DoctorListResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorListResult {

    @SerializedName("DoctorListResult")
    @Expose
    private DoctorListResult_ doctorListResult;

    public DoctorListResult_ getDoctorListResult() {
        return doctorListResult;
    }

    public void setDoctorListResult(DoctorListResult_ doctorListResult) {
        this.doctorListResult = doctorListResult;
    }

}
