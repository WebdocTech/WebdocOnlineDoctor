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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrescriptionHistoryDetailsFragment extends Fragment {

    RecyclerView recyclerView;
    Dashboard_account_consults_Adapter adapter;
    CircleImageView iv_drImage;
    TextView tv_Time, tv_Complaints, tv_Diagnosis, tv_ConsultationType, tv_Test, tvDocName;

    public PrescriptionHistoryDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_prescription_history_details, container, false);

        InitControl(view);
        ActionControl(view);

        return view;
    }

    private void ActionControl(View view)
    {
        tv_Time.setText(Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getConsultationDate());
        tv_Complaints.setText(Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getCompliant());
        if(Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getDiagnosis() != null)
        {
            tv_Diagnosis.setText(Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getDiagnosis().toString());
        }
        tv_ConsultationType.setText(Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getConsultationType());
        tv_Test.setText(Global.customerConsultationList.get(Global.selectedCustomerConsultationPosition).getTests());

        Global.customerConsultationDetailsList.clear();
        for (int i = 0; i < Global.customerConsultationResponse.getCustomerConsultationResult().getConusltationList().get(Global.selectedCustomerConsultationPosition).getConsultationdetails().size(); i++) {
            Global.customerConsultationDetailsList.add(Global.customerConsultationResponse.getCustomerConsultationResult().getConusltationList().get(Global.selectedCustomerConsultationPosition).getConsultationdetails().get(i));
        }

        recyclerView = view.findViewById(R.id.RecyclerViewConsults);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        //recyclerView.setNestedScrollingEnabled(false);
        adapter = new Dashboard_account_consults_Adapter(getActivity()); //initialize main adapter
        recyclerView.setAdapter(adapter);
    }

    private void InitControl(View view)
    {
        iv_drImage = view.findViewById(R.id.iv_drImage);
        tv_Time = view.findViewById(R.id.tv_Time);
        tv_Complaints = view.findViewById(R.id.tv_Complaints);
        tv_Diagnosis = view.findViewById(R.id.tv_Diagnosis);
        tv_ConsultationType = view.findViewById(R.id.tv_ConsultationType);
        tv_Test = view.findViewById(R.id.tv_Test);
        tvDocName = view.findViewById(R.id.tvDocName_PrescriptionHistoryDetailsFragment);
        tvDocName.setBackgroundColor(Color.parseColor(Global.THEME_COLOR_CODE));
    }
}
