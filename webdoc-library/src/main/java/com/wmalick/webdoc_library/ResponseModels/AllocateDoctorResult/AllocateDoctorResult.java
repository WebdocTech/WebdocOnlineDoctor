
package com.wmalick.webdoc_library.ResponseModels.AllocateDoctorResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllocateDoctorResult {

    @SerializedName("AllocateDoctorResult")
    @Expose
    private AllocateDoctorResult_ allocateDoctorResult;

    public AllocateDoctorResult_ getAllocateDoctorResult() {
        return allocateDoctorResult;
    }

    public void setAllocateDoctorResult(AllocateDoctorResult_ allocateDoctorResult) {
        this.allocateDoctorResult = allocateDoctorResult;
    }

}
