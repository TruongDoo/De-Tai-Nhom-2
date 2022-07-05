package com.mycompany.myapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ThongTinNguoiBenh} entity.
 */
@Schema(description = "not an ignored comment")
public class ThongTinNguoiBenhDTO implements Serializable {

    private Long id;

    private String hoVaTen;

    private String soBenhAn;

    private Boolean gioiTinh;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getSoBenhAn() {
        return soBenhAn;
    }

    public void setSoBenhAn(String soBenhAn) {
        this.soBenhAn = soBenhAn;
    }

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThongTinNguoiBenhDTO)) {
            return false;
        }

        ThongTinNguoiBenhDTO thongTinNguoiBenhDTO = (ThongTinNguoiBenhDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, thongTinNguoiBenhDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThongTinNguoiBenhDTO{" +
            "id=" + getId() +
            ", hoVaTen='" + getHoVaTen() + "'" +
            ", soBenhAn='" + getSoBenhAn() + "'" +
            ", gioiTinh='" + getGioiTinh() + "'" +
            "}";
    }
}
