
package com.wmalick.webdoc_library.ResponseModels.WebdocFeedback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebdocFeedbackApiResponse {

    @SerializedName("WebdocSdkFeedbackResult")
    @Expose
    private WebdocSdkFeedbackResult webdocSdkFeedbackResult;

    public WebdocSdkFeedbackResult getWebdocSdkFeedbackResult() {
        return webdocSdkFeedbackResult;
    }

    public void setWebdocSdkFeedbackResult(WebdocSdkFeedbackResult webdocSdkFeedbackResult) {
        this.webdocSdkFeedbackResult = webdocSdkFeedbackResult;
    }

}
