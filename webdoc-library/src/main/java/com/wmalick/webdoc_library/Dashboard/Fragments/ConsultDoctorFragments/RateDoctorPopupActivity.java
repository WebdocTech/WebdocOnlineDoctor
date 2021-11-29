package com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.wmalick.webdoc_library.Dashboard.modelclasses.FeedbackModel;
import com.wmalick.webdoc_library.Essentials.Constants;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.api.APIClient;
import com.wmalick.webdoc_library.api.APIInterface;
import com.wmalick.webdoc_library.databinding.ActivityRateDoctorPopupBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateDoctorPopupActivity extends AppCompatActivity {
    private ActivityRateDoctorPopupBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityRateDoctorPopupBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.80);
        getWindow().setLayout(screenWidth, WindowManager.LayoutParams.WRAP_CONTENT);

        ActionControls();
    }

    private void ActionControls() {
        layoutBinding.btnSubmitRateDoctorPopupActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userFeedBack = "";
                if (!TextUtils.isEmpty(layoutBinding.etReviewRateDoctorPopupActivity.getText().toString())) {
                    userFeedBack = layoutBinding.etReviewRateDoctorPopupActivity.getText().toString();
                }

                callFeedbackApi(Global.getCustomerDataModel.getGetcustomerDataResult().getCustomerData().getApplicationUserId(),
                        userFeedBack, String.valueOf(layoutBinding.ratingBarRateDoctorPopupActivity.getRating()));
            }
        });

        layoutBinding.btnLaterRateDoctorPopupActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layoutBinding.ratingBarRateDoctorPopupActivity.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (v <= 1) {
                    layoutBinding.tvRatingTagRateDoctorPopupActivity.setText(getString(R.string.rating_1));
                } else if (v <= 2) {
                    layoutBinding.tvRatingTagRateDoctorPopupActivity.setText(getString(R.string.rating_2));
                } else if (v <= 3) {
                    layoutBinding.tvRatingTagRateDoctorPopupActivity.setText(getString(R.string.rating_3));
                } else if (v <= 4) {
                    layoutBinding.tvRatingTagRateDoctorPopupActivity.setText(getString(R.string.rating_4));
                } else if (v <= 5) {
                    layoutBinding.tvRatingTagRateDoctorPopupActivity.setText(getString(R.string.rating_5));
                }
            }
        });
    }


    public void callFeedbackApi(String cID, String feedback, String rating) {
        if (!Global.utils.isInternerConnected(this)) {
            Global.utils.showToast(this, "No internet connection !");
        } else {

            Global.utils.showProgressDialog(RateDoctorPopupActivity.this, getString(R.string.submitting_feedback));
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("CustomerId", cID);
            jsonObject.addProperty("feeback", feedback);
            jsonObject.addProperty("rating", rating);

            Log.e("webdoc_doctors_sdk", jsonObject.toString());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<FeedbackModel> call = apiInterface.callFeedbackApi(jsonObject);

            call.enqueue(new Callback<FeedbackModel>() {
                @Override
                public void onResponse(Call<FeedbackModel> call, Response<FeedbackModel> response) {
                    Global.utils.hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {
                            FeedbackModel responseModel = response.body();
                            if (responseModel.getWebdocSdkFeedbackResult().getResponseCode().equals(Constants.SUCCESSCODE)) {
                                Global.utils.showSuccessSnakeBar(RateDoctorPopupActivity.this, responseModel.getWebdocSdkFeedbackResult().getMessage());
                                finish();
                            } else {
                                Global.utils.showErrorSnakeBar(RateDoctorPopupActivity.this, responseModel.getWebdocSdkFeedbackResult().getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<FeedbackModel> call, Throwable t) {
                    Global.utils.hideProgressDialog();
                    //Log.e(TAG, t.toString());
                    Toast.makeText(RateDoctorPopupActivity.this, "Ooops! something went wrong !", Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}