package com.reha.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "treatments")
public class Treatment extends AbstractEntity {

    @ToString.Exclude
    @OneToMany(mappedBy = "treatment")
    private List<Assignment> assignments;

    @Column(name = "name")
    private String name;

    @Column(name = "is_drug")
    private boolean drug;

}
