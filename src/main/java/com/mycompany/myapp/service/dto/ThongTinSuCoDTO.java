package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ThongTinSuCo} entity.
 */
public class ThongTinSuCoDTO implements Serializable {

    private Long id;

    private String viTriXayRaSuCo;

    private Instant ngayXayRaSuCo;

    private Instant thoiGian;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getViTriXayRaSuCo() {
        return viTriXayRaSuCo;
    }

    public void setViTriXayRaSuCo(String viTriXayRaSuCo) {
        this.viTriXayRaSuCo = viTriXayRaSuCo;
    }

    public Instant getNgayXayRaSuCo() {
        return ngayXayRaSuCo;
    }

    public void setNgayXayRaSuCo(Instant ngayXayRaSuCo) {
        this.ngayXayRaSuCo = ngayXayRaSuCo;
    }

    public Instant getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Instant thoiGian) {
        this.thoiGian = thoiGian;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThongTinSuCoDTO)) {
            return false;
        }

        ThongTinSuCoDTO thongTinSuCoDTO = (ThongTinSuCoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, thongTinSuCoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThongTinSuCoDTO{" +
            "id=" + getId() +
            ", viTriXayRaSuCo='" + getViTriXayRaSuCo() + "'" +
            ", ngayXayRaSuCo='" + getNgayXayRaSuCo() + "'" +
            ", thoiGian='" + getThoiGian() + "'" +
            "}";
    }
}
