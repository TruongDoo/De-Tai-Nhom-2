package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThongTinSuCoMapperTest {

    private ThongTinSuCoMapper thongTinSuCoMapper;

    @BeforeEach
    public void setUp() {
        thongTinSuCoMapper = new ThongTinSuCoMapperImpl();
    }
}
