package com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsultation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wmalick.webdoc_library.Adapters.ConsultDoctorListAdapter;
import com.wmalick.webdoc_library.Dashboard.WebdocDashboardActivity;
import com.wmalick.webdoc_library.DataModels.doctorStatusModelClass;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;


public class DoctorConsultationFragment extends Fragment {

    RecyclerView recyclerViewConsult;
    public static ConsultDoctorListAdapter doctorListConsultAdapter;
    static DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_consultation, container, false);

        WebdocDashboardActivity.toolbar.setTitle(getString(R.string.consult_doctor));

        /*Global.doctorsList.clear();
        for(int i = 0; i < Global.doctorListResponse.getDoctorListResult().getDoctorprofiles().size(); i++)
        {
            Global.doctorsList.add(Global.doctorListResponse.getDoctorListResult().getDoctorprofiles().get(i));
        }*/

        recyclerViewConsult=(RecyclerView)view.findViewById(R.id.rvConsultDoctor_DoctorConsultationFrag);
        LinearLayoutManager layoutManager1= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerViewConsult.setLayoutManager(layoutManager1);
        recyclerViewConsult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerViewConsult.setHasFixedSize(true);
        doctorListConsultAdapter =new ConsultDoctorListAdapter(getActivity(), getActivity().getSupportFragmentManager());
        recyclerViewConsult.setAdapter(doctorListConsultAdapter);
        UpdateRealTimeStatuses();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        doctorListConsultAdapter.notifyDataSetChanged();
        UpdateRealTimeStatuses();
    }

    public static void UpdateRealTimeStatuses() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    int i = 0;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String email = dataSnapshot1.getKey();
                        doctorStatusModelClass info = new doctorStatusModelClass();
                        info.setDoctorEmail(email);
                        info.setStatus(dataSnapshot1.child("status").getValue().toString());
                        for(int w=0; w<Global.doctorListResponse.getDoctorListResult().getDoctorprofiles().size(); w++){
                            if(Global.doctorListResponse.getDoctorListResult().getDoctorprofiles().get(w).getEmail().replace(".", "").equals(info.getDoctorEmail())){
                                Global.doctorListResponse.getDoctorListResult().getDoctorprofiles().get(w).setOnlineDoctor(dataSnapshot1.child("status").getValue().toString());
                            }
                        }
                        i ++;
                    }
                    doctorListConsultAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
