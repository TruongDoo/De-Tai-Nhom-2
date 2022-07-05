package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ThongTinNguoiBenh;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ThongTinNguoiBenh entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThongTinNguoiBenhRepository extends JpaRepository<ThongTinNguoiBenh, Long> {}
