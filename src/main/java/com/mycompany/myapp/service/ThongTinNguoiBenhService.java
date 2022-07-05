package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ThongTinNguoiBenhDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ThongTinNguoiBenh}.
 */
public interface ThongTinNguoiBenhService {
    /**
     * Save a thongTinNguoiBenh.
     *
     * @param thongTinNguoiBenhDTO the entity to save.
     * @return the persisted entity.
     */
    ThongTinNguoiBenhDTO save(ThongTinNguoiBenhDTO thongTinNguoiBenhDTO);

    /**
     * Updates a thongTinNguoiBenh.
     *
     * @param thongTinNguoiBenhDTO the entity to update.
     * @return the persisted entity.
     */
    ThongTinNguoiBenhDTO update(ThongTinNguoiBenhDTO thongTinNguoiBenhDTO);

    /**
     * Partially updates a thongTinNguoiBenh.
     *
     * @param thongTinNguoiBenhDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThongTinNguoiBenhDTO> partialUpdate(ThongTinNguoiBenhDTO thongTinNguoiBenhDTO);

    /**
     * Get all the thongTinNguoiBenhs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ThongTinNguoiBenhDTO> findAll(Pageable pageable);

    /**
     * Get the "id" thongTinNguoiBenh.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThongTinNguoiBenhDTO> findOne(Long id);

    /**
     * Delete the "id" thongTinNguoiBenh.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
