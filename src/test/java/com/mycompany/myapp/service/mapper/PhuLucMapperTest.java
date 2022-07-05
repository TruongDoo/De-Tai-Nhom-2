package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhuLucMapperTest {

    private PhuLucMapper phuLucMapper;

    @BeforeEach
    public void setUp() {
        phuLucMapper = new PhuLucMapperImpl();
    }
}
