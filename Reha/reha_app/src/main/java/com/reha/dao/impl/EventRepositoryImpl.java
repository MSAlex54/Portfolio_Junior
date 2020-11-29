package com.reha.dao.impl;

import com.reha.dao.interfaces.EventRepository;
import com.reha.model.entity.Event;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class EventRepositoryImpl implements EventRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Event findById(long id) {
        return em.find(Event.class, id);
    }

    @Override
    public Event createEvent(Event event) {
        em.persist(event);
        return event;
    }

    @Override
    public void saveEvents(List<Event> events) {
        for (Event e : events) {
            em.persist(e);
        }
    }

    @Override
    public Event updateEvent(Event event) {
        return em.merge(event);
    }

    @Override
    public void deleteEvent(Event event) {
        em.remove(event);
    }

    @Override
    public List<Event> getAllEvents() {
        Query query = em.createQuery("SELECT e FROM events e ORDER BY e.timeStamp");
        return query.getResultList();
    }

    @Override
    public List<Event> getAllAssignmentEvents(long assignmentId) {
        Query query = em.createQuery("SELECT e FROM events e WHERE e.assignment.id = " + assignmentId + "  ORDER BY e.timeStamp");
        return query.getResultList();
    }

    @Override
    public List<Event> getAllPatientsEvents(long patientId) {
        Query query = em.createQuery("SELECT e FROM events e WHERE e.patient.id = " + patientId + "  ORDER BY e.timeStamp");
        return query.getResultList();
    }

    @Override
    public List<Event> getEventBetweenTime(LocalDateTime startTime, LocalDateTime finishTime) {
        Query query = em.createQuery("SELECT e FROM events e WHERE e.timeStamp BETWEEN :start AND :finish ORDER BY e.timeStamp");
        query.setParameter("start", startTime);
        query.setParameter("finish", finishTime);
        return query.getResultList();
    }

}
