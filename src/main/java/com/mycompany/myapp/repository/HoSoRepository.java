package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HoSo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HoSo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HoSoRepository extends JpaRepository<HoSo, Long> {}
