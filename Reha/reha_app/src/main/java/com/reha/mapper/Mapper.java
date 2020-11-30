package com.reha.mapper;

import com.reha.model.dto.AbstractDto;
import com.reha.model.entity.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);

    D toDto(E entity);

}
