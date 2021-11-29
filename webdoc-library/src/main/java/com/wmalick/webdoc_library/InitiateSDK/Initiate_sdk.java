package com.wmalick.webdoc_library.InitiateSDK;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.wmalick.webdoc_library.AgoraNew.VideoCall.VideoCall;
import com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsult.DoctorConsultActivity;
import com.wmalick.webdoc_library.Dashboard.WebdocDashboardActivity;
import com.wmalick.webdoc_library.Dashboard.modelclasses.AllocateLawyerResult;
import com.wmalick.webdoc_library.Dashboard.modelclasses.Doctorprofile;
import com.wmalick.webdoc_library.Dashboard.modelclasses.LawyerModel;
import com.wmalick.webdoc_library.Essentials.Constants;
import com.wmalick.webdoc_library.Essentials.Global;

import com.wmalick.webdoc_library.InitiateSDK.modelclasses.KK_modelclasses.AllocateDoctorModel;
import com.wmalick.webdoc_library.InitiateSDK.modelclasses.KK_modelclasses.AllocateDoctorResult;
import com.wmalick.webdoc_library.InitiateSDK.modelclasses.TPL_modelclasses.CustomerDataModel;
import com.wmalick.webdoc_library.InitiateSDK.modelclasses.customerdoctor_modelclasses.CustomerProfile;
import com.wmalick.webdoc_library.InitiateSDK.modelclasses.customerdoctor_modelclasses.GetCustomerAndDoctorModel;
import com.wmalick.webdoc_library.InitiateSDK.modelclasses.customerdoctor_modelclasses.GetcustomerDataSdkResult;
import com.wmalick.webdoc_library.api.APIClient;
import com.wmalick.webdoc_library.api.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Initiate_sdk {
    Activity ctx;
    private String UserMobileNumber, Corporate, DoctorID, token;

    /*Initialize SDK*/
    public Initiate_sdk(Activity ctx, String MobileNumber, String Corporate, String themeColorHexCode) {
        if (TextUtils.isEmpty(themeColorHexCode)) {
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        } else if (themeColorHexCode.charAt(0) != '#') {
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        } else if (themeColorHexCode.length() < 7) {
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        } else {
            Global.THEME_COLOR_CODE = themeColorHexCode;
            Global.corporate = Corporate;
            this.ctx = ctx;
            this.UserMobileNumber = MobileNumber;
            this.Corporate = Corporate;
            //serverManager = new ServerManager(ctx, this);
            InitiateConnection();
        }
    }

    public Initiate_sdk(Activity ctx, String CustomerMobileNumber, String DoctorId, String Corporate, String themeColorHexCode) {
        if (TextUtils.isEmpty(themeColorHexCode)) {
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        } else if (themeColorHexCode.charAt(0) != '#') {
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        } else if (themeColorHexCode.length() < 7) {
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        } else {
            Global.THEME_COLOR_CODE = themeColorHexCode;
            Global.corporate = Corporate;
            this.ctx = ctx;
            this.UserMobileNumber = CustomerMobileNumber;
            this.Corporate = Corporate;
            this.DoctorID = DoctorId;
            //serverManager = new ServerManager(ctx, this);
            InitiateShortFlowConnection();
        }
    }

    public Initiate_sdk(Activity ctx, String patientEmail, String serviceName, String themeColorHexCode, boolean connection) {
        if (TextUtils.isEmpty(themeColorHexCode)) {
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        } else if (themeColorHexCode.charAt(0) != '#') {
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        } else if (themeColorHexCode.length() < 7) {
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        } else {
            Global.THEME_COLOR_CODE = themeColorHexCode;
            Global.corporate = serviceName;
            this.ctx = ctx;

            this.UserMobileNumber = patientEmail;
            Global.patientEmail = this.UserMobileNumber;

            this.Corporate = serviceName;
            //serverManager = new ServerManager(ctx, this);
            InitiateServicesConnection();
        }
    }

    private void InitiateShortFlowConnection() {
        callGetCustomerAndDoctorDataApi(ctx, UserMobileNumber, Corporate, DoctorID);
    }

    private void InitiateConnection() {
        callCustomerDataApi(ctx, UserMobileNumber, Corporate);
    }

    private void InitiateServicesConnection() {
        //Global.utils.showProgressDialog(ctx, "Initiating Connection");
        if (this.Corporate.equalsIgnoreCase("KK")) {
            callAllocateDoctorKKApi(ctx);
        } else if (this.Corporate.equalsIgnoreCase("KM")) {
            callAllocateDoctorKMApi(ctx);
        } else if (this.Corporate.equalsIgnoreCase("KS")) {
            callAllocateDoctorKSApi(ctx);
        } else if (this.Corporate.equalsIgnoreCase("QMS")) {
            callAllocateLawyerQMSApi(ctx);
        }
    }

    public void callGetCustomerAndDoctorDataApi(Activity activity, String phoneNo, String corporate, String dID) {
        if (!Global.utils.isInternerConnected(activity)) {
            Global.utils.showToast(activity, "No internet connection !");
        } else {

            Global.utils.showProgressDialog(ctx, "Initiating Connection");

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("CustomerMobilenumber", phoneNo);
            jsonObject.addProperty("Corporate", corporate);
            jsonObject.addProperty("Doctorid", dID);

            Log.e("webdoc_doctors_sdk", jsonObject.toString());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<GetCustomerAndDoctorModel> call = apiInterface.callGetCustomerAndDoctorDataApi(jsonObject);

            call.enqueue(new Callback<GetCustomerAndDoctorModel>() {
                @Override
                public void onResponse(Call<GetCustomerAndDoctorModel> call, Response<GetCustomerAndDoctorModel> response) {
                    Global.utils.hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {

                            GetCustomerAndDoctorModel responseModel = response.body();
                            if (responseModel.getGetcustomerDataSdkResult().getResponseCode().equals(Constants.SUCCESSCODE)) {
                                GetCustomerAndDoctorModel getCustomerAndDoctorModel = new GetCustomerAndDoctorModel();
                                CustomerProfile customerData = new CustomerProfile();
                                customerData.setAge(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getAge());
                                customerData.setApplicationUserId(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getApplicationUserId());
                                customerData.setCity(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getCity());
                                customerData.setCountry(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getCountry());
                                customerData.setDisease(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getDisease());
                                customerData.setEmail(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getEmail());
                                customerData.setFirstName(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getFirstName());
                                customerData.setFreecall(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getFreecall());
                                customerData.setGender(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getGender());
                                customerData.setHeight(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getHeight());
                                customerData.setLastName(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getLastName());
                                customerData.setMartialStatusandFamily(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getMartialStatusandFamily());
                                customerData.setMobileNumber(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getMobileNumber());
                                customerData.setPackageDateFrom(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getPackageDateFrom());
                                customerData.setPackageDateTo(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getPackageDateTo());
                                customerData.setPackageSubscribed(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getPackageSubscribed());
                                customerData.setVideoCalls(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getVideoCalls());
                                customerData.setVoiceCalls(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getVoiceCalls());
                                customerData.setWeight(responseModel.getGetcustomerDataSdkResult().getCustomerProfile().getWeight());

                                GetcustomerDataSdkResult getcustomerDataSdkResult = new GetcustomerDataSdkResult();
                                getcustomerDataSdkResult.setCustomerProfile(customerData);
                                getcustomerDataSdkResult.setMessage(responseModel.getGetcustomerDataSdkResult().getMessage());
                                getcustomerDataSdkResult.setResponseCode(responseModel.getGetcustomerDataSdkResult().getResponseCode());
                                getCustomerAndDoctorModel.setGetcustomerDataSdkResult(getcustomerDataSdkResult);

                                //Global.getCustomerDataApiResponse = getCustomerAndDoctorModel;

                                Doctorprofile doctorprofile = new Doctorprofile();
                                doctorprofile.setAllqualifications(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getAllqualifications());
                                doctorprofile.setApplicationUserId(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getApplicationUserId());
                                doctorprofile.setCityId(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getCityId());
                                doctorprofile.setCountryId(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getCountryId());
                                doctorprofile.setDetailedInformation(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getDetailedInformation());
                                doctorprofile.setDoctorSpecialty(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getDoctorSpecialty());
                                doctorprofile.setDutytime(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getDutytime());
                                doctorprofile.setEducation(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getEducation());
                                doctorprofile.setEducationInstitute(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getEducationInstitute());
                                doctorprofile.setEmail(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getEmail());
                                doctorprofile.setExperience(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getExperience());
                                doctorprofile.setFirstName(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getFirstName());
                                doctorprofile.setImgLink(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getImgLink());
                                doctorprofile.setLastName(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getLastName());
                                doctorprofile.setOnlineDoctor(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getOnlineDoctor());
                                doctorprofile.setRate(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getRate());
                                doctorprofile.setStatus(responseModel.getGetcustomerDataSdkResult().getDoctorprofiles().getStatus());

                                Global.selectedDoctor = doctorprofile;
                                ctx.startActivity(new Intent(ctx, DoctorConsultActivity.class));

                            } else {
                                Toast.makeText(ctx, responseModel.getGetcustomerDataSdkResult().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetCustomerAndDoctorModel> call, Throwable t) {
                    Global.utils.hideProgressDialog();
                    //Log.e(TAG, t.toString());
                    Toast.makeText(activity, "Ooops! something went wrong !", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    public void callCustomerDataApi(Activity activity, String phoneNo, String corporate) {
        if (!Global.utils.isInternerConnected(activity)) {
            Global.utils.showToast(activity, "No internet connection !");
        } else {

            Global.utils.showProgressDialog(ctx, "Initiating Connection");

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("CustomerMobilenumber", phoneNo);
            jsonObject.addProperty("Corporate", corporate);

            Log.e("webdoc_doctors_sdk", jsonObject.toString());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<CustomerDataModel> call = apiInterface.callCustomerDataApi(jsonObject);

            call.enqueue(new Callback<CustomerDataModel>() {
                @Override
                public void onResponse(Call<CustomerDataModel> call, Response<CustomerDataModel> response) {
                    Global.utils.hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {
                            if (response.body().getGetcustomerDataResult().getResponseCode().equals(Constants.SUCCESSCODE)) {
                                Global.getCustomerDataModel = response.body();
                                ctx.startActivity(new Intent(ctx, WebdocDashboardActivity.class));
                            } else {
                                Toast.makeText(ctx, response.body().getGetcustomerDataResult().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CustomerDataModel> call, Throwable t) {
                    Global.utils.hideProgressDialog();
                    //Log.e(TAG, t.toString());
                    Toast.makeText(activity, "Ooops! something went wrong !", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    public void callAllocateDoctorKKApi(Activity activity) {
        if (!Global.utils.isInternerConnected(activity)) {
            Global.utils.showToast(activity, "No internet connection !");
        } else {
            Global.utils.showProgressDialog(ctx, "Initiating Connection");
            JsonObject jsonObject = new JsonObject();
            Log.e("webdoc_doctors_sdk", jsonObject.toString());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL_SERVICES);
            Call<AllocateDoctorModel> call = apiInterface.callAllocateDoctorApi(jsonObject);

            call.enqueue(new Callback<AllocateDoctorModel>() {
                @Override
                public void onResponse(Call<AllocateDoctorModel> call, Response<AllocateDoctorModel> response) {
                    Global.utils.hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {
                            AllocateDoctorResult allocateDoctorResult = response.body().getAllocateDoctorResult();
                            if (allocateDoctorResult.getStatusCode().equals(Constants.FAILURECODE)) {
                                Global.allocateDoctorResponse = allocateDoctorResult;
                                DoctorID = allocateDoctorResult.getDoctorEmail();
                                Global.channel = DoctorID;
                                Global.selectedDoctorDeviceToken = allocateDoctorResult.getDoctorDeviceToken();
                                ctx.startActivity(new Intent(ctx, VideoCall.class));

                            } else {
                                Toast.makeText(ctx, allocateDoctorResult.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AllocateDoctorModel> call, Throwable t) {
                    Global.utils.hideProgressDialog();
                    //Log.e(TAG, t.toString());
                    Toast.makeText(activity, "Ooops! something went wrong !", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    public void callAllocateDoctorKMApi(Activity activity) {
        if (!Global.utils.isInternerConnected(activity)) {
            Global.utils.showToast(activity, "No internet connection !");
        } else {
            Global.utils.showProgressDialog(ctx, "Initiating Connection");
            JsonObject jsonObject = new JsonObject();
            Log.e("webdoc_doctors_sdk", jsonObject.toString());
            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL_SERVICES);
            Call<AllocateDoctorModel> call = apiInterface.callAllocateDoctorKMApi(jsonObject);

            call.enqueue(new Callback<AllocateDoctorModel>() {
                @Override
                public void onResponse(Call<AllocateDoctorModel> call, Response<AllocateDoctorModel> response) {
                    Global.utils.hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {
                            AllocateDoctorResult allocateDoctorResult = response.body().getAllocateDoctorResult();
                            if (allocateDoctorResult.getStatusCode().equals(Constants.FAILURECODE)) {
                                Global.allocateDoctorResponse = allocateDoctorResult;
                                DoctorID = allocateDoctorResult.getDoctorEmail();
                                Global.channel = DoctorID;
                                Global.selectedDoctorDeviceToken = allocateDoctorResult.getDoctorDeviceToken();
                                ctx.startActivity(new Intent(ctx, VideoCall.class));
                            } else {
                                Toast.makeText(ctx, allocateDoctorResult.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AllocateDoctorModel> call, Throwable t) {
                    Global.utils.hideProgressDialog();
                    //Log.e(TAG, t.toString());
                    Toast.makeText(activity, "Oopps! something went wrong !", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    public void callAllocateDoctorKSApi(Activity activity) {
        if (!Global.utils.isInternerConnected(activity)) {
            Global.utils.showToast(activity, "No internet connection !");
        } else {

            Global.utils.showProgressDialog(ctx, "Initiating Connection");

            JsonObject jsonObject = new JsonObject();

            Log.e("webdoc_doctors_sdk", jsonObject.toString());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL_SERVICES);
            Call<AllocateDoctorModel> call = apiInterface.callAllocateDoctorKSApi(jsonObject);

            call.enqueue(new Callback<AllocateDoctorModel>() {
                @Override
                public void onResponse(Call<AllocateDoctorModel> call, Response<AllocateDoctorModel> response) {
                    Global.utils.hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {

                            AllocateDoctorResult allocateDoctorResult = response.body().getAllocateDoctorResult();
                            if (allocateDoctorResult.getStatusCode().equals(Constants.FAILURECODE)) {

                                Global.allocateDoctorResponse = allocateDoctorResult;
                                DoctorID = allocateDoctorResult.getDoctorEmail();
                                Global.channel = DoctorID;
                                Global.selectedDoctorDeviceToken = allocateDoctorResult.getDoctorDeviceToken();
                                ctx.startActivity(new Intent(ctx, VideoCall.class));

                            } else {
                                Toast.makeText(ctx, allocateDoctorResult.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AllocateDoctorModel> call, Throwable t) {
                    Global.utils.hideProgressDialog();
                    //Log.e(TAG, t.toString());
                    Toast.makeText(activity, "Ooops! something went wrong !", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    public void callAllocateLawyerQMSApi(Activity activity) {
        if (!Global.utils.isInternerConnected(activity)) {
            Global.utils.showToast(activity, "No internet connection !");
        } else {

            Global.utils.showProgressDialog(ctx, "Initiating Connection");

            JsonObject jsonObject = new JsonObject();

            Log.e("webdoc_doctors_sdk", jsonObject.toString());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL_SERVICES);
            Call<LawyerModel> call = apiInterface.callAllocateLawyerApi(jsonObject);

            call.enqueue(new Callback<LawyerModel>() {
                @Override
                public void onResponse(Call<LawyerModel> call, Response<LawyerModel> response) {
                    Global.utils.hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {

                            AllocateLawyerResult allocateDoctorResult = response.body().getAllocateLawyerResult();
                            if (allocateDoctorResult.getStatusCode().equals(Constants.FAILURECODE)) {

                                Global.allocateLawyerResult = allocateDoctorResult;
                                DoctorID = allocateDoctorResult.getDoctorEmail();
                                Global.channel = DoctorID;
                                Global.selectedDoctorDeviceToken = "";
                                ctx.startActivity(new Intent(ctx, VideoCall.class));

                            } else {
                                Toast.makeText(ctx, allocateDoctorResult.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LawyerModel> call, Throwable t) {
                    Global.utils.hideProgressDialog();
                    //Log.e(TAG, t.toString());
                    Toast.makeText(activity, "Ooops! something went wrong !", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

}
