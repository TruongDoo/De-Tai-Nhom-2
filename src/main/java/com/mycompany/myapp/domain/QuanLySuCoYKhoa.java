package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QuanLySuCoYKhoa.
 */
@Entity
@Table(name = "quan_ly_su_co_y_khoa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuanLySuCoYKhoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ma_so_su_co", nullable = false)
    private String maSoSuCo;

    @Column(name = "ngay_bao_cao")
    private Instant ngayBaoCao;

    @Column(name = "don_vi_bao_cao")
    private String donViBaoCao;

    @Column(name = "so_thu_tu")
    private String soThuTu;

    @Column(name = "ten_su_co")
    private String tenSuCo;

    @Column(name = "nhom_su_co")
    private String nhomSuCo;

    @Column(name = "muc_do_su_co")
    private String mucDoSuCo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "quanLySuCoYKhoas" }, allowSetters = true)
    private ThongTinSuCo thongTinSuCo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "quanLySuCoYKhoas" }, allowSetters = true)
    private ThongTinNguoiBenh thongTinNguoiBenh;

    @ManyToOne
    @JsonIgnoreProperties(value = { "quanLySuCoYKhoas" }, allowSetters = true)
    private NhanVien quanLySuCoYKhoa;

    @OneToMany(mappedBy = "quanLySuCoYKhoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "quanLySuCoYKhoa", "phuLuc" }, allowSetters = true)
    private Set<HoSo> hoSos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QuanLySuCoYKhoa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaSoSuCo() {
        return this.maSoSuCo;
    }

    public QuanLySuCoYKhoa maSoSuCo(String maSoSuCo) {
        this.setMaSoSuCo(maSoSuCo);
        return this;
    }

    public void setMaSoSuCo(String maSoSuCo) {
        this.maSoSuCo = maSoSuCo;
    }

    public Instant getNgayBaoCao() {
        return this.ngayBaoCao;
    }

    public QuanLySuCoYKhoa ngayBaoCao(Instant ngayBaoCao) {
        this.setNgayBaoCao(ngayBaoCao);
        return this;
    }

    public void setNgayBaoCao(Instant ngayBaoCao) {
        this.ngayBaoCao = ngayBaoCao;
    }

    public String getDonViBaoCao() {
        return this.donViBaoCao;
    }

    public QuanLySuCoYKhoa donViBaoCao(String donViBaoCao) {
        this.setDonViBaoCao(donViBaoCao);
        return this;
    }

    public void setDonViBaoCao(String donViBaoCao) {
        this.donViBaoCao = donViBaoCao;
    }

    public String getSoThuTu() {
        return this.soThuTu;
    }

    public QuanLySuCoYKhoa soThuTu(String soThuTu) {
        this.setSoThuTu(soThuTu);
        return this;
    }

    public void setSoThuTu(String soThuTu) {
        this.soThuTu = soThuTu;
    }

    public String getTenSuCo() {
        return this.tenSuCo;
    }

    public QuanLySuCoYKhoa tenSuCo(String tenSuCo) {
        this.setTenSuCo(tenSuCo);
        return this;
    }

    public void setTenSuCo(String tenSuCo) {
        this.tenSuCo = tenSuCo;
    }

    public String getNhomSuCo() {
        return this.nhomSuCo;
    }

    public QuanLySuCoYKhoa nhomSuCo(String nhomSuCo) {
        this.setNhomSuCo(nhomSuCo);
        return this;
    }

    public void setNhomSuCo(String nhomSuCo) {
        this.nhomSuCo = nhomSuCo;
    }

    public String getMucDoSuCo() {
        return this.mucDoSuCo;
    }

    public QuanLySuCoYKhoa mucDoSuCo(String mucDoSuCo) {
        this.setMucDoSuCo(mucDoSuCo);
        return this;
    }

    public void setMucDoSuCo(String mucDoSuCo) {
        this.mucDoSuCo = mucDoSuCo;
    }

    public ThongTinSuCo getThongTinSuCo() {
        return this.thongTinSuCo;
    }

    public void setThongTinSuCo(ThongTinSuCo thongTinSuCo) {
        this.thongTinSuCo = thongTinSuCo;
    }

    public QuanLySuCoYKhoa thongTinSuCo(ThongTinSuCo thongTinSuCo) {
        this.setThongTinSuCo(thongTinSuCo);
        return this;
    }

    public ThongTinNguoiBenh getThongTinNguoiBenh() {
        return this.thongTinNguoiBenh;
    }

    public void setThongTinNguoiBenh(ThongTinNguoiBenh thongTinNguoiBenh) {
        this.thongTinNguoiBenh = thongTinNguoiBenh;
    }

    public QuanLySuCoYKhoa thongTinNguoiBenh(ThongTinNguoiBenh thongTinNguoiBenh) {
        this.setThongTinNguoiBenh(thongTinNguoiBenh);
        return this;
    }

    public NhanVien getQuanLySuCoYKhoa() {
        return this.quanLySuCoYKhoa;
    }

    public void setQuanLySuCoYKhoa(NhanVien nhanVien) {
        this.quanLySuCoYKhoa = nhanVien;
    }

    public QuanLySuCoYKhoa quanLySuCoYKhoa(NhanVien nhanVien) {
        this.setQuanLySuCoYKhoa(nhanVien);
        return this;
    }

    public Set<HoSo> getHoSos() {
        return this.hoSos;
    }

    public void setHoSos(Set<HoSo> hoSos) {
        if (this.hoSos != null) {
            this.hoSos.forEach(i -> i.setQuanLySuCoYKhoa(null));
        }
        if (hoSos != null) {
            hoSos.forEach(i -> i.setQuanLySuCoYKhoa(this));
        }
        this.hoSos = hoSos;
    }

    public QuanLySuCoYKhoa hoSos(Set<HoSo> hoSos) {
        this.setHoSos(hoSos);
        return this;
    }

    public QuanLySuCoYKhoa addHoSo(HoSo hoSo) {
        this.hoSos.add(hoSo);
        hoSo.setQuanLySuCoYKhoa(this);
        return this;
    }

    public QuanLySuCoYKhoa removeHoSo(HoSo hoSo) {
        this.hoSos.remove(hoSo);
        hoSo.setQuanLySuCoYKhoa(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuanLySuCoYKhoa)) {
            return false;
        }
        return id != null && id.equals(((QuanLySuCoYKhoa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuanLySuCoYKhoa{" +
            "id=" + getId() +
            ", maSoSuCo='" + getMaSoSuCo() + "'" +
            ", ngayBaoCao='" + getNgayBaoCao() + "'" +
            ", donViBaoCao='" + getDonViBaoCao() + "'" +
            ", soThuTu='" + getSoThuTu() + "'" +
            ", tenSuCo='" + getTenSuCo() + "'" +
            ", nhomSuCo='" + getNhomSuCo() + "'" +
            ", mucDoSuCo='" + getMucDoSuCo() + "'" +
            "}";
    }
}
