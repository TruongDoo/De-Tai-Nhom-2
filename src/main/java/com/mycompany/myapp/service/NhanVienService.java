package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.NhanVienDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.NhanVien}.
 */
public interface NhanVienService {
    /**
     * Save a nhanVien.
     *
     * @param nhanVienDTO the entity to save.
     * @return the persisted entity.
     */
    NhanVienDTO save(NhanVienDTO nhanVienDTO);

    /**
     * Updates a nhanVien.
     *
     * @param nhanVienDTO the entity to update.
     * @return the persisted entity.
     */
    NhanVienDTO update(NhanVienDTO nhanVienDTO);

    /**
     * Partially updates a nhanVien.
     *
     * @param nhanVienDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NhanVienDTO> partialUpdate(NhanVienDTO nhanVienDTO);

    /**
     * Get all the nhanViens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NhanVienDTO> findAll(Pageable pageable);

    /**
     * Get the "id" nhanVien.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NhanVienDTO> findOne(Long id);

    /**
     * Delete the "id" nhanVien.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
