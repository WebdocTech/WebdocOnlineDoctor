package com.wmalick.webdoc_library.Dashboard.Fragments.PrescriptionHistoryFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.wmalick.webdoc_library.Adapters.Dashboard_account_consults_Adapter;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.databinding.FragmentPrescriptionHistoryBinding;
import com.wmalick.webdoc_library.databinding.FragmentPrescriptionHistoryDetailsBinding;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrescriptionHistoryDetailsFragment extends Fragment {
    private FragmentPrescriptionHistoryDetailsBinding layoutBinding;
    Dashboard_account_consults_Adapter adapter;

    public PrescriptionHistoryDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutBinding = FragmentPrescriptionHistoryDetailsBinding.inflate(inflater, container, false);


        InitControl();
        ActionControl();

        return layoutBinding.getRoot();
    }

    private void ActionControl() {
        layoutBinding.tvTime.setText(Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getConsultationDate());
        layoutBinding.tvComplaints.setText(Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getCompliant());
        if (Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getDiagnosis() != null) {
            layoutBinding.tvDiagnosis.setText(Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getDiagnosis().toString());
        }
        layoutBinding.tvConsultationType.setText(Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getConsultationType());
        layoutBinding.tvTest.setText(Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getTests());

        Global.customerConsultationDetailsList.clear();
        for (int i = 0; i < Global.customerConsultationResponse.getCustomerConsultationResult().getConusltationList().get(Global.selectedCustomerConsultationPosition).getConsultationdetails().size(); i++) {
            Global.customerConsultationDetailsList.add(Global.customerConsultationResponse.getCustomerConsultationResult().getConusltationList().get(Global.selectedCustomerConsultationPosition).getConsultationdetails().get(i));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutBinding.RecyclerViewConsults.setLayoutManager(layoutManager);
        layoutBinding.RecyclerViewConsults.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        layoutBinding.RecyclerViewConsults.setHasFixedSize(true);
        adapter = new Dashboard_account_consults_Adapter(getActivity()); //initialize main adapter
        layoutBinding.RecyclerViewConsults.setAdapter(adapter);
    }

    private void InitControl() {
        layoutBinding.tvDocNamePrescriptionHistoryDetailsFragment.setBackgroundColor(Color.parseColor(Global.THEME_COLOR_CODE));
        layoutBinding.tvDocNamePrescriptionHistoryDetailsFragment.setText(Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getDoctorName());
    }
}
