package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HoSoMapperTest {

    private HoSoMapper hoSoMapper;

    @BeforeEach
    public void setUp() {
        hoSoMapper = new HoSoMapperImpl();
    }
}
