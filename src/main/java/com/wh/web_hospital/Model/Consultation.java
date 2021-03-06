package com.wh.web_hospital.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.wh.web_hospital.Domain.ValidationGroups;
import com.wh.web_hospital.Exceptions.ServicesExceptions;

@Entity
public class Consultation implements Serializable {
 
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Valid
    @ConvertGroup(from = Default.class ,to = ValidationGroups.DoctorId.class)
    @ManyToOne
    @NotNull
    private Doctor doctor;

    @Valid
    @ConvertGroup(from = Default.class ,to = ValidationGroups.PatientId.class)
    @ManyToOne
    @NotNull
    private Patient patient;
    
    private LocalDate consultationDateScheduled;
    
    @NotNull
    private LocalDate consultationDate;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetTime consultationStartedTime;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetTime consultationFinishedTime;

    private String consultationResume;

    @NotNull
    private float price;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = Access.READ_ONLY)
    private ConsultationStatus status;
    

    public Consultation() {
    }

    public Consultation(long id, Doctor doctor, Patient patient,LocalDate consultationDateScheduled, LocalDate consultationDate, OffsetTime consultationStartedTime, OffsetTime consultationFinishedTime, String consultationResume, float price, ConsultationStatus status) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.consultationDateScheduled = consultationDateScheduled;
        this.consultationDate = consultationDate;
        this.consultationStartedTime = consultationStartedTime;
        this.consultationFinishedTime = consultationFinishedTime;
        this.consultationResume = consultationResume;
        this.price = price;
        this.status = status;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getConsultationDateScheduled() {
        return this.consultationDateScheduled;
    }

    public void setConsultationDateScheduled(LocalDate consultationDateScheduled) {
        this.consultationDateScheduled = consultationDateScheduled;
    }

    public LocalDate getConsultationDate() {
        return this.consultationDate;
    }

    public void setConsultationDate(LocalDate consultationDate) {
        this.consultationDate = consultationDate;
    }

    public OffsetTime getConsultationStartedTime() {
        return this.consultationStartedTime;
    }

    public void setConsultationStartedTime(OffsetTime consultationStartedTime) {
        this.consultationStartedTime = consultationStartedTime;
    }

    public OffsetTime getConsultationFinishedTime() {
        return this.consultationFinishedTime;
    }

    public void setConsultationFinishedTime(OffsetTime consultationFinishedTime) {
        this.consultationFinishedTime = consultationFinishedTime;
    }

    public String getConsultationResume() {
        return this.consultationResume;
    }

    public void setConsultationResume(String consultationResume) {
        this.consultationResume = consultationResume;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public ConsultationStatus getStatus() {
        return this.status;
    }

    public void setStatus(ConsultationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Consultation)) {
            return false;
        }
        Consultation consultation = (Consultation) o;
        return id == consultation.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void startConsultation() {
        if(!ConsultationStatus.SCHEDULED.equals(getStatus())){
            throw new ServicesExceptions("Cannot start an unscheduled consultation");
        }

        setStatus(ConsultationStatus.STARTED);
        setConsultationStartedTime(OffsetTime.now());
    }

    public void cancelConsultation() {
        if(ConsultationStatus.CANCELED.equals(getStatus())){
            throw new ServicesExceptions("Consultation already canceled");
        }

        setStatus(ConsultationStatus.CANCELED);
    }

    public void finishConsultation() {
        if(!ConsultationStatus.STARTED.equals(getStatus())){
            throw new ServicesExceptions("Cannot finish an unstarted consultation");
        }

        setStatus(ConsultationStatus.FINISHED);
        setConsultationFinishedTime(OffsetTime.now());
    }
    
}