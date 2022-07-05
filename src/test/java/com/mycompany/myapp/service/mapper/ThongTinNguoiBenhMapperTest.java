package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThongTinNguoiBenhMapperTest {

    private ThongTinNguoiBenhMapper thongTinNguoiBenhMapper;

    @BeforeEach
    public void setUp() {
        thongTinNguoiBenhMapper = new ThongTinNguoiBenhMapperImpl();
    }
}
