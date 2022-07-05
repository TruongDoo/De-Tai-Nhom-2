package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.NhanVien;
import com.mycompany.myapp.domain.QuanLySuCoYKhoa;
import com.mycompany.myapp.domain.ThongTinNguoiBenh;
import com.mycompany.myapp.domain.ThongTinSuCo;
import com.mycompany.myapp.service.dto.NhanVienDTO;
import com.mycompany.myapp.service.dto.QuanLySuCoYKhoaDTO;
import com.mycompany.myapp.service.dto.ThongTinNguoiBenhDTO;
import com.mycompany.myapp.service.dto.ThongTinSuCoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QuanLySuCoYKhoa} and its DTO {@link QuanLySuCoYKhoaDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuanLySuCoYKhoaMapper extends EntityMapper<QuanLySuCoYKhoaDTO, QuanLySuCoYKhoa> {
    @Mapping(target = "thongTinSuCo", source = "thongTinSuCo", qualifiedByName = "thongTinSuCoId")
    @Mapping(target = "thongTinNguoiBenh", source = "thongTinNguoiBenh", qualifiedByName = "thongTinNguoiBenhId")
    @Mapping(target = "quanLySuCoYKhoa", source = "quanLySuCoYKhoa", qualifiedByName = "nhanVienId")
    QuanLySuCoYKhoaDTO toDto(QuanLySuCoYKhoa s);

    @Named("thongTinSuCoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ThongTinSuCoDTO toDtoThongTinSuCoId(ThongTinSuCo thongTinSuCo);

    @Named("thongTinNguoiBenhId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ThongTinNguoiBenhDTO toDtoThongTinNguoiBenhId(ThongTinNguoiBenh thongTinNguoiBenh);

    @Named("nhanVienId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NhanVienDTO toDtoNhanVienId(NhanVien nhanVien);
}
