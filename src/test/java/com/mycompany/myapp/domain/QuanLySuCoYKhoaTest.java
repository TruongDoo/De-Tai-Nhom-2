package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuanLySuCoYKhoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuanLySuCoYKhoa.class);
        QuanLySuCoYKhoa quanLySuCoYKhoa1 = new QuanLySuCoYKhoa();
        quanLySuCoYKhoa1.setId(1L);
        QuanLySuCoYKhoa quanLySuCoYKhoa2 = new QuanLySuCoYKhoa();
        quanLySuCoYKhoa2.setId(quanLySuCoYKhoa1.getId());
        assertThat(quanLySuCoYKhoa1).isEqualTo(quanLySuCoYKhoa2);
        quanLySuCoYKhoa2.setId(2L);
        assertThat(quanLySuCoYKhoa1).isNotEqualTo(quanLySuCoYKhoa2);
        quanLySuCoYKhoa1.setId(null);
        assertThat(quanLySuCoYKhoa1).isNotEqualTo(quanLySuCoYKhoa2);
    }
}
