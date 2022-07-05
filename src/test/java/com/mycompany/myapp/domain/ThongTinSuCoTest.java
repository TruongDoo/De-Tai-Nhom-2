package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThongTinSuCoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThongTinSuCo.class);
        ThongTinSuCo thongTinSuCo1 = new ThongTinSuCo();
        thongTinSuCo1.setId(1L);
        ThongTinSuCo thongTinSuCo2 = new ThongTinSuCo();
        thongTinSuCo2.setId(thongTinSuCo1.getId());
        assertThat(thongTinSuCo1).isEqualTo(thongTinSuCo2);
        thongTinSuCo2.setId(2L);
        assertThat(thongTinSuCo1).isNotEqualTo(thongTinSuCo2);
        thongTinSuCo1.setId(null);
        assertThat(thongTinSuCo1).isNotEqualTo(thongTinSuCo2);
    }
}
