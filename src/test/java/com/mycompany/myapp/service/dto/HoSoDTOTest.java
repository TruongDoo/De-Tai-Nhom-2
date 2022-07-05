package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HoSoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoSoDTO.class);
        HoSoDTO hoSoDTO1 = new HoSoDTO();
        hoSoDTO1.setId(1L);
        HoSoDTO hoSoDTO2 = new HoSoDTO();
        assertThat(hoSoDTO1).isNotEqualTo(hoSoDTO2);
        hoSoDTO2.setId(hoSoDTO1.getId());
        assertThat(hoSoDTO1).isEqualTo(hoSoDTO2);
        hoSoDTO2.setId(2L);
        assertThat(hoSoDTO1).isNotEqualTo(hoSoDTO2);
        hoSoDTO1.setId(null);
        assertThat(hoSoDTO1).isNotEqualTo(hoSoDTO2);
    }
}
