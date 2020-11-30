package com.reha.mapper;

import com.reha.model.dto.DiagnosisDto;
import com.reha.model.entity.Diagnosis;
import org.springframework.stereotype.Component;

@Component
public class DiagnosisMapper extends AbstractMapper<Diagnosis, DiagnosisDto> {

    public DiagnosisMapper() {
        super(Diagnosis.class, DiagnosisDto.class);
    }
}
