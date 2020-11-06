
package com.wmalick.webdoc_library.ResponseModels.GetCustomerAndDoctorData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerProfile {

    @SerializedName("Age")
    @Expose
    private String age;
    @SerializedName("ApplicationUserId")
    @Expose
    private String applicationUserId;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("Disease")
    @Expose
    private String disease;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("Freecall")
    @Expose
    private String freecall;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Height")
    @Expose
    private String height;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("MartialStatusandFamily")
    @Expose
    private String martialStatusandFamily;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("PackageDateFrom")
    @Expose
    private String packageDateFrom;
    @SerializedName("PackageDateTo")
    @Expose
    private String packageDateTo;
    @SerializedName("PackageSubscribed")
    @Expose
    private String packageSubscribed;
    @SerializedName("VideoCalls")
    @Expose
    private String videoCalls;
    @SerializedName("VoiceCalls")
    @Expose
    private String voiceCalls;
    @SerializedName("Weight")
    @Expose
    private String weight;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(String applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFreecall() {
        return freecall;
    }

    public void setFreecall(String freecall) {
        this.freecall = freecall;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMartialStatusandFamily() {
        return martialStatusandFamily;
    }

    public void setMartialStatusandFamily(String martialStatusandFamily) {
        this.martialStatusandFamily = martialStatusandFamily;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPackageDateFrom() {
        return packageDateFrom;
    }

    public void setPackageDateFrom(String packageDateFrom) {
        this.packageDateFrom = packageDateFrom;
    }

    public String getPackageDateTo() {
        return packageDateTo;
    }

    public void setPackageDateTo(String packageDateTo) {
        this.packageDateTo = packageDateTo;
    }

    public String getPackageSubscribed() {
        return packageSubscribed;
    }

    public void setPackageSubscribed(String packageSubscribed) {
        this.packageSubscribed = packageSubscribed;
    }

    public String getVideoCalls() {
        return videoCalls;
    }

    public void setVideoCalls(String videoCalls) {
        this.videoCalls = videoCalls;
    }

    public String getVoiceCalls() {
        return voiceCalls;
    }

    public void setVoiceCalls(String voiceCalls) {
        this.voiceCalls = voiceCalls;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}
