package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ThongTinSuCoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ThongTinSuCo}.
 */
public interface ThongTinSuCoService {
    /**
     * Save a thongTinSuCo.
     *
     * @param thongTinSuCoDTO the entity to save.
     * @return the persisted entity.
     */
    ThongTinSuCoDTO save(ThongTinSuCoDTO thongTinSuCoDTO);

    /**
     * Updates a thongTinSuCo.
     *
     * @param thongTinSuCoDTO the entity to update.
     * @return the persisted entity.
     */
    ThongTinSuCoDTO update(ThongTinSuCoDTO thongTinSuCoDTO);

    /**
     * Partially updates a thongTinSuCo.
     *
     * @param thongTinSuCoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThongTinSuCoDTO> partialUpdate(ThongTinSuCoDTO thongTinSuCoDTO);

    /**
     * Get all the thongTinSuCos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ThongTinSuCoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" thongTinSuCo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThongTinSuCoDTO> findOne(Long id);

    /**
     * Delete the "id" thongTinSuCo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
