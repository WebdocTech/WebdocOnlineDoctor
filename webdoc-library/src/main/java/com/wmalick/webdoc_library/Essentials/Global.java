package com.wmalick.webdoc_library.Essentials;


import com.wmalick.webdoc_library.ResponseModels.CustomerConsultationResult.Consultationdetail;
import com.wmalick.webdoc_library.ResponseModels.CustomerConsultationResult.ConusltationList;
import com.wmalick.webdoc_library.ResponseModels.CustomerConsultationResult.CustomerConsultationResult;
import com.wmalick.webdoc_library.ResponseModels.DoctorListResult.DoctorListResult;
import com.wmalick.webdoc_library.ResponseModels.DoctorListResult.Doctorprofile;
import com.wmalick.webdoc_library.ResponseModels.GetCustomerData.GetCustomerDataApiResponse;

import java.util.ArrayList;

public class Global {

    public static String corporate = "";
    public static String THEME_COLOR_CODE = "#ffcc0000";

    /* TODO: Utils */
    public static Utils utils = new Utils();

    /* TODO: API Response Models Objects */
    public static DoctorListResult doctorListResponse = new DoctorListResult();
    public static CustomerConsultationResult customerConsultationResponse = new CustomerConsultationResult();
    public static GetCustomerDataApiResponse getCustomerDataApiResponse = new GetCustomerDataApiResponse();

    /* TODO: API Response Models Lists */
    public static ArrayList<Doctorprofile> doctorsList = new ArrayList<>();

    public static ArrayList<ConusltationList> customerConsultationList = new ArrayList<>();
    public static ArrayList<Consultationdetail> customerConsultationDetailsList = new ArrayList<>();

    /* TODO: Selected Lists Index */
    public static Doctorprofile selectedDoctor = new Doctorprofile();
    public static int selectedCustomerConsultationPosition;

    /*TODO: Doctor didn't pick the call*/
    public static boolean call_not_answered;

    /*TODO: Device token of doctor*/
    public static String selectedDoctorDeviceToken;

}
