package com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wmalick.webdoc_library.Essentials.Constants;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.FormModels.SubmitWebdocFeedbackFormModel;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.ResponseModels.WebdocFeedback.WebdocFeedbackApiResponse;
import com.wmalick.webdoc_library.ServerManager.ServerManager;
import com.wmalick.webdoc_library.ServerManager.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RateDoctorPopupActivity extends AppCompatActivity implements VolleyListener {

    TextView tvTitle, tvSubtitle, tvRatingTag;
    EditText etReview;
    Button btnLater, btnSubmit;
    RatingBar ratingBar;
    ServerManager serverManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_doctor_popup);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.80);
        getWindow().setLayout(screenWidth, WindowManager.LayoutParams.WRAP_CONTENT);
        InitViews();
        ActionControls();
    }

    private void ActionControls() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userFeedBack = "";
                if(!TextUtils.isEmpty(etReview.getText().toString())){
                    userFeedBack = etReview.getText().toString();
                }
                SubmitWebdocFeedbackFormModel dataModel = new SubmitWebdocFeedbackFormModel();
                dataModel.setCustomerId(Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getApplicationUserId());
                dataModel.setFeeback(userFeedBack);
                dataModel.setRating(String.valueOf(ratingBar.getRating()));
                Global.utils.showProgressDialog(RateDoctorPopupActivity.this, getString(R.string.submitting_feedback));
                serverManager.SubmitWebdocFeedBack(dataModel);
            }
        });

        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v <= 1){
                    tvRatingTag.setText(getString(R.string.rating_1));
                }else if(v <= 2){
                    tvRatingTag.setText(getString(R.string.rating_2));
                }else if(v <= 3){
                    tvRatingTag.setText(getString(R.string.rating_3));
                }else if(v <= 4){
                    tvRatingTag.setText(getString(R.string.rating_4));
                }else if(v <= 5){
                    tvRatingTag.setText(getString(R.string.rating_5));
                }
            }
        });
    }

    private void InitViews() {
        serverManager = new ServerManager(this, this);
        tvTitle = findViewById(R.id.tvTitle_RateDoctorPopupActivity);
        tvSubtitle = findViewById(R.id.tvSubtitle_RateDoctorPopupActivity);
        tvRatingTag = findViewById(R.id.tvRatingTag_RateDoctorPopupActivity);
        etReview = findViewById(R.id.etReview_RateDoctorPopupActivity);
        btnSubmit = findViewById(R.id.btnSubmit_RateDoctorPopupActivity);
        btnLater = findViewById(R.id.btnLater_RateDoctorPopupActivity);
        ratingBar = findViewById(R.id.ratingBar_RateDoctorPopupActivity);
    }

    @Override
    public void getRequestResponse(JSONObject response, String requestedApi) throws JSONException {
        Gson gson = new Gson();
         if(requestedApi.equalsIgnoreCase(Constants.WEBDOC_FEEDBACK)){
            Global.utils.hideProgressDialog();
            WebdocFeedbackApiResponse responseModel = gson.fromJson(response.toString(), WebdocFeedbackApiResponse.class);
            if(responseModel.getWebdocSdkFeedbackResult().getResponseCode().equals(Constants.SUCCESSCODE)){
                Global.utils.showSuccessSnakeBar(this, responseModel.getWebdocSdkFeedbackResult().getMessage());
                finish();
            }else{
                Global.utils.showErrorSnakeBar(this, responseModel.getWebdocSdkFeedbackResult().getMessage());
            }
        }
    }
}