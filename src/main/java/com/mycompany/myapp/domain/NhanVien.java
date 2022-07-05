package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NhanVien.
 */
@Entity
@Table(name = "nhan_vien")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NhanVien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chuc_vu")
    private String chucVu;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "email")
    private String email;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @OneToMany(mappedBy = "quanLySuCoYKhoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "thongTinSuCo", "thongTinNguoiBenh", "quanLySuCoYKhoa", "hoSos" }, allowSetters = true)
    private Set<QuanLySuCoYKhoa> quanLySuCoYKhoas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NhanVien id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChucVu() {
        return this.chucVu;
    }

    public NhanVien chucVu(String chucVu) {
        this.setChucVu(chucVu);
        return this;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getHoTen() {
        return this.hoTen;
    }

    public NhanVien hoTen(String hoTen) {
        this.setHoTen(hoTen);
        return this;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return this.email;
    }

    public NhanVien email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return this.diaChi;
    }

    public NhanVien diaChi(String diaChi) {
        this.setDiaChi(diaChi);
        return this;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return this.soDienThoai;
    }

    public NhanVien soDienThoai(String soDienThoai) {
        this.setSoDienThoai(soDienThoai);
        return this;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public Set<QuanLySuCoYKhoa> getQuanLySuCoYKhoas() {
        return this.quanLySuCoYKhoas;
    }

    public void setQuanLySuCoYKhoas(Set<QuanLySuCoYKhoa> quanLySuCoYKhoas) {
        if (this.quanLySuCoYKhoas != null) {
            this.quanLySuCoYKhoas.forEach(i -> i.setQuanLySuCoYKhoa(null));
        }
        if (quanLySuCoYKhoas != null) {
            quanLySuCoYKhoas.forEach(i -> i.setQuanLySuCoYKhoa(this));
        }
        this.quanLySuCoYKhoas = quanLySuCoYKhoas;
    }

    public NhanVien quanLySuCoYKhoas(Set<QuanLySuCoYKhoa> quanLySuCoYKhoas) {
        this.setQuanLySuCoYKhoas(quanLySuCoYKhoas);
        return this;
    }

    public NhanVien addQuanLySuCoYKhoa(QuanLySuCoYKhoa quanLySuCoYKhoa) {
        this.quanLySuCoYKhoas.add(quanLySuCoYKhoa);
        quanLySuCoYKhoa.setQuanLySuCoYKhoa(this);
        return this;
    }

    public NhanVien removeQuanLySuCoYKhoa(QuanLySuCoYKhoa quanLySuCoYKhoa) {
        this.quanLySuCoYKhoas.remove(quanLySuCoYKhoa);
        quanLySuCoYKhoa.setQuanLySuCoYKhoa(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhanVien)) {
            return false;
        }
        return id != null && id.equals(((NhanVien) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhanVien{" +
            "id=" + getId() +
            ", chucVu='" + getChucVu() + "'" +
            ", hoTen='" + getHoTen() + "'" +
            ", email='" + getEmail() + "'" +
            ", diaChi='" + getDiaChi() + "'" +
            ", soDienThoai='" + getSoDienThoai() + "'" +
            "}";
    }
}
