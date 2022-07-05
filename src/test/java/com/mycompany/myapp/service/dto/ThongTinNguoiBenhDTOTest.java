package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThongTinNguoiBenhDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThongTinNguoiBenhDTO.class);
        ThongTinNguoiBenhDTO thongTinNguoiBenhDTO1 = new ThongTinNguoiBenhDTO();
        thongTinNguoiBenhDTO1.setId(1L);
        ThongTinNguoiBenhDTO thongTinNguoiBenhDTO2 = new ThongTinNguoiBenhDTO();
        assertThat(thongTinNguoiBenhDTO1).isNotEqualTo(thongTinNguoiBenhDTO2);
        thongTinNguoiBenhDTO2.setId(thongTinNguoiBenhDTO1.getId());
        assertThat(thongTinNguoiBenhDTO1).isEqualTo(thongTinNguoiBenhDTO2);
        thongTinNguoiBenhDTO2.setId(2L);
        assertThat(thongTinNguoiBenhDTO1).isNotEqualTo(thongTinNguoiBenhDTO2);
        thongTinNguoiBenhDTO1.setId(null);
        assertThat(thongTinNguoiBenhDTO1).isNotEqualTo(thongTinNguoiBenhDTO2);
    }
}
