package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.NhanVien} entity.
 */
public class NhanVienDTO implements Serializable {

    private Long id;

    private String chucVu;

    private String hoTen;

    private String email;

    private String diaChi;

    private String soDienThoai;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhanVienDTO)) {
            return false;
        }

        NhanVienDTO nhanVienDTO = (NhanVienDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nhanVienDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhanVienDTO{" +
            "id=" + getId() +
            ", chucVu='" + getChucVu() + "'" +
            ", hoTen='" + getHoTen() + "'" +
            ", email='" + getEmail() + "'" +
            ", diaChi='" + getDiaChi() + "'" +
            ", soDienThoai='" + getSoDienThoai() + "'" +
            "}";
    }
}
