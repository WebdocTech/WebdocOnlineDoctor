package com.wmalick.webdoc_library.Dashboard.modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackModel {

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