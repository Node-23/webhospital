package com.wh.web_hospital.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.wh.web_hospital.Model.Patient;
import com.wh.web_hospital.Repository.PatientRepository;
import com.wh.web_hospital.Service.Patient.PatientRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/webhospital")
public class PatientController {
    
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientRegister patientRegister;

    @GetMapping("/patients")
    public List<Patient> getPatients(){
        return patientRepository.findAll();
    }

    @GetMapping("/patients/contains")
    public List<Patient> getPatientsContains(@RequestBody String name){
        return patientRepository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable(value = "id") long id){
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isPresent()){
            return ResponseEntity.ok(patient.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/patients/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Patient newPatient(@Valid @RequestBody Patient patient){
        return patientRegister.register(patient);
    }

    @PutMapping("/patients/edit/{id}")
    public ResponseEntity<Patient> editPatient(@Valid @PathVariable Long id,@RequestBody Patient patient){
        if(!patientRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        patient.setId(id);
        patient = patientRegister.register(patient);

        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/patients/delete/{id}")
    public ResponseEntity<Void> removePatient(@PathVariable long id){

        if(!patientRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        patientRegister.delete(id);
        return ResponseEntity.noContent().build();

    }

}
