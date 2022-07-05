package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThongTinNguoiBenhTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThongTinNguoiBenh.class);
        ThongTinNguoiBenh thongTinNguoiBenh1 = new ThongTinNguoiBenh();
        thongTinNguoiBenh1.setId(1L);
        ThongTinNguoiBenh thongTinNguoiBenh2 = new ThongTinNguoiBenh();
        thongTinNguoiBenh2.setId(thongTinNguoiBenh1.getId());
        assertThat(thongTinNguoiBenh1).isEqualTo(thongTinNguoiBenh2);
        thongTinNguoiBenh2.setId(2L);
        assertThat(thongTinNguoiBenh1).isNotEqualTo(thongTinNguoiBenh2);
        thongTinNguoiBenh1.setId(null);
        assertThat(thongTinNguoiBenh1).isNotEqualTo(thongTinNguoiBenh2);
    }
}
