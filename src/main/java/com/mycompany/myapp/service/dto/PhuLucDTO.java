package com.mycompany.myapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PhuLuc} entity.
 */
public class PhuLucDTO implements Serializable {

    private Long id;

    private String soThuTu;

    private String tenBieuMau;

    @NotNull
    private String maHieu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoThuTu() {
        return soThuTu;
    }

    public void setSoThuTu(String soThuTu) {
        this.soThuTu = soThuTu;
    }

    public String getTenBieuMau() {
        return tenBieuMau;
    }

    public void setTenBieuMau(String tenBieuMau) {
        this.tenBieuMau = tenBieuMau;
    }

    public String getMaHieu() {
        return maHieu;
    }

    public void setMaHieu(String maHieu) {
        this.maHieu = maHieu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhuLucDTO)) {
            return false;
        }

        PhuLucDTO phuLucDTO = (PhuLucDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, phuLucDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhuLucDTO{" +
            "id=" + getId() +
            ", soThuTu='" + getSoThuTu() + "'" +
            ", tenBieuMau='" + getTenBieuMau() + "'" +
            ", maHieu='" + getMaHieu() + "'" +
            "}";
    }
}
