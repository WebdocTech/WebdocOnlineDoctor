package com.wmalick.webdoc_library.api;
import com.google.gson.JsonObject;
import com.wmalick.webdoc_library.Dashboard.modelclasses.DoctorsListModel;
import com.wmalick.webdoc_library.Dashboard.modelclasses.FeedbackModel;
import com.wmalick.webdoc_library.Dashboard.modelclasses.LawyerModel;
import com.wmalick.webdoc_library.InitiateSDK.modelclasses.KK_modelclasses.AllocateDoctorModel;
import com.wmalick.webdoc_library.InitiateSDK.modelclasses.TPL_modelclasses.CustomerDataModel;
import com.wmalick.webdoc_library.InitiateSDK.modelclasses.customerdoctor_modelclasses.GetCustomerAndDoctorModel;
import com.wmalick.webdoc_library.InitiateSDK.modelclasses.CustomerConsultationResult.CustomerConsultationResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("DoctorList")
    Call<DoctorsListModel> callDoctorsListApi(@Body JsonObject mojb);

    @POST("CustomerConsultation")
    Call<CustomerConsultationResult> callCustomerConsultationApi(@Body JsonObject mojb);

    @POST("WebdocFeedback")
    Call<FeedbackModel> callFeedbackApi(@Body JsonObject mojb);

    @POST("GetCustomerData")
    Call<CustomerDataModel> callCustomerDataApi(@Body JsonObject mojb);

    @POST("Kk/v1/AllocateDoctor")
    Call<AllocateDoctorModel> callAllocateDoctorApi(@Body JsonObject mojb);

    @POST("Km/v1/AllocateDoctor")
    Call<AllocateDoctorModel> callAllocateDoctorKMApi(@Body JsonObject mojb);

    @POST("Ks/v1/AllocateDoctor")
    Call<AllocateDoctorModel> callAllocateDoctorKSApi(@Body JsonObject mojb);

    @POST("QMS/v1/AllocateLawyer")
    Call<LawyerModel> callAllocateLawyerApi(@Body JsonObject mojb);

    @POST("GetcustomerDataSdk")
    Call<GetCustomerAndDoctorModel> callGetCustomerAndDoctorDataApi(@Body JsonObject mojb);

    /*@POST("Login")
    Call<loginResponseModel> callLoginApi(@Body JsonObject mojb);

    @POST("CabServiceHistory")
    Call<bookingHistory_model> callBookingHistoryApi(@Body JsonObject mojb);

    @POST("UpdatePassword")
    Call<passwordUpdateModel> callUpdatePasswordApi(@Body JsonObject mojb);

    @POST("DeleteFamilyMember")
    Call<LoginResult> callDeleteFamilyMemberApi(@Body JsonObject mojb);

    @POST("GetDriverLocations")
    Call<nearbyDriversLocation_model> callNearByDriversApi(@Body JsonObject mojb);

    @POST("GetActiveRidesFamilyMembers")
    Call<familyMemberActiveRides> callActiveRideFamilyMemberApi(@Body JsonObject mojb);

    @POST("AddFamilyMembers")
    Call<LoginResult> callAddFamilyMemberApi(@Body JsonObject mojb);

    @POST("retryBookingRequest")
    Call<bookingDailyRideModel> callRetryBookingRequestApi(@Body JsonObject mojb);

    @POST("CancelDailyBookingRide")
    Call<getCancelDailyRideResponse> callCancelDailyBookingApi(@Body JsonObject mojb);

    @POST("BookingDailyRide")
    Call<bookingDailyRideModel> callBookingDailyRideApi(@Body JsonObject mojb);

    @POST("PickAnDropRequest")
    Call<LoginResult> callBookingFamilyMemberApi(@Body JsonObject mojb);

    @POST("PickAndDropHistory")
    Call<pickDropHistory> callPickDropHistoryApi(@Body JsonObject mojb);

    @POST("UpdateFamilyProfile")
    Call<LoginResult> callUpdateFamilyMemberProfileApi(@Body JsonObject mojb);

    @POST("RateARide")
    Call<rateA_RideResponse> callRateARideApi(@Body JsonObject mojb);

    @POST("trackvehicle")
    Call<trackVehicleModel> callTrackVehicleApi(@Body JsonObject mojb);

    @POST("SaveSos")
    Call<sos_model> callSaveSOSApi(@Body JsonObject mojb);

    @POST("CancelPickAndDropRequest")
    Call<LoginResult> callCancelPickandDropRequestApi(@Body JsonObject mojb);

    @POST("CancelBookingRequest")
    Call<LoginResult> callCancelBookingRequestApi(@Body JsonObject mojb);*/

}