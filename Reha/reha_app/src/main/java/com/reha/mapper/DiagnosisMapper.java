package com.reha.mapper;

import com.reha.model.dto.DiagnosisDto;
import com.reha.model.entity.Diagnosis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiagnosisMapper extends AbstractMapper<Diagnosis, DiagnosisDto> {

    @Autowired
    public DiagnosisMapper() {
        super(Diagnosis.class, DiagnosisDto.class);
    }
}
