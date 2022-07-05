package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PhuLucDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.PhuLuc}.
 */
public interface PhuLucService {
    /**
     * Save a phuLuc.
     *
     * @param phuLucDTO the entity to save.
     * @return the persisted entity.
     */
    PhuLucDTO save(PhuLucDTO phuLucDTO);

    /**
     * Updates a phuLuc.
     *
     * @param phuLucDTO the entity to update.
     * @return the persisted entity.
     */
    PhuLucDTO update(PhuLucDTO phuLucDTO);

    /**
     * Partially updates a phuLuc.
     *
     * @param phuLucDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PhuLucDTO> partialUpdate(PhuLucDTO phuLucDTO);

    /**
     * Get all the phuLucs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhuLucDTO> findAll(Pageable pageable);

    /**
     * Get the "id" phuLuc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhuLucDTO> findOne(Long id);

    /**
     * Delete the "id" phuLuc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
