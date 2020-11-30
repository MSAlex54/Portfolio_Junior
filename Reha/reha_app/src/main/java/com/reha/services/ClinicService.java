package com.reha.services;

import com.reha.dao.interfaces.AssignmentRepository;
import com.reha.jms.MessageSender;
import com.reha.model.dto.AssignmentDto;
import com.reha.model.dto.PatientDto;
import com.reha.model.entity.Assignment;
import com.reha.model.entity.Event;
import com.reha.model.enums.AssignmentStatuses;
import com.reha.model.enums.EventStatuses;
import com.reha.model.enums.IntervalTypes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class ClinicService {

    private static final Logger logger = Logger.getLogger(ClinicService.class);
    private final AssignmentRepository assignmentRepository;
    private final EventService eventService;
    private final PatientService patientService;
    private final UserService userService;

    @Autowired
    public ClinicService(AssignmentRepository assignmentRepository, EventService eventService, PatientService patientService, UserService userService) {
        this.assignmentRepository = assignmentRepository;
        this.eventService = eventService;
        this.patientService = patientService;
        this.userService = userService;
    }

    /**
     * Discharging patient and finishing all assignments
     *
     * @param id Patient id
     */
    public void dischargePatient(long id) {
        logger.info("Patient ID:" + id + " discharged by user: " + userService.getCurrentUser().getUsername());
        List<Assignment> assignments = assignmentRepository.getAllPatientsAssignment(id);
        if (Objects.nonNull(assignments)) {
            for (Assignment a : assignments) {
                cancelAssignment(a.getId());
            }
        }
        PatientDto patientDto = patientService.getPatientById(id);
        patientDto.setHealthy(true);
        patientService.updatePatient(patientDto);
    }

    public void updateEvents(AssignmentDto dto) {
        Assignment assignment = assignmentRepository.findById(dto.getId());
        cancelRestOfAssignmentEvents(assignment);
        generateEvents(assignment, LocalDate.now(), dto.getMoments(), dto.getDaysOfWeek());
    }

    /**
     * This method canceling assignment
     *
     * @param id - assignment id
     */
    public void cancelAssignment(long id) {
        logger.info("Assignment ID:" + id + " canceled by user " + userService.getCurrentUser().getUsername());
        Assignment assignment = assignmentRepository.findById(id);
        assignment.setFinishDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        assignment.setStatus(AssignmentStatuses.CANCELLED.getTitle());
        assignmentRepository.updateAssignment(assignment);
        cancelRestOfAssignmentEvents(assignment);
        MessageSender.sendMessage();
    }

    /**
     * This method cancels all assignments which still scheduled
     *
     * @param assignment
     */
    public void cancelRestOfAssignmentEvents(Assignment assignment) {
        if (assignment.getEvents() == null) {
            assignment.setEvents(eventService.getAllAssignmentEvents(assignment.getId()));
        }
        if (assignment.getEvents() != null) {
            for (Event event : assignment.getEvents()) {
                if (event.getStatus().equals(EventStatuses.SCHEDULED.getTitle())) {
                    event.setStatus(EventStatuses.CANCELLED.getTitle());
                    event.setNote("Assignment canceled");
                    event.setActive(false);
                    eventService.updateEvent(event);
                    logger.info("Event ID:" + event.getId() + " canceled by user " + userService.getCurrentUser().getUsername());
                }
            }
        }
    }

    /**
     * Auto generation of events by time pattern
     *
     * @param assignment what assignment contains this events
     * @param fromDate   start date
     * @param moments    time marks during the day
     * @param days       days of week
     */
    public List<Event> generateEvents(Assignment assignment, LocalDate fromDate, List<Time> moments, List<String> days) {
        List<Event> events = null;
        switch (IntervalTypes.valueOf(assignment.getIntervalType().toUpperCase())) {
            case DAY:
                events = generateDailyEvents(assignment, fromDate, moments);
                break;
            case WEEK:
                events = generateWeeklyEvents(assignment, fromDate, moments, days);
                break;
            case MONTH:
                events = generateMonthlyEvents(assignment, fromDate, moments);
                break;
            default:
        }
        eventService.saveEvents(events);
        boolean needSendUpdate = false;
        for (Event e : events) {
            if (e.getTimeStamp().toLocalDate().isEqual(LocalDate.now())) {
                needSendUpdate = true;
                break;
            }
        }
        if (needSendUpdate) MessageSender.sendMessage();
        return events;
    }

    /**
     * Generate monthly events
     *
     * @param assignment which contains this events
     * @param fromDate   start date
     * @param moments    list of time moments
     * @return generated events list
     */
    private List<Event> generateMonthlyEvents(Assignment assignment, LocalDate fromDate, List<Time> moments) {
        List<Event> eventList = new ArrayList<>();
        LocalDate cursorDate = fromDate;
        LocalDate tillDate = assignment.getFinishDate().toLocalDate();
        if (cursorDate.isAfter(tillDate)) {
            return eventList;
        }
        while (cursorDate.isBefore(tillDate.plusDays(1))) {
            if (cursorDate.getDayOfMonth() == assignment.getDayOfMonth() ||
                    (assignment.getDayOfMonth() > cursorDate.lengthOfMonth() && assignment.getDayOfMonth() > cursorDate.getDayOfMonth())
            ) {
                for (Time t : moments) {
                    eventList.add(eventService.generateEvent(assignment, LocalDateTime.of(cursorDate, t.toLocalTime())));
                }
                cursorDate = cursorDate.plusMonths(assignment.getIntervalSize());
            } else {
                cursorDate = cursorDate.plusDays(1);
            }
        }
        return eventList;
    }

    /**
     * Generate weekly events. Events wil be generate on select days of week
     *
     * @param assignment which contains this events
     * @param fromDate   start date
     * @param moments    list of time moments
     * @param days       list of days of week
     * @return generated events list
     */
    private List<Event> generateWeeklyEvents(Assignment assignment, LocalDate fromDate, List<Time> moments, List<String> days) {
        List<Event> eventList = new ArrayList<>();
        LocalDate cursorDate = fromDate;
        LocalDate tillDate = assignment.getFinishDate().toLocalDate();
        Set<String> daySet = new HashSet<>(days);
        if (cursorDate.isAfter(tillDate)) {
            return eventList;
        }
        boolean dayInCurrentWeek = false;
        LocalDate startOfWeek = cursorDate.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
        LocalDate endOfWeek = startOfWeek.plusWeeks(1);
        while (cursorDate.isBefore(tillDate.plusDays(1))) {
            if (cursorDate.isBefore(endOfWeek)) {
                if (daySet.contains(cursorDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()))) {
                    for (Time t : moments) {
                        eventList.add(eventService.generateEvent(assignment, LocalDateTime.of(cursorDate, t.toLocalTime())));
                    }
                    cursorDate = cursorDate.plusDays(1);
                    dayInCurrentWeek = true;
                } else {
                    cursorDate = cursorDate.plusDays(1);
                }
            } else if (dayInCurrentWeek) {
                startOfWeek = startOfWeek.plusWeeks(assignment.getIntervalSize());
                cursorDate = startOfWeek;
                dayInCurrentWeek = false;
            } else {
                startOfWeek = endOfWeek;
                endOfWeek = endOfWeek.plusWeeks(1);
                cursorDate.plusDays(1);
            }
        }
        return eventList;
    }

    /**
     * Generate daily events
     *
     * @param assignment which contains this events
     * @param fromDate   start date
     * @param moments    list of time moments
     * @return generated events list
     */
    private List<Event> generateDailyEvents(Assignment assignment, LocalDate fromDate, List<Time> moments) {
        List<Event> eventList = new ArrayList<>();
        LocalDate cursorDate = fromDate;
        LocalDate tillDate = assignment.getFinishDate().toLocalDate();
        if (cursorDate.isAfter(tillDate)) {
            return eventList;
        }
        while (cursorDate.isBefore(tillDate.plusDays(1))) {
            for (Time t : moments) {
                eventList.add(eventService.generateEvent(assignment, LocalDateTime.of(cursorDate, t.toLocalTime())));
            }
            cursorDate = cursorDate.plusDays(assignment.getIntervalSize());
        }
        return eventList;
    }


}
