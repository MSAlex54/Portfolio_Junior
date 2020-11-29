package com.reha.services;

import com.reha.dao.interfaces.EventRepository;
import com.reha.jms.MessageSender;
import com.reha.mapper.EventMapper;
import com.reha.model.dto.EventDto;
import com.reha.model.entity.Assignment;
import com.reha.model.entity.Event;
import com.reha.model.enums.EventStatuses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private static final Logger logger = Logger.getLogger(EventService.class);
    private final EventRepository repository;
    private final EventMapper mapper;
    private final UserService userService;

    @Autowired
    public EventService(EventRepository repository, EventMapper mapper, UserService userService) {
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
    }

    public Event updateEvent(EventDto eventDto) {
        logger.info("Event ID:" + eventDto.getId() + " updated by user " + userService.getCurrentUser().getUsername());
        Event event = repository.findById(eventDto.getId());
        event.setStatus(eventDto.getStatus());
        event.setNote(eventDto.getNote());
        event.setActive(false);
        if (event.getTimeStamp().toLocalDate().isEqual(LocalDate.now())) {
            MessageSender.sendMessage();
        }
        return repository.updateEvent(event);
    }

    public EventDto getEventById(long eventId) {
        return mapper.toDto(repository.findById(eventId));
    }

    public List<Event> getAllEvents() {
        return repository.getAllEvents();
    }

    public List<Event> getAllAssignmentEvents(long assignmentId) {
        return repository.getAllAssignmentEvents(assignmentId);
    }

    public List<Event> getAllPatientsEvents(long patientId) {
        return repository.getAllPatientsEvents(patientId);
    }

    public List<Event> getEventBetweenTime(LocalDateTime startTime, LocalDateTime finishTime) {
        return repository.getEventBetweenTime(startTime, finishTime);
    }

    public List<Event> getNearestEvents() {
        return repository.getEventBetweenTime(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
    }

    public List<Event> getTodaysEvents() {
        return repository.getEventBetweenTime(LocalDateTime.now().toLocalDate().atStartOfDay(), LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay());
    }

    public List<EventDto> getForBoard() {
        return getTodaysEvents().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Event generateEvent(Assignment assignment, LocalDateTime timeStamp) {
        return Event.builder()
                .assignment(assignment)
                .patient(assignment.getPatient())
                .timeStamp(timeStamp)
                .treatment(assignment.getTreatment())
                .dose(assignment.getDose())
                .status(EventStatuses.SCHEDULED.getTitle())
                .active(true)
                .build();
    }

    public void saveEvents(List<Event> events) {
        repository.saveEvents(events);
    }

    public Event updateEvent(Event event) {
        return repository.updateEvent(event);
    }
}
