package com.reha.controllers;

import com.reha.model.dto.PatientDto;
import com.reha.model.dto.PatientsDiagnosisDto;
import com.reha.services.PatientService;
import com.reha.services.PatientsDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patient/{patientId}/")
public class PatientsDiagnosisController {
    private static final String VIEWS_PATIENT_DIAGNOSIS_FORM = "diagnoses/PatientsDiagnosisForm";
    private final PatientsDiagnosisService patientsDiagnosisService;
    private final PatientService patientService;

    @Autowired
    public PatientsDiagnosisController(PatientsDiagnosisService patientsDiagnosisService, PatientService patientService) {
        this.patientsDiagnosisService = patientsDiagnosisService;
        this.patientService = patientService;
    }

    @ModelAttribute("patient")
    public PatientDto findAssignment(@PathVariable("patientId") int patientId) {
        return this.patientService.getPatientById(patientId);
    }

    @GetMapping("/diagnoses")
    public String initCreationForm(Model model) {
        model.addAttribute("patientsDiagnosis", new PatientsDiagnosisDto());
        model.addAttribute("diagnoses", patientsDiagnosisService.getAllDiagnoses());
        return VIEWS_PATIENT_DIAGNOSIS_FORM;
    }

    @PostMapping("/diagnoses")
    public String processCreationForm(@ModelAttribute("patient") PatientDto patient,
                                      @ModelAttribute("patientsDiagnosis") PatientsDiagnosisDto patientsDiagnosis) {
        patientsDiagnosisService.createPatientsDiagnosis(patientsDiagnosis, patient);
        return "redirect:/patient/{patientId}/details";
    }

    @GetMapping("/diagnoses/{diagnosisId}")
    public String initEditForm(@ModelAttribute("patient") PatientDto patient,
                               Model model, @PathVariable("diagnosisId") long diagnosisId) {
        model.addAttribute("patientsDiagnosis", patientsDiagnosisService.getPatientsDiagnosisById(diagnosisId));
        model.addAttribute("diagnoses", patientsDiagnosisService.getAllDiagnoses());
        return VIEWS_PATIENT_DIAGNOSIS_FORM;
    }

    @PostMapping("/diagnoses/{diagnosisId}")
    public String processEditForm(@ModelAttribute("patient") PatientDto patient,
                                  @ModelAttribute("patientsDiagnosis") PatientsDiagnosisDto patientsDiagnosis,
                                  @PathVariable("diagnosisId") long diagnosisId) {
        patientsDiagnosis.setId(diagnosisId);
        patientsDiagnosisService.updatePatientsDiagnosis(patientsDiagnosis);
        return "redirect:/patient/{patientId}/details";
    }
}
