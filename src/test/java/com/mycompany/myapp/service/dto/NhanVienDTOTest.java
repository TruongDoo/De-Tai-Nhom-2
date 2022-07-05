package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NhanVienDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhanVienDTO.class);
        NhanVienDTO nhanVienDTO1 = new NhanVienDTO();
        nhanVienDTO1.setId(1L);
        NhanVienDTO nhanVienDTO2 = new NhanVienDTO();
        assertThat(nhanVienDTO1).isNotEqualTo(nhanVienDTO2);
        nhanVienDTO2.setId(nhanVienDTO1.getId());
        assertThat(nhanVienDTO1).isEqualTo(nhanVienDTO2);
        nhanVienDTO2.setId(2L);
        assertThat(nhanVienDTO1).isNotEqualTo(nhanVienDTO2);
        nhanVienDTO1.setId(null);
        assertThat(nhanVienDTO1).isNotEqualTo(nhanVienDTO2);
    }
}
