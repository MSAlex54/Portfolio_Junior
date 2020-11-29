package com.reha.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDto extends AbstractDto {

    @ToString.Exclude
    private List<AssignmentDto> assignments;

    private String name;

    private boolean drug;

}
