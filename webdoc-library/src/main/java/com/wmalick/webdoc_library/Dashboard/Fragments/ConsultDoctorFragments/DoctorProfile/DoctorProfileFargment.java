package com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorProfile;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsult.DoctorConsultActivity;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;

public class DoctorProfileFargment extends Fragment {

    TextView tv_DocName, textView_Speciality, textView_Education, text_Degree, text_College, textView_Years, text_ExpDetail;
    Button btnConsult;
    ImageView ivImgDoc;
    ImageView ivOnlineStatus;
    static Activity activity;

    public DoctorProfileFargment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_profile_fargment, container, false);

        InitControl(view);
        ActionControl(view);
        doctorStatusRealTime();

        return view;
    }

    private void ActionControl(View view) {
        activity = getActivity();
        tv_DocName.setText(Global.selectedDoctor.getFirstName() + " " + Global.selectedDoctor.getLastName());
        textView_Speciality.setText(Global.selectedDoctor.getDoctorSpecialty());
        textView_Education.setText(Global.selectedDoctor.getEducation());
        text_Degree.setText(Global.selectedDoctor.getEducation());
        text_College.setText(Global.selectedDoctor.getEducationInstitute());
        textView_Years.setText(Global.selectedDoctor.getExperience());
        text_ExpDetail.setText(Global.selectedDoctor.getDetailedInformation());
        Picasso.get().load(Global.selectedDoctor.getImgLink()).placeholder(R.drawable.ic_placeholder_doctor).error(R.drawable.ic_placeholder_doctor).into(ivImgDoc);

        btnConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(),  DoctorConsultActivity.class));
            }
        });

    }

    private void InitControl(View view) {
        tv_DocName = view.findViewById(R.id.tv_DocName);
        textView_Speciality = view.findViewById(R.id.textView_Speciality);
        textView_Education = view.findViewById(R.id.textView_Education);
        text_Degree = view.findViewById(R.id.text_Degree);
        text_College = view.findViewById(R.id.text_College);
        textView_Years = view.findViewById(R.id.textView_Years);
        text_ExpDetail = view.findViewById(R.id.text_ExpDetail);
        btnConsult = view.findViewById(R.id.btn_Consult);
        btnConsult.getBackground().setTint(Color.parseColor(Global.THEME_COLOR_CODE));
        ivImgDoc = view.findViewById(R.id.user_image);
        ivOnlineStatus = view.findViewById(R.id.iv_onlineStatus_doctorProfile);

    }
    private void doctorStatusRealTime() {
        FirebaseApp appReference = firebaseAppReference(activity);
        FirebaseDatabase databaseReference = FirebaseDatabase.getInstance(appReference);
        databaseReference.getReference().child("Doctors").child(Global.selectedDoctor.getEmail().replace(".", "")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    if (dataSnapshot.child("status").getValue() != null) {
                        Global.selectedDoctor.setOnlineDoctor(dataSnapshot.child("status").getValue().toString());
                        if (dataSnapshot.child("status").getValue().toString().equals("online")) {
                            ivOnlineStatus.setImageResource(R.drawable.online);
                        } else {
                            ivOnlineStatus.setImageResource(R.drawable.ic_offline);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private static FirebaseApp firebaseAppReference(Context context)
    {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyAY2_N3ab_45ggVlisDcuOWxhbzVZZqN34")
                .setApplicationId("1:642677587502:android:d0de10b1c22b1ee21a69bf")
                .setDatabaseUrl("https://webdoc-896a8.firebaseio.com/")
                .build();

        try {
            FirebaseApp app = FirebaseApp.initializeApp(context, options, "WebDocDoctorSDK");
            return app;
        }
        catch (IllegalStateException e)
        {
            return FirebaseApp.getInstance("WebDocDoctorSDK");
        }
    }

}
