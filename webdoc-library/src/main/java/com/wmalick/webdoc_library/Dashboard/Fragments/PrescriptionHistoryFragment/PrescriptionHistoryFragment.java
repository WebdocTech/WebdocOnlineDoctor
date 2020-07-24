package com.wmalick.webdoc_library.Dashboard.Fragments.PrescriptionHistoryFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wmalick.webdoc_library.Adapters.PrescriptionHistoryAdapter;
import com.wmalick.webdoc_library.Dashboard.WebdocDashboardActivity;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;

public class PrescriptionHistoryFragment extends Fragment {
     RecyclerView recyclerView;
     PrescriptionHistoryAdapter adapter;
     ConstraintLayout Consult_Layout, NoConsult_Layout;
     TextView labelNoConsultation;

    public PrescriptionHistoryFragment() {
        // Required empty public constructor
    }


    // Dashboard--Fragments-- idar aik package bnaen 'PrescriptionHistoryFragment' kay naam se, aur ye java file add kr dain

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     View view= inflater.inflate(R.layout.fragment_prescription_history, container, false);

        WebdocDashboardActivity.toolbar.setTitle(getString(R.string.prescription_history));

        Consult_Layout = view.findViewById(R.id.Consult_Layout);
        NoConsult_Layout = view.findViewById(R.id.NoConsult_Layout);
        labelNoConsultation = view.findViewById(R.id.tvNoConsultation_PrescriptionHistoryFragment);
        labelNoConsultation.setTextColor(Color.parseColor(Global.THEME_COLOR_CODE));

        Global.customerConsultationList.clear();
        for(int i = 0 ; i < Global.customerConsultationResponse.getCustomerConsultationResult().getConusltationList().size(); i++)
        {
            Global.customerConsultationList.add(Global.customerConsultationResponse.getCustomerConsultationResult().getConusltationList().get(i));
        }

        if(Global.customerConsultationList.size() > 0)
        {
            NoConsult_Layout.setVisibility(View.GONE);
            Consult_Layout.setVisibility(View.VISIBLE);
        }
        else
        {
            Consult_Layout.setVisibility(View.GONE);
            NoConsult_Layout.setVisibility(View.VISIBLE);
        }

        recyclerView=view.findViewById(R.id.RecyclerView_Prescription);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter =new PrescriptionHistoryAdapter(getActivity());
        recyclerView.setAdapter(adapter);

     return view;
    }

}
