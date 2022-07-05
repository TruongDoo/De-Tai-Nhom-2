package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.QuanLySuCoYKhoaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.QuanLySuCoYKhoa}.
 */
public interface QuanLySuCoYKhoaService {
    /**
     * Save a quanLySuCoYKhoa.
     *
     * @param quanLySuCoYKhoaDTO the entity to save.
     * @return the persisted entity.
     */
    QuanLySuCoYKhoaDTO save(QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO);

    /**
     * Updates a quanLySuCoYKhoa.
     *
     * @param quanLySuCoYKhoaDTO the entity to update.
     * @return the persisted entity.
     */
    QuanLySuCoYKhoaDTO update(QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO);

    /**
     * Partially updates a quanLySuCoYKhoa.
     *
     * @param quanLySuCoYKhoaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<QuanLySuCoYKhoaDTO> partialUpdate(QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO);

    /**
     * Get all the quanLySuCoYKhoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuanLySuCoYKhoaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" quanLySuCoYKhoa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuanLySuCoYKhoaDTO> findOne(Long id);

    /**
     * Delete the "id" quanLySuCoYKhoa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
