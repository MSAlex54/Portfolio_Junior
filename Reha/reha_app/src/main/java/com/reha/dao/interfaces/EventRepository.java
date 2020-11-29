package com.reha.dao.interfaces;

import com.reha.model.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository {
    Event findById(long id);

    Event createEvent(Event event);

    void saveEvents(List<Event> events);

    Event updateEvent(Event event);

    void deleteEvent(Event event);

    List<Event> getAllEvents();

    List<Event> getAllAssignmentEvents(long assignmentId);

    List<Event> getAllPatientsEvents(long patientId);

    List<Event> getEventBetweenTime(LocalDateTime startTime, LocalDateTime finishTime);

}
