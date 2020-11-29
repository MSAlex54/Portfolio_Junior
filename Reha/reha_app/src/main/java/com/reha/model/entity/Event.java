package com.reha.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "events")
public class Event extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "date_time")
    private LocalDateTime timeStamp;

    @ManyToOne
    @JoinColumn(name = "treatment_id", nullable = false)
    private Treatment treatment;

    @Column(name = "dose")
    private String dose;

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    @Column(name = "active")
    private boolean active;

    public String getNormalTime() {
        return getTimeStamp().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
}
