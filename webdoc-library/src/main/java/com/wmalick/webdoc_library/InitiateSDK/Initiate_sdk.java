package com.wmalick.webdoc_library.InitiateSDK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wmalick.webdoc_library.Agora.Constant;
import com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsult.DoctorConsultActivity;
import com.wmalick.webdoc_library.Dashboard.WebdocDashboardActivity;
import com.wmalick.webdoc_library.Essentials.Constants;
import com.wmalick.webdoc_library.Essentials.Global;

import org.json.JSONException;
import org.json.JSONObject;

import com.wmalick.webdoc_library.ResponseModels.DoctorListResult.Doctorprofile;
import com.wmalick.webdoc_library.ResponseModels.GetCustomerAndDoctorData.GetCustomerAndDoctorDataApiResponse;
import com.wmalick.webdoc_library.ResponseModels.GetCustomerData.CustomerData;
import com.wmalick.webdoc_library.ResponseModels.GetCustomerData.GetCustomerDataApiResponse;
import com.wmalick.webdoc_library.ResponseModels.GetCustomerData.GetcustomerDataResult;
import com.wmalick.webdoc_library.ServerManager.ServerManager;
import com.wmalick.webdoc_library.ServerManager.VolleyListener;

public class Initiate_sdk implements VolleyListener {
    Activity ctx;
    private String UserMobileNumber, Corporate, DoctorID;
    ServerManager serverManager;

    /*Initialize SDK*/
    public Initiate_sdk(Activity ctx, String MobileNumber, String Corporate, String themeColorHexCode) {
        if(TextUtils.isEmpty(themeColorHexCode)){
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        }else if(themeColorHexCode.charAt(0) != '#'){
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        }else if(themeColorHexCode.length() < 7){
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        }else{
            Global.THEME_COLOR_CODE = themeColorHexCode;
            Global.corporate = Corporate;
            this.ctx = ctx;
            this.UserMobileNumber = MobileNumber;
            this.Corporate = Corporate;
            serverManager = new ServerManager(ctx, this);
            InitiateConnection();
        }
    }

    public Initiate_sdk(Activity ctx, String CustomerMobileNumber, String DoctorId, String Corporate, String themeColorHexCode) {
        if(TextUtils.isEmpty(themeColorHexCode)){
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        }else if(themeColorHexCode.charAt(0) != '#'){
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        }else if(themeColorHexCode.length() < 7){
            Toast.makeText(ctx, "Invalid Theme Code", Toast.LENGTH_SHORT).show();
        }else{
            Global.THEME_COLOR_CODE = themeColorHexCode;
            Global.corporate = Corporate;
            this.ctx = ctx;
            this.UserMobileNumber = CustomerMobileNumber;
            this.Corporate = Corporate;
            this.DoctorID = DoctorId;
            serverManager = new ServerManager(ctx, this);
            InitiateShortFlowConnection();
        }
    }

    private void InitiateShortFlowConnection() {
        Global.utils.showProgressDialog(ctx, "Initiating Connection");
        serverManager.GetCustomerAndDoctorData(UserMobileNumber, Corporate, DoctorID);
    }

    private void InitiateConnection() {
        Global.utils.showProgressDialog(ctx, "Initiating Connection");
        serverManager.GetCustomerData(UserMobileNumber, Corporate);
    }

    @Override
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException {
        Global.utils.hideProgressDialog();
        Gson gson = new Gson();
        if(requestType.equals(Constants.GET_CUSTOMER_AND_DOCTOR_DATA)){
            GetCustomerAndDoctorDataApiResponse responseModel  = gson.fromJson(response.toString(), GetCustomerAndDoctorDataApiResponse.class);
            if(responseModel.getGetcustomerDataSdkResult().getResponseCode().equals(Constants.SUCCESSCODE)){
                GetCustomerDataApiResponse getCustomerDataApiResponse = new GetCustomerDataApiResponse();
                CustomerData customerData = new CustomerData();
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
                GetcustomerDataResult getcustomerDataResult = new GetcustomerDataResult();
                getcustomerDataResult.setCustomerData(customerData);
                getcustomerDataResult.setMessage(responseModel.getGetcustomerDataSdkResult().getMessage());
                getcustomerDataResult.setResponseCode(responseModel.getGetcustomerDataSdkResult().getResponseCode());
                getCustomerDataApiResponse.setGetcustomerDataResult(getcustomerDataResult);
                Global.getCustomerDataApiResponse = getCustomerDataApiResponse;

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

            }else{
                Toast.makeText(ctx, responseModel.getGetcustomerDataSdkResult().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else if(requestType.equals(Constants.GET_CUSTOMER_DATA)){
            GetCustomerDataApiResponse responseModel = gson.fromJson(response.toString(), GetCustomerDataApiResponse.class);
            if(responseModel.getGetcustomerDataResult().getResponseCode().equals(Constants.SUCCESSCODE)){
                Global.getCustomerDataApiResponse = responseModel;
                ctx.startActivity(new Intent(ctx, WebdocDashboardActivity.class));
                /*ctx.finish();*/
            }else{
                Toast.makeText(ctx, responseModel.getGetcustomerDataResult().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
