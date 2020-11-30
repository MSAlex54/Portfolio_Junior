package com.reha.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reha.model.enums.AssignmentStatuses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDto extends AbstractDto {

    private long patientId;

    private long treatmentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date finishDate;

    private int intervalSize;

    private String intervalType;

    private List<String> daysOfWeek;

    private int dayOfMonth;

    private List<Time> moments;

    private String dose;

    private String status = AssignmentStatuses.ASSIGNED.getTitle();

    public void setMoments(List<String> moments) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        ArrayList<Time> mom = new ArrayList<>();
        for (String s : moments) {
            if (Objects.nonNull(s)) {
                try {
                    mom.add(new Time(formatter.parse(s).getTime()));
                } catch (ParseException e) {

                }
            }
        }
        this.moments = mom;
    }

}
