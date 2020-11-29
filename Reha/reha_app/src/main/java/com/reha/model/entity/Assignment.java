package com.reha.model.entity;

import com.reha.model.enums.AssignmentStatuses;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "assignments")
public class Assignment extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "treatment_id", nullable = false)
    private Treatment treatment;

    @ToString.Exclude
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    private List<Event> events;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "finish_date")
    private Date finishDate;

    @Transient
    private String timePattern;

    @Column(name = "interval_size")
    private int intervalSize;

    @Column(name = "interval_type")
    private String intervalType;

    @Column(name = "days_of_week")
    private String daysOfWeek;

    @Column(name = "day_of_month")
    private int dayOfMonth;

    @Column(name = "moments")
    private String moments;

    @Column(name = "dose")
    private String dose;

    @Column(name = "status")
    private String status = AssignmentStatuses.ASSIGNED.getTitle();

    public String getTimePattern() {
        setTimePattern("Every " + getIntervalSize() + " " + getIntervalType());
        return timePattern;
    }
}
