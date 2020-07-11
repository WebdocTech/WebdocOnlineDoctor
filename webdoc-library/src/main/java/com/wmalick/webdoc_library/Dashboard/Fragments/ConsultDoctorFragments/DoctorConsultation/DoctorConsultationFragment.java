package com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsultation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wmalick.webdoc_library.Adapters.ConsultDoctorListAdapter;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorConsultationFragment extends Fragment {

    RecyclerView recyclerViewConsult;
    ConsultDoctorListAdapter doctorListConsultAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_consultation, container, false);

        Global.doctorsList.clear();
        for(int i = 0; i < Global.doctorListResponse.getDoctorListResult().getDoctorprofiles().size(); i++)
        {
            Global.doctorsList.add(Global.doctorListResponse.getDoctorListResult().getDoctorprofiles().get(i));
        }

        recyclerViewConsult=(RecyclerView)view.findViewById(R.id.rvConsultDoctor_DoctorConsultationFrag);
        LinearLayoutManager layoutManager1= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerViewConsult.setLayoutManager(layoutManager1);
        recyclerViewConsult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerViewConsult.setHasFixedSize(true);
        doctorListConsultAdapter =new ConsultDoctorListAdapter(getActivity(), getActivity().getSupportFragmentManager());
        recyclerViewConsult.setAdapter(doctorListConsultAdapter);

        return view;
    }
}
