package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "thong_tin_nguoi_benh")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ThongTinNguoiBenh implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ho_va_ten")
    private String hoVaTen;

    @Column(name = "so_benh_an")
    private String soBenhAn;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @OneToMany(mappedBy = "thongTinNguoiBenh")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "thongTinSuCo", "thongTinNguoiBenh", "quanLySuCoYKhoa", "hoSos" }, allowSetters = true)
    private Set<QuanLySuCoYKhoa> quanLySuCoYKhoas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ThongTinNguoiBenh id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHoVaTen() {
        return this.hoVaTen;
    }

    public ThongTinNguoiBenh hoVaTen(String hoVaTen) {
        this.setHoVaTen(hoVaTen);
        return this;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getSoBenhAn() {
        return this.soBenhAn;
    }

    public ThongTinNguoiBenh soBenhAn(String soBenhAn) {
        this.setSoBenhAn(soBenhAn);
        return this;
    }

    public void setSoBenhAn(String soBenhAn) {
        this.soBenhAn = soBenhAn;
    }

    public Boolean getGioiTinh() {
        return this.gioiTinh;
    }

    public ThongTinNguoiBenh gioiTinh(Boolean gioiTinh) {
        this.setGioiTinh(gioiTinh);
        return this;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Set<QuanLySuCoYKhoa> getQuanLySuCoYKhoas() {
        return this.quanLySuCoYKhoas;
    }

    public void setQuanLySuCoYKhoas(Set<QuanLySuCoYKhoa> quanLySuCoYKhoas) {
        if (this.quanLySuCoYKhoas != null) {
            this.quanLySuCoYKhoas.forEach(i -> i.setThongTinNguoiBenh(null));
        }
        if (quanLySuCoYKhoas != null) {
            quanLySuCoYKhoas.forEach(i -> i.setThongTinNguoiBenh(this));
        }
        this.quanLySuCoYKhoas = quanLySuCoYKhoas;
    }

    public ThongTinNguoiBenh quanLySuCoYKhoas(Set<QuanLySuCoYKhoa> quanLySuCoYKhoas) {
        this.setQuanLySuCoYKhoas(quanLySuCoYKhoas);
        return this;
    }

    public ThongTinNguoiBenh addQuanLySuCoYKhoa(QuanLySuCoYKhoa quanLySuCoYKhoa) {
        this.quanLySuCoYKhoas.add(quanLySuCoYKhoa);
        quanLySuCoYKhoa.setThongTinNguoiBenh(this);
        return this;
    }

    public ThongTinNguoiBenh removeQuanLySuCoYKhoa(QuanLySuCoYKhoa quanLySuCoYKhoa) {
        this.quanLySuCoYKhoas.remove(quanLySuCoYKhoa);
        quanLySuCoYKhoa.setThongTinNguoiBenh(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThongTinNguoiBenh)) {
            return false;
        }
        return id != null && id.equals(((ThongTinNguoiBenh) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThongTinNguoiBenh{" +
            "id=" + getId() +
            ", hoVaTen='" + getHoVaTen() + "'" +
            ", soBenhAn='" + getSoBenhAn() + "'" +
            ", gioiTinh='" + getGioiTinh() + "'" +
            "}";
    }
}
