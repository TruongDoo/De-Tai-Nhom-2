package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ThongTinSuCo.
 */
@Entity
@Table(name = "thong_tin_su_co")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ThongTinSuCo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "vi_tri_xay_ra_su_co")
    private String viTriXayRaSuCo;

    @Column(name = "ngay_xay_ra_su_co")
    private Instant ngayXayRaSuCo;

    @Column(name = "thoi_gian")
    private Instant thoiGian;

    @OneToMany(mappedBy = "thongTinSuCo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "thongTinSuCo", "thongTinNguoiBenh", "quanLySuCoYKhoa", "hoSos" }, allowSetters = true)
    private Set<QuanLySuCoYKhoa> quanLySuCoYKhoas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ThongTinSuCo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getViTriXayRaSuCo() {
        return this.viTriXayRaSuCo;
    }

    public ThongTinSuCo viTriXayRaSuCo(String viTriXayRaSuCo) {
        this.setViTriXayRaSuCo(viTriXayRaSuCo);
        return this;
    }

    public void setViTriXayRaSuCo(String viTriXayRaSuCo) {
        this.viTriXayRaSuCo = viTriXayRaSuCo;
    }

    public Instant getNgayXayRaSuCo() {
        return this.ngayXayRaSuCo;
    }

    public ThongTinSuCo ngayXayRaSuCo(Instant ngayXayRaSuCo) {
        this.setNgayXayRaSuCo(ngayXayRaSuCo);
        return this;
    }

    public void setNgayXayRaSuCo(Instant ngayXayRaSuCo) {
        this.ngayXayRaSuCo = ngayXayRaSuCo;
    }

    public Instant getThoiGian() {
        return this.thoiGian;
    }

    public ThongTinSuCo thoiGian(Instant thoiGian) {
        this.setThoiGian(thoiGian);
        return this;
    }

    public void setThoiGian(Instant thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Set<QuanLySuCoYKhoa> getQuanLySuCoYKhoas() {
        return this.quanLySuCoYKhoas;
    }

    public void setQuanLySuCoYKhoas(Set<QuanLySuCoYKhoa> quanLySuCoYKhoas) {
        if (this.quanLySuCoYKhoas != null) {
            this.quanLySuCoYKhoas.forEach(i -> i.setThongTinSuCo(null));
        }
        if (quanLySuCoYKhoas != null) {
            quanLySuCoYKhoas.forEach(i -> i.setThongTinSuCo(this));
        }
        this.quanLySuCoYKhoas = quanLySuCoYKhoas;
    }

    public ThongTinSuCo quanLySuCoYKhoas(Set<QuanLySuCoYKhoa> quanLySuCoYKhoas) {
        this.setQuanLySuCoYKhoas(quanLySuCoYKhoas);
        return this;
    }

    public ThongTinSuCo addQuanLySuCoYKhoa(QuanLySuCoYKhoa quanLySuCoYKhoa) {
        this.quanLySuCoYKhoas.add(quanLySuCoYKhoa);
        quanLySuCoYKhoa.setThongTinSuCo(this);
        return this;
    }

    public ThongTinSuCo removeQuanLySuCoYKhoa(QuanLySuCoYKhoa quanLySuCoYKhoa) {
        this.quanLySuCoYKhoas.remove(quanLySuCoYKhoa);
        quanLySuCoYKhoa.setThongTinSuCo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThongTinSuCo)) {
            return false;
        }
        return id != null && id.equals(((ThongTinSuCo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThongTinSuCo{" +
            "id=" + getId() +
            ", viTriXayRaSuCo='" + getViTriXayRaSuCo() + "'" +
            ", ngayXayRaSuCo='" + getNgayXayRaSuCo() + "'" +
            ", thoiGian='" + getThoiGian() + "'" +
            "}";
    }
}
