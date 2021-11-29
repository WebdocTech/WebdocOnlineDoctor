package com.wmalick.webdoc_library.InitiateSDK.modelclasses.KK_modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllocateDoctorModel {

    @SerializedName("AllocateDoctorResult")
    @Expose
    private AllocateDoctorResult allocateDoctorResult;

    public AllocateDoctorResult getAllocateDoctorResult() {
        return allocateDoctorResult;
    }

    public void setAllocateDoctorResult(AllocateDoctorResult allocateDoctorResult) {
        this.allocateDoctorResult = allocateDoctorResult;
    }

}