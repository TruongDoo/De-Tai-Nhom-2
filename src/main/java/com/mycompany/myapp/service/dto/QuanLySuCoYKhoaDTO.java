package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.QuanLySuCoYKhoa} entity.
 */
public class QuanLySuCoYKhoaDTO implements Serializable {

    private Long id;

    @NotNull
    private String maSoSuCo;

    private Instant ngayBaoCao;

    private String donViBaoCao;

    private String soThuTu;

    private String tenSuCo;

    private String nhomSuCo;

    private String mucDoSuCo;

    private ThongTinSuCoDTO thongTinSuCo;

    private ThongTinNguoiBenhDTO thongTinNguoiBenh;

    private NhanVienDTO quanLySuCoYKhoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaSoSuCo() {
        return maSoSuCo;
    }

    public void setMaSoSuCo(String maSoSuCo) {
        this.maSoSuCo = maSoSuCo;
    }

    public Instant getNgayBaoCao() {
        return ngayBaoCao;
    }

    public void setNgayBaoCao(Instant ngayBaoCao) {
        this.ngayBaoCao = ngayBaoCao;
    }

    public String getDonViBaoCao() {
        return donViBaoCao;
    }

    public void setDonViBaoCao(String donViBaoCao) {
        this.donViBaoCao = donViBaoCao;
    }

    public String getSoThuTu() {
        return soThuTu;
    }

    public void setSoThuTu(String soThuTu) {
        this.soThuTu = soThuTu;
    }

    public String getTenSuCo() {
        return tenSuCo;
    }

    public void setTenSuCo(String tenSuCo) {
        this.tenSuCo = tenSuCo;
    }

    public String getNhomSuCo() {
        return nhomSuCo;
    }

    public void setNhomSuCo(String nhomSuCo) {
        this.nhomSuCo = nhomSuCo;
    }

    public String getMucDoSuCo() {
        return mucDoSuCo;
    }

    public void setMucDoSuCo(String mucDoSuCo) {
        this.mucDoSuCo = mucDoSuCo;
    }

    public ThongTinSuCoDTO getThongTinSuCo() {
        return thongTinSuCo;
    }

    public void setThongTinSuCo(ThongTinSuCoDTO thongTinSuCo) {
        this.thongTinSuCo = thongTinSuCo;
    }

    public ThongTinNguoiBenhDTO getThongTinNguoiBenh() {
        return thongTinNguoiBenh;
    }

    public void setThongTinNguoiBenh(ThongTinNguoiBenhDTO thongTinNguoiBenh) {
        this.thongTinNguoiBenh = thongTinNguoiBenh;
    }

    public NhanVienDTO getQuanLySuCoYKhoa() {
        return quanLySuCoYKhoa;
    }

    public void setQuanLySuCoYKhoa(NhanVienDTO quanLySuCoYKhoa) {
        this.quanLySuCoYKhoa = quanLySuCoYKhoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuanLySuCoYKhoaDTO)) {
            return false;
        }

        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO = (QuanLySuCoYKhoaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, quanLySuCoYKhoaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuanLySuCoYKhoaDTO{" +
            "id=" + getId() +
            ", maSoSuCo='" + getMaSoSuCo() + "'" +
            ", ngayBaoCao='" + getNgayBaoCao() + "'" +
            ", donViBaoCao='" + getDonViBaoCao() + "'" +
            ", soThuTu='" + getSoThuTu() + "'" +
            ", tenSuCo='" + getTenSuCo() + "'" +
            ", nhomSuCo='" + getNhomSuCo() + "'" +
            ", mucDoSuCo='" + getMucDoSuCo() + "'" +
            ", thongTinSuCo=" + getThongTinSuCo() +
            ", thongTinNguoiBenh=" + getThongTinNguoiBenh() +
            ", quanLySuCoYKhoa=" + getQuanLySuCoYKhoa() +
            "}";
    }
}
