package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ThongTinNguoiBenh;
import com.mycompany.myapp.service.dto.ThongTinNguoiBenhDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ThongTinNguoiBenh} and its DTO {@link ThongTinNguoiBenhDTO}.
 */
@Mapper(componentModel = "spring")
public interface ThongTinNguoiBenhMapper extends EntityMapper<ThongTinNguoiBenhDTO, ThongTinNguoiBenh> {}
