package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhuLucDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhuLucDTO.class);
        PhuLucDTO phuLucDTO1 = new PhuLucDTO();
        phuLucDTO1.setId(1L);
        PhuLucDTO phuLucDTO2 = new PhuLucDTO();
        assertThat(phuLucDTO1).isNotEqualTo(phuLucDTO2);
        phuLucDTO2.setId(phuLucDTO1.getId());
        assertThat(phuLucDTO1).isEqualTo(phuLucDTO2);
        phuLucDTO2.setId(2L);
        assertThat(phuLucDTO1).isNotEqualTo(phuLucDTO2);
        phuLucDTO1.setId(null);
        assertThat(phuLucDTO1).isNotEqualTo(phuLucDTO2);
    }
}
