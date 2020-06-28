
package com.wmalick.webdoc_library.ResponseModels.GetCustomerData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCustomerDataApiResponse {

    @SerializedName("GetcustomerDataResult")
    @Expose
    private GetcustomerDataResult getcustomerDataResult;

    public GetcustomerDataResult getGetcustomerDataResult() {
        return getcustomerDataResult;
    }

    public void setGetcustomerDataResult(GetcustomerDataResult getcustomerDataResult) {
        this.getcustomerDataResult = getcustomerDataResult;
    }

}
