package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.PhuLuc;
import com.mycompany.myapp.service.dto.PhuLucDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PhuLuc} and its DTO {@link PhuLucDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhuLucMapper extends EntityMapper<PhuLucDTO, PhuLuc> {}
