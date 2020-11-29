package com.reha.controllers;

import com.reha.model.dto.EventDto;
import com.reha.model.dto.PatientDto;
import com.reha.model.enums.EventStatuses;
import com.reha.services.EventService;
import com.reha.services.PatientService;
import com.reha.utils.validators.EventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EventController {

    private static final String VIEWS_EVENT_FORM = "events/EventForm";
    private static final String VIEWS_EVENT_LIST = "events/EventsList";
    private final EventService eventService;
    private final PatientService patientService;
    private final EventValidator validator;

    @Autowired
    public EventController(EventService eventService, PatientService patientService, EventValidator validator) {
        this.eventService = eventService;
        this.patientService = patientService;
        this.validator = validator;
    }

    @ModelAttribute("patients")
    public List<PatientDto> getPatientsList() {
        return patientService.getAll();
    }

    @ModelAttribute("statuses")
    public EventStatuses[] getEventsList() {
        return EventStatuses.values();
    }

    @GetMapping("/event/list")
    public String getAllEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return VIEWS_EVENT_LIST;
    }

    @GetMapping("/event/hour")
    public String getNearestEvents(ModelMap model) {
        model.addAttribute("events", eventService.getNearestEvents());
        return VIEWS_EVENT_LIST;
    }

    @GetMapping("/event/day")
    public String getTodaysEvents(ModelMap model) {
        model.addAttribute("events", eventService.getTodaysEvents());
        return VIEWS_EVENT_LIST;
    }

    @GetMapping("/event/p/{patientId}")
    public String getPatientEvents(@PathVariable("patientId") long patientId, ModelMap model) {
        model.addAttribute("events", eventService.getAllPatientsEvents(patientId));
        return VIEWS_EVENT_LIST;
    }

    @GetMapping("/event/{eventId}")
    public String initEditForm(@PathVariable("eventId") long eventId,
                               Model model) {
        EventDto event = eventService.getEventById(eventId);
        model.addAttribute("event", event);
        model.addAttribute("statuses", EventStatuses.values());
        return VIEWS_EVENT_FORM;
    }

    @PostMapping("/event/{eventId}")
    public String processEditForm(@ModelAttribute("event") @Valid EventDto event,
                                  @PathVariable("eventId") long eventId,
                                  BindingResult result
    ) {
        event.setId(eventId);
        EventDto originalEvent = eventService.getEventById(eventId);
        event.setPatientName(originalEvent.getPatientName());
        event.setTimeStamp(originalEvent.getTimeStamp());
        event.setTreatmentName(originalEvent.getTreatmentName());
        event.setDose(originalEvent.getDose());
        event.setActive(originalEvent.isActive());
        validator.validate(event, result);
        if (result.hasErrors()) {
            return VIEWS_EVENT_FORM;
        }
        eventService.updateEvent(event);
        return "redirect:/event/list";
    }
}
