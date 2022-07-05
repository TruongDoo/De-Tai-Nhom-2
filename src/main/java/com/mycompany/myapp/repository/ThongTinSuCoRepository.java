package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ThongTinSuCo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ThongTinSuCo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThongTinSuCoRepository extends JpaRepository<ThongTinSuCo, Long> {}
