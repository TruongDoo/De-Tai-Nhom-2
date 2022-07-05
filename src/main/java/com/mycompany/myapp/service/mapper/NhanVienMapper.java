package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.NhanVien;
import com.mycompany.myapp.service.dto.NhanVienDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NhanVien} and its DTO {@link NhanVienDTO}.
 */
@Mapper(componentModel = "spring")
public interface NhanVienMapper extends EntityMapper<NhanVienDTO, NhanVien> {}
