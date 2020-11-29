package com.reha.controllers;

import com.reha.model.dto.AssignmentDto;
import com.reha.model.dto.PatientDto;
import com.reha.model.dto.TreatmentDto;
import com.reha.model.enums.IntervalTypes;
import com.reha.services.AssignmentService;
import com.reha.services.ClinicService;
import com.reha.services.PatientService;
import com.reha.services.TreatmentService;
import com.reha.utils.DateUtils;
import com.reha.utils.validators.AssignmentDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/patient/{patientId}")
public class AssignmentController {

    private static final String VIEWS_ASSIGNMENT_FORM = "assignments/AssignmentForm";
    private final PatientService patientService;
    private final AssignmentService assignmentService;
    private final TreatmentService treatmentService;
    private final ClinicService clinicService;
    private final AssignmentDtoValidator validator;

    @Autowired
    public AssignmentController(PatientService patientService, AssignmentService assignmentService, TreatmentService treatmentService, ClinicService clinicService, AssignmentDtoValidator validator) {
        this.patientService = patientService;
        this.treatmentService = treatmentService;
        this.assignmentService = assignmentService;
        this.clinicService = clinicService;
        this.validator = validator;
    }

    @ModelAttribute("patient")
    public PatientDto findPatient(@PathVariable("patientId") int patientId) {
        return this.patientService.getPatientById(patientId);
    }

    @ModelAttribute("daysOfWeekList")
    public List<String> initD() {
        return DateUtils.getDaysList();
    }

    @ModelAttribute("intervalsTypes")
    public IntervalTypes[] getIntervals() {
        return IntervalTypes.values();
    }

    @ModelAttribute("treatments")
    public List<TreatmentDto> getTreatmentd() {
        return treatmentService.getAllTreatments();
    }

    @GetMapping("/assignment/{assignmentId}")
    public String initEditForm(@PathVariable("assignmentId") long id, Model model) {
        model.addAttribute("assignment", assignmentService.getAssignmentById(id));
        return VIEWS_ASSIGNMENT_FORM;
    }

    @PostMapping("/assignment/{assignmentId}")
    public String processEditForm(
            @ModelAttribute("patient") PatientDto patient,
            @ModelAttribute("assignment") AssignmentDto assignment,
            @PathVariable("assignmentId") long assignmentId,
            BindingResult result) {
        assignment.setId(assignmentId);
        validator.validate(assignment, result);
        if (result.hasErrors()) {
            return VIEWS_ASSIGNMENT_FORM;
        }
        assignmentService.updateAssignment(assignment);
        return "redirect:/patient/{patientId}/details";
    }

    @GetMapping("/assignment/{assignmentId}/cancel")
    public String initCancelAssignment(@PathVariable("assignmentId") long id, Model model) {
        clinicService.cancelAssignment(id);
        return "redirect:/patient/{patientId}/details";
    }

    @GetMapping("/assignment")
    public String initCreationForm(@ModelAttribute("patient") PatientDto patient, Model model) {
        model.addAttribute("assignment", assignmentService.getNewAssignmetnDto());
        return VIEWS_ASSIGNMENT_FORM;
    }

    @PostMapping("/assignment")
    public String processCreationForm(@PathVariable("patientId") int patientId,
                                      @ModelAttribute("assignment") @Valid AssignmentDto assignment,
                                      BindingResult result) {
        validator.validate(assignment, result);
        if (result.hasErrors()) {
            return VIEWS_ASSIGNMENT_FORM;
        }
        assignmentService.createAssignment(assignment, patientId);
        return "redirect:/patient/{patientId}/details";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

}
