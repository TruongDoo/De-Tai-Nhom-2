package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.NhanVien;
import com.mycompany.myapp.repository.NhanVienRepository;
import com.mycompany.myapp.service.dto.NhanVienDTO;
import com.mycompany.myapp.service.mapper.NhanVienMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NhanVienResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NhanVienResourceIT {

    private static final String DEFAULT_CHUC_VU = "AAAAAAAAAA";
    private static final String UPDATED_CHUC_VU = "BBBBBBBBBB";

    private static final String DEFAULT_HO_TEN = "AAAAAAAAAA";
    private static final String UPDATED_HO_TEN = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DIA_CHI = "AAAAAAAAAA";
    private static final String UPDATED_DIA_CHI = "BBBBBBBBBB";

    private static final String DEFAULT_SO_DIEN_THOAI = "AAAAAAAAAA";
    private static final String UPDATED_SO_DIEN_THOAI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nhan-viens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private NhanVienMapper nhanVienMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNhanVienMockMvc;

    private NhanVien nhanVien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhanVien createEntity(EntityManager em) {
        NhanVien nhanVien = new NhanVien()
            .chucVu(DEFAULT_CHUC_VU)
            .hoTen(DEFAULT_HO_TEN)
            .email(DEFAULT_EMAIL)
            .diaChi(DEFAULT_DIA_CHI)
            .soDienThoai(DEFAULT_SO_DIEN_THOAI);
        return nhanVien;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhanVien createUpdatedEntity(EntityManager em) {
        NhanVien nhanVien = new NhanVien()
            .chucVu(UPDATED_CHUC_VU)
            .hoTen(UPDATED_HO_TEN)
            .email(UPDATED_EMAIL)
            .diaChi(UPDATED_DIA_CHI)
            .soDienThoai(UPDATED_SO_DIEN_THOAI);
        return nhanVien;
    }

    @BeforeEach
    public void initTest() {
        nhanVien = createEntity(em);
    }

    @Test
    @Transactional
    void createNhanVien() throws Exception {
        int databaseSizeBeforeCreate = nhanVienRepository.findAll().size();
        // Create the NhanVien
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(nhanVien);
        restNhanVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVienDTO)))
            .andExpect(status().isCreated());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeCreate + 1);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getChucVu()).isEqualTo(DEFAULT_CHUC_VU);
        assertThat(testNhanVien.getHoTen()).isEqualTo(DEFAULT_HO_TEN);
        assertThat(testNhanVien.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testNhanVien.getDiaChi()).isEqualTo(DEFAULT_DIA_CHI);
        assertThat(testNhanVien.getSoDienThoai()).isEqualTo(DEFAULT_SO_DIEN_THOAI);
    }

    @Test
    @Transactional
    void createNhanVienWithExistingId() throws Exception {
        // Create the NhanVien with an existing ID
        nhanVien.setId(1L);
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(nhanVien);

        int databaseSizeBeforeCreate = nhanVienRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhanVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVienDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNhanViens() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList
        restNhanVienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhanVien.getId().intValue())))
            .andExpect(jsonPath("$.[*].chucVu").value(hasItem(DEFAULT_CHUC_VU)))
            .andExpect(jsonPath("$.[*].hoTen").value(hasItem(DEFAULT_HO_TEN)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].diaChi").value(hasItem(DEFAULT_DIA_CHI)))
            .andExpect(jsonPath("$.[*].soDienThoai").value(hasItem(DEFAULT_SO_DIEN_THOAI)));
    }

    @Test
    @Transactional
    void getNhanVien() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get the nhanVien
        restNhanVienMockMvc
            .perform(get(ENTITY_API_URL_ID, nhanVien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nhanVien.getId().intValue()))
            .andExpect(jsonPath("$.chucVu").value(DEFAULT_CHUC_VU))
            .andExpect(jsonPath("$.hoTen").value(DEFAULT_HO_TEN))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.diaChi").value(DEFAULT_DIA_CHI))
            .andExpect(jsonPath("$.soDienThoai").value(DEFAULT_SO_DIEN_THOAI));
    }

    @Test
    @Transactional
    void getNonExistingNhanVien() throws Exception {
        // Get the nhanVien
        restNhanVienMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNhanVien() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();

        // Update the nhanVien
        NhanVien updatedNhanVien = nhanVienRepository.findById(nhanVien.getId()).get();
        // Disconnect from session so that the updates on updatedNhanVien are not directly saved in db
        em.detach(updatedNhanVien);
        updatedNhanVien
            .chucVu(UPDATED_CHUC_VU)
            .hoTen(UPDATED_HO_TEN)
            .email(UPDATED_EMAIL)
            .diaChi(UPDATED_DIA_CHI)
            .soDienThoai(UPDATED_SO_DIEN_THOAI);
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(updatedNhanVien);

        restNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhanVienDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhanVienDTO))
            )
            .andExpect(status().isOk());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getChucVu()).isEqualTo(UPDATED_CHUC_VU);
        assertThat(testNhanVien.getHoTen()).isEqualTo(UPDATED_HO_TEN);
        assertThat(testNhanVien.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNhanVien.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testNhanVien.getSoDienThoai()).isEqualTo(UPDATED_SO_DIEN_THOAI);
    }

    @Test
    @Transactional
    void putNonExistingNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // Create the NhanVien
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(nhanVien);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhanVienDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhanVienDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // Create the NhanVien
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(nhanVien);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhanVienDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // Create the NhanVien
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(nhanVien);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVienDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNhanVienWithPatch() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();

        // Update the nhanVien using partial update
        NhanVien partialUpdatedNhanVien = new NhanVien();
        partialUpdatedNhanVien.setId(nhanVien.getId());

        partialUpdatedNhanVien.hoTen(UPDATED_HO_TEN).diaChi(UPDATED_DIA_CHI).soDienThoai(UPDATED_SO_DIEN_THOAI);

        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhanVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhanVien))
            )
            .andExpect(status().isOk());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getChucVu()).isEqualTo(DEFAULT_CHUC_VU);
        assertThat(testNhanVien.getHoTen()).isEqualTo(UPDATED_HO_TEN);
        assertThat(testNhanVien.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testNhanVien.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testNhanVien.getSoDienThoai()).isEqualTo(UPDATED_SO_DIEN_THOAI);
    }

    @Test
    @Transactional
    void fullUpdateNhanVienWithPatch() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();

        // Update the nhanVien using partial update
        NhanVien partialUpdatedNhanVien = new NhanVien();
        partialUpdatedNhanVien.setId(nhanVien.getId());

        partialUpdatedNhanVien
            .chucVu(UPDATED_CHUC_VU)
            .hoTen(UPDATED_HO_TEN)
            .email(UPDATED_EMAIL)
            .diaChi(UPDATED_DIA_CHI)
            .soDienThoai(UPDATED_SO_DIEN_THOAI);

        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhanVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhanVien))
            )
            .andExpect(status().isOk());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getChucVu()).isEqualTo(UPDATED_CHUC_VU);
        assertThat(testNhanVien.getHoTen()).isEqualTo(UPDATED_HO_TEN);
        assertThat(testNhanVien.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNhanVien.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testNhanVien.getSoDienThoai()).isEqualTo(UPDATED_SO_DIEN_THOAI);
    }

    @Test
    @Transactional
    void patchNonExistingNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // Create the NhanVien
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(nhanVien);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nhanVienDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhanVienDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // Create the NhanVien
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(nhanVien);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhanVienDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // Create the NhanVien
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(nhanVien);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nhanVienDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNhanVien() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        int databaseSizeBeforeDelete = nhanVienRepository.findAll().size();

        // Delete the nhanVien
        restNhanVienMockMvc
            .perform(delete(ENTITY_API_URL_ID, nhanVien.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
