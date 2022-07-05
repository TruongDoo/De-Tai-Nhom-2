package com.mycompany.myapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.HoSo} entity.
 */
@Schema(description = "The Employee entity.")
public class HoSoDTO implements Serializable {

    private Long id;

    /**
     * The firstname attribute.
     */
    @Schema(description = "The firstname attribute.")
    private String soThuTu;

    private String tenHoSo;

    private String noiLuu;

    private Instant thoiGianLuu;

    private QuanLySuCoYKhoaDTO quanLySuCoYKhoa;

    private PhuLucDTO phuLuc;

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

    public String getTenHoSo() {
        return tenHoSo;
    }

    public void setTenHoSo(String tenHoSo) {
        this.tenHoSo = tenHoSo;
    }

    public String getNoiLuu() {
        return noiLuu;
    }

    public void setNoiLuu(String noiLuu) {
        this.noiLuu = noiLuu;
    }

    public Instant getThoiGianLuu() {
        return thoiGianLuu;
    }

    public void setThoiGianLuu(Instant thoiGianLuu) {
        this.thoiGianLuu = thoiGianLuu;
    }

    public QuanLySuCoYKhoaDTO getQuanLySuCoYKhoa() {
        return quanLySuCoYKhoa;
    }

    public void setQuanLySuCoYKhoa(QuanLySuCoYKhoaDTO quanLySuCoYKhoa) {
        this.quanLySuCoYKhoa = quanLySuCoYKhoa;
    }

    public PhuLucDTO getPhuLuc() {
        return phuLuc;
    }

    public void setPhuLuc(PhuLucDTO phuLuc) {
        this.phuLuc = phuLuc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoSoDTO)) {
            return false;
        }

        HoSoDTO hoSoDTO = (HoSoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hoSoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoSoDTO{" +
            "id=" + getId() +
            ", soThuTu='" + getSoThuTu() + "'" +
            ", tenHoSo='" + getTenHoSo() + "'" +
            ", noiLuu='" + getNoiLuu() + "'" +
            ", thoiGianLuu='" + getThoiGianLuu() + "'" +
            ", quanLySuCoYKhoa=" + getQuanLySuCoYKhoa() +
            ", phuLuc=" + getPhuLuc() +
            "}";
    }
}
