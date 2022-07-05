package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ThongTinSuCo;
import com.mycompany.myapp.service.dto.ThongTinSuCoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ThongTinSuCo} and its DTO {@link ThongTinSuCoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ThongTinSuCoMapper extends EntityMapper<ThongTinSuCoDTO, ThongTinSuCo> {}
