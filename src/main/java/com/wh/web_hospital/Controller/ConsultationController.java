package com.wh.web_hospital.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.wh.web_hospital.Model.Consultation;
import com.wh.web_hospital.Repository.ConsultationRepository;
import com.wh.web_hospital.Service.Consultation.ConsultationRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ConsultationController {
    
    @Autowired
    private ConsultationRegister consultationRegister;

    @Autowired
    private ConsultationRepository consultationRepository;

    @PostMapping("/consultation/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Consultation create(@Valid @RequestBody Consultation consultation){
        return consultationRegister.create(consultation);
    }

    @GetMapping("/consultation")
    public List<Consultation> getConsultations(){
        return consultationRepository.findAll();
    }

    @GetMapping("/consultation/{id}")
    public ResponseEntity<Consultation> getConsultation(@PathVariable Long id){
        Optional<Consultation> consultation =  consultationRepository.findById(id);

        if(consultation.isPresent()){
            return ResponseEntity.ok(consultation.get());
        }
        
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/consultation/{id}/start")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void startConsultation(@PathVariable Long id){
        consultationRegister.startConsultation(id);
    }

    @PutMapping("/consultation/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cacenlConsultation(@PathVariable Long id){
        consultationRegister.cancelConsultation(id);
    }

    @PutMapping("/consultation/{id}/finish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finishConsultation(@PathVariable Long id, @RequestBody String resume){
        consultationRegister.finishConsultation(id,resume);
    }

}
