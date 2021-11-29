package com.wmalick.webdoc_library.Dashboard.Fragments.PrescriptionHistoryFragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.JsonObject;
import com.wmalick.webdoc_library.Adapters.PrescriptionHistoryAdapter;
import com.wmalick.webdoc_library.Dashboard.WebdocDashboardActivity;
import com.wmalick.webdoc_library.Essentials.Constants;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.InitiateSDK.modelclasses.CustomerConsultationResult.CustomerConsultationResult;
import com.wmalick.webdoc_library.api.APIClient;
import com.wmalick.webdoc_library.api.APIInterface;
import com.wmalick.webdoc_library.databinding.FragmentPrescriptionHistoryBinding;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionHistoryFragment extends Fragment {
    private FragmentPrescriptionHistoryBinding layoutBinding;
    PrescriptionHistoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_prescription_history, container, false);
        layoutBinding = FragmentPrescriptionHistoryBinding.inflate(inflater, container, false);

        WebdocDashboardActivity.toolbar.setTitle(getString(R.string.prescription_history));
        layoutBinding.tvNoConsultationPrescriptionHistoryFragment.setTextColor(Color.parseColor(Global.THEME_COLOR_CODE));

        if (Global.customerConsultationList.size() > 0) {
            layoutBinding.NoConsultLayout.setVisibility(View.GONE);
            layoutBinding.ConsultLayout.setVisibility(View.VISIBLE);
        } else {
            layoutBinding.ConsultLayout.setVisibility(View.GONE);
            layoutBinding.NoConsultLayout.setVisibility(View.VISIBLE);
        }

        if (Global.customerConsultationResponse == null){
            getPrescriptionHistory();
        } else {
            setAdapter();
        }

        return layoutBinding.getRoot();
    }

    public void setAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutBinding.RecyclerViewPrescription.setLayoutManager(layoutManager);
        layoutBinding.RecyclerViewPrescription.setHasFixedSize(true);
        adapter = new PrescriptionHistoryAdapter(getActivity());
        layoutBinding.RecyclerViewPrescription.setAdapter(adapter);
    }

    private void getPrescriptionHistory() {
       callPrescriptionHistoryApi(getActivity(), Global.getCustomerDataModel.getGetcustomerDataResult().getCustomerData().getApplicationUserId());
    }

    public void callPrescriptionHistoryApi(Activity activity, String cID) {
        if (!Global.utils.isInternerConnected(activity)) {
            Global.utils.showToast(activity, "No internet connection !");
        } else {

            Global.utils.showProgressDialog(activity, this.getString(R.string.loading_prescription));

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("CustomerId", cID);

            Log.e("webdoc_doctors_sdk", jsonObject.toString());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<CustomerConsultationResult> call = apiInterface.callCustomerConsultationApi(jsonObject);

            call.enqueue(new Callback<CustomerConsultationResult>() {
                @Override
                public void onResponse(Call<CustomerConsultationResult> call, Response<CustomerConsultationResult> response) {
                    Global.utils.hideProgressDialog();
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

                                setAdapter();

                            } else {
                                Toast.makeText(getActivity(), customerConsultationResult.getCustomerConsultationResult().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CustomerConsultationResult> call, Throwable t) {
                    Global.utils.hideProgressDialog();
                    //Log.e(TAG, t.toString());
                    Toast.makeText(activity, "Ooops! something went wrong !", Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}
