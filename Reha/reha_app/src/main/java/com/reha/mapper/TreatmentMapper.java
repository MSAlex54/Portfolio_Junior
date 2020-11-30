package com.reha.mapper;

import com.reha.model.dto.TreatmentDto;
import com.reha.model.entity.Treatment;
import org.springframework.stereotype.Component;

@Component
public class TreatmentMapper extends AbstractMapper<Treatment, TreatmentDto> {

    public TreatmentMapper() {
        super(Treatment.class, TreatmentDto.class);
    }
}
