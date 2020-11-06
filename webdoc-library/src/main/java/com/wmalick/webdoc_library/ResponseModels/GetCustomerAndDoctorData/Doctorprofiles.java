
package com.wmalick.webdoc_library.ResponseModels.GetCustomerAndDoctorData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doctorprofiles {

    @SerializedName("Allqualifications")
    @Expose
    private String allqualifications;
    @SerializedName("ApplicationUserId")
    @Expose
    private String applicationUserId;
    @SerializedName("CityId")
    @Expose
    private String cityId;
    @SerializedName("CountryId")
    @Expose
    private String countryId;
    @SerializedName("DetailedInformation")
    @Expose
    private String detailedInformation;
    @SerializedName("DoctorSchedule")
    @Expose
    private Object doctorSchedule;
    @SerializedName("DoctorSpecialty")
    @Expose
    private String doctorSpecialty;
    @SerializedName("Dutytime")
    @Expose
    private String dutytime;
    @SerializedName("Education")
    @Expose
    private String education;
    @SerializedName("EducationInstitute")
    @Expose
    private String educationInstitute;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Experience")
    @Expose
    private String experience;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("ImgLink")
    @Expose
    private String imgLink;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("OnlineDoctor")
    @Expose
    private String onlineDoctor;
    @SerializedName("Rate")
    @Expose
    private String rate;
    @SerializedName("Status")
    @Expose
    private String status;

    public String getAllqualifications() {
        return allqualifications;
    }

    public void setAllqualifications(String allqualifications) {
        this.allqualifications = allqualifications;
    }

    public String getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(String applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getDetailedInformation() {
        return detailedInformation;
    }

    public void setDetailedInformation(String detailedInformation) {
        this.detailedInformation = detailedInformation;
    }

    public Object getDoctorSchedule() {
        return doctorSchedule;
    }

    public void setDoctorSchedule(Object doctorSchedule) {
        this.doctorSchedule = doctorSchedule;
    }

    public String getDoctorSpecialty() {
        return doctorSpecialty;
    }

    public void setDoctorSpecialty(String doctorSpecialty) {
        this.doctorSpecialty = doctorSpecialty;
    }

    public String getDutytime() {
        return dutytime;
    }

    public void setDutytime(String dutytime) {
        this.dutytime = dutytime;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducationInstitute() {
        return educationInstitute;
    }

    public void setEducationInstitute(String educationInstitute) {
        this.educationInstitute = educationInstitute;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOnlineDoctor() {
        return onlineDoctor;
    }

    public void setOnlineDoctor(String onlineDoctor) {
        this.onlineDoctor = onlineDoctor;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
