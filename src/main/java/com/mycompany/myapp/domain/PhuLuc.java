package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PhuLuc.
 */
@Entity
@Table(name = "phu_luc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PhuLuc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "so_thu_tu")
    private String soThuTu;

    @Column(name = "ten_bieu_mau")
    private String tenBieuMau;

    @NotNull
    @Column(name = "ma_hieu", nullable = false)
    private String maHieu;

    /**
     * A relationship
     */
    @OneToMany(mappedBy = "phuLuc")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "quanLySuCoYKhoa", "phuLuc" }, allowSetters = true)
    private Set<HoSo> hoSos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PhuLuc id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoThuTu() {
        return this.soThuTu;
    }

    public PhuLuc soThuTu(String soThuTu) {
        this.setSoThuTu(soThuTu);
        return this;
    }

    public void setSoThuTu(String soThuTu) {
        this.soThuTu = soThuTu;
    }

    public String getTenBieuMau() {
        return this.tenBieuMau;
    }

    public PhuLuc tenBieuMau(String tenBieuMau) {
        this.setTenBieuMau(tenBieuMau);
        return this;
    }

    public void setTenBieuMau(String tenBieuMau) {
        this.tenBieuMau = tenBieuMau;
    }

    public String getMaHieu() {
        return this.maHieu;
    }

    public PhuLuc maHieu(String maHieu) {
        this.setMaHieu(maHieu);
        return this;
    }

    public void setMaHieu(String maHieu) {
        this.maHieu = maHieu;
    }

    public Set<HoSo> getHoSos() {
        return this.hoSos;
    }

    public void setHoSos(Set<HoSo> hoSos) {
        if (this.hoSos != null) {
            this.hoSos.forEach(i -> i.setPhuLuc(null));
        }
        if (hoSos != null) {
            hoSos.forEach(i -> i.setPhuLuc(this));
        }
        this.hoSos = hoSos;
    }

    public PhuLuc hoSos(Set<HoSo> hoSos) {
        this.setHoSos(hoSos);
        return this;
    }

    public PhuLuc addHoSo(HoSo hoSo) {
        this.hoSos.add(hoSo);
        hoSo.setPhuLuc(this);
        return this;
    }

    public PhuLuc removeHoSo(HoSo hoSo) {
        this.hoSos.remove(hoSo);
        hoSo.setPhuLuc(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhuLuc)) {
            return false;
        }
        return id != null && id.equals(((PhuLuc) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhuLuc{" +
            "id=" + getId() +
            ", soThuTu='" + getSoThuTu() + "'" +
            ", tenBieuMau='" + getTenBieuMau() + "'" +
            ", maHieu='" + getMaHieu() + "'" +
            "}";
    }
}
