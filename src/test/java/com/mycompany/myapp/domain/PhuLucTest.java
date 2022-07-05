package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhuLucTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhuLuc.class);
        PhuLuc phuLuc1 = new PhuLuc();
        phuLuc1.setId(1L);
        PhuLuc phuLuc2 = new PhuLuc();
        phuLuc2.setId(phuLuc1.getId());
        assertThat(phuLuc1).isEqualTo(phuLuc2);
        phuLuc2.setId(2L);
        assertThat(phuLuc1).isNotEqualTo(phuLuc2);
        phuLuc1.setId(null);
        assertThat(phuLuc1).isNotEqualTo(phuLuc2);
    }
}
