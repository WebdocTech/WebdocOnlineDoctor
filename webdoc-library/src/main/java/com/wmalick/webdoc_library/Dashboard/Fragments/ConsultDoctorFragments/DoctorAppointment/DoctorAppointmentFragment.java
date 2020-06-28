package com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorAppointment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wmalick.webdoc_library.Adapters.BookDoctorListAdapter;
import com.wmalick.webdoc_library.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorAppointmentFragment extends Fragment {

    RecyclerView recyclerViewConsult;
    BookDoctorListAdapter doctorListConsultAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_appointment, container, false);

        recyclerViewConsult=(RecyclerView)view.findViewById(R.id.rvAppointmentDoctor_DoctorConsultationFrag);
        LinearLayoutManager layoutManager1= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerViewConsult.setLayoutManager(layoutManager1);
        recyclerViewConsult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerViewConsult.setHasFixedSize(true);
        doctorListConsultAdapter =new BookDoctorListAdapter(getActivity(), getActivity().getSupportFragmentManager());

        recyclerViewConsult.setAdapter(doctorListConsultAdapter);

        return view;
    }

}
