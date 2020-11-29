package com.reha.controllers;

import com.reha.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TreatmentController {
    private static final String VIEWS_TREATMENT_CREATE_OR_UPDATE_FORM = "treatments/CreateUpdateTreatment";
    private final TreatmentService treatmentService;

    @Autowired
    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping("/t")
    public String getTreatments(Model model) {
        model.addAttribute("treatments", treatmentService.getAllTreatments());
        return VIEWS_TREATMENT_CREATE_OR_UPDATE_FORM;
    }

}
