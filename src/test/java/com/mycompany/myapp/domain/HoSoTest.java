package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HoSoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoSo.class);
        HoSo hoSo1 = new HoSo();
        hoSo1.setId(1L);
        HoSo hoSo2 = new HoSo();
        hoSo2.setId(hoSo1.getId());
        assertThat(hoSo1).isEqualTo(hoSo2);
        hoSo2.setId(2L);
        assertThat(hoSo1).isNotEqualTo(hoSo2);
        hoSo1.setId(null);
        assertThat(hoSo1).isNotEqualTo(hoSo2);
    }
}
