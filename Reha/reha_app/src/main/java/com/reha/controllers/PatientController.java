package com.reha.controllers;

import com.reha.model.dto.PatientDto;
import com.reha.model.dto.UserEmployeeDto;
import com.reha.services.ClinicService;
import com.reha.services.PatientService;
import com.reha.utils.exceptions.PatientNotFoundException;
import com.reha.utils.validators.PatientDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private static final String VIEWS_PATIENT_FORM = "patients/PatientForm";
    private static final String VIEWS_PATIENT_LIST = "patients/PatientsList";
    private static final String VIEWS_PATIENT_DETAILS = "patients/PatientDetails";

    private final PatientService patientService;
    private final ClinicService clinicService;
    private final PatientDtoValidator patientDtoValidator;

    @Autowired
    public PatientController(PatientService patientService, ClinicService clinicService, PatientDtoValidator patientDtoValidator) {
        this.patientService = patientService;
        this.clinicService = clinicService;
        this.patientDtoValidator = patientDtoValidator;
    }

    @ModelAttribute("doctors")
    public Collection<UserEmployeeDto> getPatientsList() {
        return patientService.getDoctorList();
    }

    @GetMapping("/list")
    public String patientsList(Model model) {
        model.addAttribute("patients", patientService.getAll());
        return VIEWS_PATIENT_LIST;
    }

    @GetMapping("/{patientId}")
    public String initEditForm(@PathVariable("patientId") long id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        model.addAttribute("doctors", patientService.getDoctorList());
        return VIEWS_PATIENT_FORM;
    }

    @PostMapping("/{patientId}")
    public String processEditForm(@PathVariable("patientId") long id, PatientDto patient) {
        patient.setId(id);
        patientService.updatePatient(patient);
        return "redirect:/patient/{patientId}/details";
    }

    @GetMapping("")
    public String initCreationForm(Model model, @ModelAttribute("patient") PatientDto patient) {
        return VIEWS_PATIENT_FORM;
    }

    @PostMapping("")
    public String processCreationForm(@ModelAttribute("patient") @Valid PatientDto patient,
                                      BindingResult result) {
        patientDtoValidator.validate(patient, result);
        if (result.hasErrors()) {
            return VIEWS_PATIENT_FORM;
        }
        patientService.createPatient(patient);
        return "redirect:/patient/list/";
    }

    @GetMapping("/{patientId}/details")
    public String showPatient(Model model, @PathVariable("patientId") int id) {
        if (patientService.getPatientById(id) == null) {
            throw new PatientNotFoundException("PatNotFound");
        }
        model.addAttribute("patient", patientService.getPatientById(id));
        model.addAttribute("doctorName", patientService.getDoctorById(patientService.getPatientById(id).getDoctorId()).getName());
        return VIEWS_PATIENT_DETAILS;
    }

    @GetMapping("/{patientId}/discharge")
    public String dischargePatient(Model model, @PathVariable("patientId") int id) {
        clinicService.dischargePatient(id);
        return "redirect:/patient/" + id + "/details";
    }

    @GetMapping("/{patientId}/delete")
    public String patientDelete(@PathVariable("patientId") int id) {
        patientService.deletePatientById(id);
        return "redirect:/patient/list/";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }
}
