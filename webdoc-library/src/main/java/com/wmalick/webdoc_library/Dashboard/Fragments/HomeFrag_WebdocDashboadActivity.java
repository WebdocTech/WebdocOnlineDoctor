package com.wmalick.webdoc_library.Dashboard.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsultation.DoctorConsultationFragment;
import com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.RateDoctorPopupActivity;
import com.wmalick.webdoc_library.Dashboard.Fragments.PrescriptionHistoryFragment.PrescriptionHistoryFragment;
import com.wmalick.webdoc_library.Dashboard.WebdocDashboardActivity;
import com.wmalick.webdoc_library.Essentials.Constants;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.FormModels.SubmitWebdocFeedbackFormModel;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.ResponseModels.CustomerConsultationResult.CustomerConsultationResult;
import com.wmalick.webdoc_library.ResponseModels.DoctorListResult.DoctorListResult;
import com.wmalick.webdoc_library.ResponseModels.WebdocFeedback.WebdocFeedbackApiResponse;
import com.wmalick.webdoc_library.ServerManager.ServerManager;
import com.wmalick.webdoc_library.ServerManager.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;
public class HomeFrag_WebdocDashboadActivity extends Fragment implements VolleyListener {

    TextView tvConsultDoctor, tvPrescriptionHistory, tvFeedBack;
    ServerManager serverManager;
    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_frag__webdoc_dashboad, container, false);

        WebdocDashboardActivity.toolbar.setTitle(getString(R.string.health));

        InitControl(view);
        ActionControl(view);
        return view;
    }

    private void ActionControl(View view) {

        tvConsultDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDoctorsList();
            }
        });

        tvPrescriptionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPrescriptionHistory();
            }
        });

        tvFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FeedBackDialog();
                /*getActivity().startActivity(new Intent(getActivity(), RateDoctorPopupActivity.class));*/

            }
        });
    }

    private void InitControl(View view) {
        serverManager = new ServerManager(getActivity(), this);
        tvConsultDoctor = view.findViewById(R.id.tvConsultDoctor_HomeFrag_WebdocDashboardActivity);
        tvPrescriptionHistory = view.findViewById(R.id.tvPrescriptionHistory_HomeFrag_WebdocDashboardActivity);
        tvFeedBack = view.findViewById(R.id.tvFeedback_HomeFrag_WebdocDashboardActivity);
        tvConsultDoctor.getCompoundDrawables()[0].setColorFilter(Color.parseColor(Global.THEME_COLOR_CODE), PorterDuff.Mode.SRC_ATOP);
        tvPrescriptionHistory.getCompoundDrawables()[0].setColorFilter(Color.parseColor(Global.THEME_COLOR_CODE), PorterDuff.Mode.SRC_ATOP);
        tvFeedBack.getCompoundDrawables()[0].setColorFilter(Color.parseColor(Global.THEME_COLOR_CODE), PorterDuff.Mode.SRC_ATOP);

    }

    private void getPrescriptionHistory() {
        Global.utils.showProgressDialog(getActivity(), getActivity().getString(R.string.loading_prescription));
        serverManager.GetPrescriptionHistory(Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getApplicationUserId());
    }

    private void getDoctorsList() {
        Global.utils.showProgressDialog(getActivity(), getActivity().getString(R.string.loading_doctors));
        serverManager.GetDoctorsList();
    }

    private void FeedBackDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View v = getLayoutInflater().inflate(R.layout.alert_dashboard_feedback, null);
        dialogBuilder.setView(v);

        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final TextView rating = v.findViewById(R.id.tv_rating);
        rating.setText(getString(R.string.rating_5));

        final EditText etFeedback = v.findViewById(R.id.edt_feedback);
        final RatingBar ratingBar = v.findViewById(R.id.ratingBar_WebdocRating);
        rating.setTextColor(Color.parseColor(Global.THEME_COLOR_CODE));
        //ratingBar.getBackground().setTint(Color.parseColor(Global.THEME_COLOR_CODE));
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor(Global.THEME_COLOR_CODE), PorterDuff.Mode.SRC_ATOP);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v <= 1){
                    rating.setText(getString(R.string.rating_1));
                }else if(v <= 2){
                    rating.setText(getString(R.string.rating_2));
                }else if(v <= 3){
                    rating.setText(getString(R.string.rating_3));
                }else if(v <= 4){
                    rating.setText(getString(R.string.rating_4));
                }else if(v <= 5){
                    rating.setText(getString(R.string.rating_5));
                }
            }
        });

        Button submit = v.findViewById(R.id.btn_Submit);
        submit.getBackground().setTint(Color.parseColor(Global.THEME_COLOR_CODE));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userFeedBack = "";
                if(!TextUtils.isEmpty(etFeedback.getText().toString())){
                    userFeedBack = etFeedback.getText().toString();
                }
                SubmitWebdocFeedbackFormModel dataModel = new SubmitWebdocFeedbackFormModel();
                dataModel.setCustomerId(Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getApplicationUserId());
                dataModel.setFeeback(userFeedBack);
                dataModel.setRating(String.valueOf(ratingBar.getRating()));

                Global.utils.showProgressDialog(getActivity(), getActivity().getString(R.string.submitting_feedback));
                serverManager.SubmitWebdocFeedBack(dataModel);
            }
        });
        Button later = v.findViewById(R.id.btn_Later);
        later.setTextColor(Color.parseColor(Global.THEME_COLOR_CODE));
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }//alert

    @Override
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException {
        Gson gson = new Gson();
        if (requestType.equalsIgnoreCase(Constants.DOCTORS_LIST_API))
        {
            DoctorListResult doctorListResponse = gson.fromJson(response.toString(), DoctorListResult.class);

            if(doctorListResponse.getDoctorListResult().getResponseCode().equalsIgnoreCase(Constants.SUCCESSCODE))
            {
                Global.doctorListResponse = doctorListResponse;

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer_WebdocDashboardActivity, new DoctorConsultationFragment())
                        .addToBackStack("")
                        .commit();

                Global.utils.hideProgressDialog();

            }
            else
            {
                Toast.makeText(getActivity(), doctorListResponse.getDoctorListResult().getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else if(requestType.equalsIgnoreCase(Constants.CUSTOMER_CONSULTATION_API))
        {
            Global.utils.hideProgressDialog();
            CustomerConsultationResult customerConsultationResult = gson.fromJson(response.toString(), CustomerConsultationResult.class);

            if(customerConsultationResult.getCustomerConsultationResult().getResponseCode().equalsIgnoreCase(Constants.SUCCESSCODE))
            {

                Global.customerConsultationResponse = customerConsultationResult;

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer_WebdocDashboardActivity, new PrescriptionHistoryFragment())
                        .addToBackStack("")
                        .commit();

                Global.utils.hideProgressDialog();

            }
            else
            {
                Toast.makeText(getActivity(), customerConsultationResult.getCustomerConsultationResult().getMessage(), Toast.LENGTH_LONG).show();
            }
        }else if(requestType.equalsIgnoreCase(Constants.WEBDOC_FEEDBACK)){
            Global.utils.hideProgressDialog();
            WebdocFeedbackApiResponse responseModel = gson.fromJson(response.toString(), WebdocFeedbackApiResponse.class);
            if(responseModel.getWebdocSdkFeedbackResult().getResponseCode().equals(Constants.SUCCESSCODE)){
                alertDialog.dismiss();
                Global.utils.showSuccessSnakeBar(getActivity(), responseModel.getWebdocSdkFeedbackResult().getMessage());
            }else{
                Global.utils.showErrorSnakeBar(getActivity(), responseModel.getWebdocSdkFeedbackResult().getMessage());
            }

        }
    }
}
