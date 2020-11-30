package com.reha.mapper;

import com.reha.model.dto.EventDto;
import com.reha.model.entity.Event;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EventMapper extends AbstractMapper<Event, EventDto> {

    public EventMapper() {
        super(Event.class, EventDto.class);
    }

    @Override
    public void mapSpecificFields(Event source, EventDto destination) {
        destination.setPatientName(Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getPatient().getName());
        destination.setTreatmentName(Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getTreatment().getName());
    }

}
