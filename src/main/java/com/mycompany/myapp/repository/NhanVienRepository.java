package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NhanVien;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NhanVien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {}
