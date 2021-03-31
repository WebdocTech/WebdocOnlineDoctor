package com.wmalick.webdoc_library.Essentials;

/**
 * Created by Admin on 7/3/2019.
 */

public class Constants {
    /*TODO: REAL URL*/
    public static final String BASE_URL = "https://webdocapiservice.webddocsystems.com/iOSApp.svc/";
    public static final String BASE_URL_KK = "https://kz.webddocsystems.com/";

    /*TODO: TESTING URL*/
    //public static final String BASE_URL = "https://webdoctesting.webddocsystems.com/iOSApp.svc/";

    public static final String DOCTORS_LIST_API = "DoctorList";
    public static final String CUSTOMER_CONSULTATION_API = "CustomerConsultation";
    public static final String GET_CUSTOMER_DATA = "GetCustomerData";
    public static final String GET_CUSTOMER_AND_DOCTOR_DATA = "GetcustomerDataSdk";
    public static final String WEBDOC_FEEDBACK = "WebdocFeedback";

    public static final String GET_DATA_KK = "Kk/v1/AllocateDoctor";

    /*  TODO: Details api key */
    public static String doctorsKey = "f9ac31e833bd4eba815c89ea3ac5ae5a";
    public static String patientCity = "Islamabad";

    /* TODO: Prescription api key */
    public static String prescriptionHistoryKey =  "e7c96f5f-d412-4a26-bf67-dbed9a0c9819";
    public static final String SUCCESSCODE = "0000";
    public static final String FAILURECODE = "0001";

    /*TODO: FIREBASE NOTIFICATIONS KEYS */
    public static final String FIREBASE_SERVER_KEY = "key=AAAAlaKRvi4:APA91bE3dXivguMI7ENzztZjd9vNx256Zx-h8-LsfNwFTl-nL6gZMNVKLTJ-Kkh4N7jTs_BVhnx0BV78cKCxU5XtSEOiS3lbJQKn0rfzRlhduMGC049iCJ9S3QUh_J-lKuX_1GQG4PmH";
    public static final String FIREBASE_SERVER_KEY_AGRIEXPERT = "key=AAAAtMoZmiI:APA91bHJuoliDNEv6IUix2RhjJ4JFTe3lrR0GSLys0uv9VqX5ePwlS-msdttGhsm7nc5blZvAQZvpUPx-oAm3ZEo_tR-RXBHQ6MFeasy6y0oz2dk-zTPe2D54meaWP-jWF6_PBzmmI9m";
    public static final String  FIREBASE_NOTIFICATION_URL = "https://fcm.googleapis.com/fcm/send";
}
