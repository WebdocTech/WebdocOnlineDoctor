package com.wmalick.webdoc_library.Dashboard;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsultation.DoctorConsultationFragment;
import com.wmalick.webdoc_library.Dashboard.Fragments.PrescriptionHistoryFragment.PrescriptionHistoryFragment;
import com.wmalick.webdoc_library.Dashboard.modelclasses.DoctorListResult;
import com.wmalick.webdoc_library.Dashboard.modelclasses.DoctorsListModel;
import com.wmalick.webdoc_library.Dashboard.modelclasses.FeedbackModel;
import com.wmalick.webdoc_library.Essentials.Constants;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.InitiateSDK.modelclasses.CustomerConsultationResult.CustomerConsultationResult;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.api.APIClient;
import com.wmalick.webdoc_library.api.APIInterface;
import com.wmalick.webdoc_library.databinding.ActivityWebdocDashboardBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebdocDashboardActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityWebdocDashboardBinding layoutBinding;
    private String mSinchUserId;
    private long mSigningSequence = 1;
    FirebaseAuth mAuth;
    AlertDialog alertDialog;
    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor(Global.THEME_COLOR_CODE));
        layoutBinding = ActivityWebdocDashboardBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());


        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        //settting toolbar:
        toolbar = findViewById(R.id.toolbar_WebdocDashboardActivity);
        layoutBinding.toolbarWebdocDashboardActivity.setBackgroundColor(Color.parseColor(Global.THEME_COLOR_CODE));
        setSupportActionBar(layoutBinding.toolbarWebdocDashboardActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        layoutBinding.toolbarWebdocDashboardActivity.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        getSupportActionBar().setTitle(getString(R.string.health));


        layoutBinding.toolbarWebdocDashboardActivity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layoutBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        InitControl();
        getDoctorsList();

        layoutBinding.progressBarWebdocDashboardActivity.setVisibility(View.GONE);
    }

    private void InitControl() {
        //serverManager = new ServerManager(this, this);
        layoutBinding.bottomNavigation.setBackgroundColor((Color.parseColor(Global.THEME_COLOR_CODE)));
    }

    private void getDoctorsList() {
        /*Global.utils.showProgressDialog(this, this.getString(R.string.loading_doctors));
        serverManager.GetDoctorsList();*/
        callDoctorsListApi();
    }

    private void FeedBackDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
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
                if (v <= 1) {
                    rating.setText(getString(R.string.rating_1));
                } else if (v <= 2) {
                    rating.setText(getString(R.string.rating_2));
                } else if (v <= 3) {
                    rating.setText(getString(R.string.rating_3));
                } else if (v <= 4) {
                    rating.setText(getString(R.string.rating_4));
                } else if (v <= 5) {
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
                if (!TextUtils.isEmpty(etFeedback.getText().toString())) {
                    userFeedBack = etFeedback.getText().toString();
                }

                /*SubmitWebdocFeedbackFormModel dataModel = new SubmitWebdocFeedbackFormModel();
                dataModel.setCustomerId(Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getApplicationUserId());
                dataModel.setFeeback(userFeedBack);
                dataModel.setRating(String.valueOf(ratingBar.getRating()));

                Global.utils.showProgressDialog(WebdocDashboardActivity.this, getString(R.string.submitting_feedback));
                serverManager.SubmitWebdocFeedBack(dataModel);*/

                callFeedbackApi(String.valueOf(ratingBar.getRating()), userFeedBack,
                        Global.getCustomerDataModel.getGetcustomerDataResult().getCustomerData().getApplicationUserId());
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_consult) {
            fragment = new DoctorConsultationFragment();
        } else if (itemId == R.id.menu_history) {
            fragment = new PrescriptionHistoryFragment();
        }

        return loadFragment(fragment);
    }

    private void setDefaultFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new DoctorConsultationFragment())
                .commit();
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        Drawable drawable = menu.findItem(R.id.menu_feedback).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_feedback) {
            FeedBackDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void callDoctorsListApi() {
        if (!Global.utils.isInternerConnected(this)) {
            Global.utils.showToast(this, "No internet connection !");
        } else {

            Global.utils.showProgressDialog(this, this.getString(R.string.loading_doctors));

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Key", Constants.doctorsKey);
            jsonObject.addProperty("City", Constants.patientCity);

            Log.e("webdoc_doctors_sdk", jsonObject.toString());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<DoctorsListModel> call = apiInterface.callDoctorsListApi(jsonObject);

            call.enqueue(new Callback<DoctorsListModel>() {
                @Override
                public void onResponse(Call<DoctorsListModel> call, Response<DoctorsListModel> response) {
                    Global.utils.hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {
                            Gson gson = new Gson();
                            DoctorListResult doctorListResponse = response.body().getDoctorListResult();
                            if (doctorListResponse.getResponseCode().equalsIgnoreCase(Constants.SUCCESSCODE)) {
                                Global.doctorListResponse = doctorListResponse;
                                setDefaultFragment();
                            } else {
                                Toast.makeText(WebdocDashboardActivity.this, response.body().getDoctorListResult().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            getPrescriptionHistory();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DoctorsListModel> call, Throwable t) {
                    Global.utils.hideProgressDialog();
                    //Log.e(TAG, t.toString());
                    Toast.makeText(WebdocDashboardActivity.this, "Ooops! something went wrong !", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    public void callFeedbackApi(String rating, String feedback, String cID) {
        if (!Global.utils.isInternerConnected(this)) {
            Global.utils.showToast(this, "No internet connection !");
        } else {

            Global.utils.showProgressDialog(WebdocDashboardActivity.this, getString(R.string.submitting_feedback));
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
                            if (response.body().getWebdocSdkFeedbackResult().getResponseCode().equalsIgnoreCase(Constants.SUCCESSCODE)) {
                                Global.utils.showSuccessSnakeBar(WebdocDashboardActivity.this, response.body().getWebdocSdkFeedbackResult().getMessage());
                            } else {
                                Global.utils.showSuccessSnakeBar(WebdocDashboardActivity.this, response.body().getWebdocSdkFeedbackResult().getMessage());
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
                    Toast.makeText(WebdocDashboardActivity.this, "Ooops! something went wrong !", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private void getPrescriptionHistory() {
        /*if (!Global.getCustomerDataModel.getGetcustomerDataResult().
                getCustomerData().getApplicationUserId().equals("")) {*/

        String str = Global.getCustomerDataModel.getGetcustomerDataResult().getCustomerData().getApplicationUserId();

            callPrescriptionHistoryApi(this, Global.getCustomerDataModel.
                    getGetcustomerDataResult().getCustomerData().getApplicationUserId());
        //}
    }

    public void callPrescriptionHistoryApi(Activity activity, String cID) {
        if (!Global.utils.isInternerConnected(activity)) {
            Global.utils.showToast(activity, "No internet connection !");
        } else {

            //Global.utils.showProgressDialog(activity, this.getString(R.string.loading_prescription));

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("CustomerId", cID);

            Log.e("webdoc_doctors_sdk", jsonObject.toString());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<CustomerConsultationResult> call = apiInterface.callCustomerConsultationApi(jsonObject);

            call.enqueue(new Callback<CustomerConsultationResult>() {
                @Override
                public void onResponse(Call<CustomerConsultationResult> call, Response<CustomerConsultationResult> response) {
                    //Global.utils.hideProgressDialog();

                    if (response.isSuccessful()) {
                        try {
                            //Global.utils.hideProgressDialog();
                            CustomerConsultationResult customerConsultationResult = response.body();

                            if (customerConsultationResult.getCustomerConsultationResult().getResponseCode().equalsIgnoreCase(Constants.SUCCESSCODE)) {

                                Global.customerConsultationResponse = customerConsultationResult;

                                Global.customerConsultationList.clear();
                                for (int i = 0; i < Global.customerConsultationResponse.getCustomerConsultationResult().getConusltationList().size(); i++) {
                                    Global.customerConsultationList.add(Global.customerConsultationResponse.getCustomerConsultationResult().getConusltationList().get(i));
                                }

                            } else {
                                Toast.makeText(WebdocDashboardActivity.this, customerConsultationResult.getCustomerConsultationResult().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CustomerConsultationResult> call, Throwable t) {
                    //Global.utils.hideProgressDialog();
                    //Log.e(TAG, t.toString());
                    Toast.makeText(activity, "Ooops! something went wrong !", Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}
