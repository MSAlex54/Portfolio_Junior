package com.reha.mapper;

import com.reha.model.dto.TreatmentDto;
import com.reha.model.entity.Treatment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TreatmentMapper  extends AbstractMapper<Treatment, TreatmentDto>  {

    @Autowired
    public TreatmentMapper() {
        super(Treatment.class, TreatmentDto.class);
    }
}
