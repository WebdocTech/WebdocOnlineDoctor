package com.wmalick.webdoc_library.Dashboard.modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LawyerModel {

    @SerializedName("AllocateLawyerResult")
    @Expose
    private AllocateLawyerResult allocateLawyerResult;

    public AllocateLawyerResult getAllocateLawyerResult() {
        return allocateLawyerResult;
    }

    public void setAllocateLawyerResult(AllocateLawyerResult allocateLawyerResult) {
        this.allocateLawyerResult = allocateLawyerResult;
    }

}
