package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.HoSoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.HoSo}.
 */
public interface HoSoService {
    /**
     * Save a hoSo.
     *
     * @param hoSoDTO the entity to save.
     * @return the persisted entity.
     */
    HoSoDTO save(HoSoDTO hoSoDTO);

    /**
     * Updates a hoSo.
     *
     * @param hoSoDTO the entity to update.
     * @return the persisted entity.
     */
    HoSoDTO update(HoSoDTO hoSoDTO);

    /**
     * Partially updates a hoSo.
     *
     * @param hoSoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HoSoDTO> partialUpdate(HoSoDTO hoSoDTO);

    /**
     * Get all the hoSos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HoSoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" hoSo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HoSoDTO> findOne(Long id);

    /**
     * Delete the "id" hoSo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
