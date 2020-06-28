package com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorProfile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorProfileFargment extends Fragment {

    TextView tv_DocName, textView_Speciality, textView_Education, text_Degree, text_College, textView_Years, text_ExpDetail;


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

        return view;
    }

    private void ActionControl(View view) {

        tv_DocName.setText(Global.selectedDoctor.getFirstName() + " " + Global.selectedDoctor.getLastName());
        textView_Speciality.setText(Global.selectedDoctor.getDoctorSpecialty());
        textView_Education.setText(Global.selectedDoctor.getEducation());
        text_Degree.setText(Global.selectedDoctor.getEducation());
        text_College.setText(Global.selectedDoctor.getEducationInstitute());
        textView_Years.setText(Global.selectedDoctor.getExperience());
        text_ExpDetail.setText(Global.selectedDoctor.getDetailedInformation());

    }

    private void InitControl(View view) {

        tv_DocName = view.findViewById(R.id.tv_DocName);
        textView_Speciality = view.findViewById(R.id.textView_Speciality);
        textView_Education = view.findViewById(R.id.textView_Education);
        text_Degree = view.findViewById(R.id.text_Degree);
        text_College = view.findViewById(R.id.text_College);
        textView_Years = view.findViewById(R.id.textView_Years);
        text_ExpDetail = view.findViewById(R.id.text_ExpDetail);
    }

}
