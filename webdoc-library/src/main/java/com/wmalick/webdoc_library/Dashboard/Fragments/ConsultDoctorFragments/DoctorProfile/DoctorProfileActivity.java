package com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsult.DoctorConsultActivity;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.databinding.ActivityDoctorProfileBinding;

public class DoctorProfileActivity extends AppCompatActivity {
    private ActivityDoctorProfileBinding layoutBinding;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityDoctorProfileBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        getWindow().setStatusBarColor(Color.parseColor(Global.THEME_COLOR_CODE));

        layoutBinding.toolBar.setBackgroundColor(Color.parseColor(Global.THEME_COLOR_CODE));
        layoutBinding.btnConsult.setBackgroundColor(Color.parseColor(Global.THEME_COLOR_CODE));
        setSupportActionBar(layoutBinding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        layoutBinding.toolBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        getSupportActionBar().setTitle(getString(R.string.doctor_profile));
        layoutBinding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ActionControl();
        doctorStatusRealTime();
    }

    private void ActionControl() {
        activity = this;
        layoutBinding.tvDocName.setText(Global.selectedDoctor.getFirstName() + " " + Global.selectedDoctor.getLastName());
        layoutBinding.textViewSpeciality.setText(Global.selectedDoctor.getDoctorSpecialty());
        layoutBinding.textViewEducation.setText(Global.selectedDoctor.getEducation());
        layoutBinding.textDegree.setText(Global.selectedDoctor.getEducation());
        layoutBinding.textCollege.setText(Global.selectedDoctor.getEducationInstitute());
        layoutBinding.textViewYears.setText(Global.selectedDoctor.getExperience());
        layoutBinding.textExpDetail.setText(Global.selectedDoctor.getDetailedInformation());
        Picasso.get().load(Global.selectedDoctor.getImgLink()).placeholder(R.drawable.ic_placeholder_doctor).error(R.drawable.ic_placeholder_doctor).into(layoutBinding.userImage);

        layoutBinding.btnConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorProfileActivity.this, DoctorConsultActivity.class));
            }
        });

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
                            layoutBinding.ivOnlineStatus.setImageResource(R.drawable.online);
                        } else {
                            layoutBinding.ivOnlineStatus.setImageResource(R.drawable.ic_offline);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private static FirebaseApp firebaseAppReference(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyAY2_N3ab_45ggVlisDcuOWxhbzVZZqN34")
                .setApplicationId("1:642677587502:android:d0de10b1c22b1ee21a69bf")
                .setDatabaseUrl("https://webdoc-896a8.firebaseio.com/")
                .build();

        try {
            FirebaseApp app = FirebaseApp.initializeApp(context, options, "WebDocDoctorSDK");
            return app;
        } catch (IllegalStateException e) {
            return FirebaseApp.getInstance("WebDocDoctorSDK");
        }
    }
}