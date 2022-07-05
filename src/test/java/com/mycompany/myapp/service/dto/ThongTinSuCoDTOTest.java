package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThongTinSuCoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThongTinSuCoDTO.class);
        ThongTinSuCoDTO thongTinSuCoDTO1 = new ThongTinSuCoDTO();
        thongTinSuCoDTO1.setId(1L);
        ThongTinSuCoDTO thongTinSuCoDTO2 = new ThongTinSuCoDTO();
        assertThat(thongTinSuCoDTO1).isNotEqualTo(thongTinSuCoDTO2);
        thongTinSuCoDTO2.setId(thongTinSuCoDTO1.getId());
        assertThat(thongTinSuCoDTO1).isEqualTo(thongTinSuCoDTO2);
        thongTinSuCoDTO2.setId(2L);
        assertThat(thongTinSuCoDTO1).isNotEqualTo(thongTinSuCoDTO2);
        thongTinSuCoDTO1.setId(null);
        assertThat(thongTinSuCoDTO1).isNotEqualTo(thongTinSuCoDTO2);
    }
}
