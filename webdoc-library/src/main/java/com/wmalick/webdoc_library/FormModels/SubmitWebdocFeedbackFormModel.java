package com.wmalick.webdoc_library.FormModels;

public class SubmitWebdocFeedbackFormModel {
    String CustomerId, feeback, rating;

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getFeeback() {
        return feeback;
    }

    public void setFeeback(String feeback) {
        this.feeback = feeback;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
