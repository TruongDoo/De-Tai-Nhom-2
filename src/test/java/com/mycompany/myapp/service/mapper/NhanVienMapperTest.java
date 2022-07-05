package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NhanVienMapperTest {

    private NhanVienMapper nhanVienMapper;

    @BeforeEach
    public void setUp() {
        nhanVienMapper = new NhanVienMapperImpl();
    }
}
