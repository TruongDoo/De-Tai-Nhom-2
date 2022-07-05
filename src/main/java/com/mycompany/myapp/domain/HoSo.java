package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Employee entity.
 */
@Entity
@Table(name = "ho_so")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HoSo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The firstname attribute.
     */
    @Column(name = "so_thu_tu")
    private String soThuTu;

    @Column(name = "ten_ho_so")
    private String tenHoSo;

    @Column(name = "noi_luu")
    private String noiLuu;

    @Column(name = "thoi_gian_luu")
    private Instant thoiGianLuu;

    @ManyToOne
    @JsonIgnoreProperties(value = { "thongTinSuCo", "thongTinNguoiBenh", "quanLySuCoYKhoa", "hoSos" }, allowSetters = true)
    private QuanLySuCoYKhoa quanLySuCoYKhoa;

    /**
     * Another side of the same relationship
     */
    @ManyToOne
    @JsonIgnoreProperties(value = { "hoSos" }, allowSetters = true)
    private PhuLuc phuLuc;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HoSo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoThuTu() {
        return this.soThuTu;
    }

    public HoSo soThuTu(String soThuTu) {
        this.setSoThuTu(soThuTu);
        return this;
    }

    public void setSoThuTu(String soThuTu) {
        this.soThuTu = soThuTu;
    }

    public String getTenHoSo() {
        return this.tenHoSo;
    }

    public HoSo tenHoSo(String tenHoSo) {
        this.setTenHoSo(tenHoSo);
        return this;
    }

    public void setTenHoSo(String tenHoSo) {
        this.tenHoSo = tenHoSo;
    }

    public String getNoiLuu() {
        return this.noiLuu;
    }

    public HoSo noiLuu(String noiLuu) {
        this.setNoiLuu(noiLuu);
        return this;
    }

    public void setNoiLuu(String noiLuu) {
        this.noiLuu = noiLuu;
    }

    public Instant getThoiGianLuu() {
        return this.thoiGianLuu;
    }

    public HoSo thoiGianLuu(Instant thoiGianLuu) {
        this.setThoiGianLuu(thoiGianLuu);
        return this;
    }

    public void setThoiGianLuu(Instant thoiGianLuu) {
        this.thoiGianLuu = thoiGianLuu;
    }

    public QuanLySuCoYKhoa getQuanLySuCoYKhoa() {
        return this.quanLySuCoYKhoa;
    }

    public void setQuanLySuCoYKhoa(QuanLySuCoYKhoa quanLySuCoYKhoa) {
        this.quanLySuCoYKhoa = quanLySuCoYKhoa;
    }

    public HoSo quanLySuCoYKhoa(QuanLySuCoYKhoa quanLySuCoYKhoa) {
        this.setQuanLySuCoYKhoa(quanLySuCoYKhoa);
        return this;
    }

    public PhuLuc getPhuLuc() {
        return this.phuLuc;
    }

    public void setPhuLuc(PhuLuc phuLuc) {
        this.phuLuc = phuLuc;
    }

    public HoSo phuLuc(PhuLuc phuLuc) {
        this.setPhuLuc(phuLuc);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoSo)) {
            return false;
        }
        return id != null && id.equals(((HoSo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoSo{" +
            "id=" + getId() +
            ", soThuTu='" + getSoThuTu() + "'" +
            ", tenHoSo='" + getTenHoSo() + "'" +
            ", noiLuu='" + getNoiLuu() + "'" +
            ", thoiGianLuu='" + getThoiGianLuu() + "'" +
            "}";
    }
}
