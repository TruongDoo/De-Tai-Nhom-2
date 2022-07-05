package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuanLySuCoYKhoaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuanLySuCoYKhoaDTO.class);
        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO1 = new QuanLySuCoYKhoaDTO();
        quanLySuCoYKhoaDTO1.setId(1L);
        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO2 = new QuanLySuCoYKhoaDTO();
        assertThat(quanLySuCoYKhoaDTO1).isNotEqualTo(quanLySuCoYKhoaDTO2);
        quanLySuCoYKhoaDTO2.setId(quanLySuCoYKhoaDTO1.getId());
        assertThat(quanLySuCoYKhoaDTO1).isEqualTo(quanLySuCoYKhoaDTO2);
        quanLySuCoYKhoaDTO2.setId(2L);
        assertThat(quanLySuCoYKhoaDTO1).isNotEqualTo(quanLySuCoYKhoaDTO2);
        quanLySuCoYKhoaDTO1.setId(null);
        assertThat(quanLySuCoYKhoaDTO1).isNotEqualTo(quanLySuCoYKhoaDTO2);
    }
}
