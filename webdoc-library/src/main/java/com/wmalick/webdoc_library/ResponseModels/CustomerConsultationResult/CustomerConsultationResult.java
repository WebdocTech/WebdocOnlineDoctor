
package com.wmalick.webdoc_library.ResponseModels.CustomerConsultationResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerConsultationResult {

    @SerializedName("CustomerConsultationResult")
    @Expose
    private CustomerConsultationResult_ customerConsultationResult;

    public CustomerConsultationResult_ getCustomerConsultationResult() {
        return customerConsultationResult;
    }

    public void setCustomerConsultationResult(CustomerConsultationResult_ customerConsultationResult) {
        this.customerConsultationResult = customerConsultationResult;
    }

}
