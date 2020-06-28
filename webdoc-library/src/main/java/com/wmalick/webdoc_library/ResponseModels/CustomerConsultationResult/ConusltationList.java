
package com.wmalick.webdoc_library.ResponseModels.CustomerConsultationResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConusltationList {

    @SerializedName("Compliant")
    @Expose
    private String compliant;
    @SerializedName("ConsultationDate")
    @Expose
    private String consultationDate;
    @SerializedName("ConsultationType")
    @Expose
    private String consultationType;
    @SerializedName("Consultationdetails")
    @Expose
    private List<Consultationdetail> consultationdetails = null;
    @SerializedName("Diagnosis")
    @Expose
    private Object diagnosis;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("Prescription")
    @Expose
    private String prescription;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("Tests")
    @Expose
    private String tests;
    @SerializedName("id")
    @Expose
    private String id;

    public String getCompliant() {
        return compliant;
    }

    public void setCompliant(String compliant) {
        this.compliant = compliant;
    }

    public String getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(String consultationDate) {
        this.consultationDate = consultationDate;
    }

    public String getConsultationType() {
        return consultationType;
    }

    public void setConsultationType(String consultationType) {
        this.consultationType = consultationType;
    }

    public List<Consultationdetail> getConsultationdetails() {
        return consultationdetails;
    }

    public void setConsultationdetails(List<Consultationdetail> consultationdetails) {
        this.consultationdetails = consultationdetails;
    }

    public Object getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Object diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
