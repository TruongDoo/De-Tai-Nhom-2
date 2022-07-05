package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PhuLuc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PhuLuc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhuLucRepository extends JpaRepository<PhuLuc, Long> {}
