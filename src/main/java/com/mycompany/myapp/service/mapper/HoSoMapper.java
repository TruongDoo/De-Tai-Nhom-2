package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.HoSo;
import com.mycompany.myapp.domain.PhuLuc;
import com.mycompany.myapp.domain.QuanLySuCoYKhoa;
import com.mycompany.myapp.service.dto.HoSoDTO;
import com.mycompany.myapp.service.dto.PhuLucDTO;
import com.mycompany.myapp.service.dto.QuanLySuCoYKhoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HoSo} and its DTO {@link HoSoDTO}.
 */
@Mapper(componentModel = "spring")
public interface HoSoMapper extends EntityMapper<HoSoDTO, HoSo> {
    @Mapping(target = "quanLySuCoYKhoa", source = "quanLySuCoYKhoa", qualifiedByName = "quanLySuCoYKhoaId")
    @Mapping(target = "phuLuc", source = "phuLuc", qualifiedByName = "phuLucId")
    HoSoDTO toDto(HoSo s);

    @Named("quanLySuCoYKhoaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QuanLySuCoYKhoaDTO toDtoQuanLySuCoYKhoaId(QuanLySuCoYKhoa quanLySuCoYKhoa);

    @Named("phuLucId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PhuLucDTO toDtoPhuLucId(PhuLuc phuLuc);
}
