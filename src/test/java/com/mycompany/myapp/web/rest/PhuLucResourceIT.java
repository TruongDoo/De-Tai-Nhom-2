package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PhuLuc;
import com.mycompany.myapp.repository.PhuLucRepository;
import com.mycompany.myapp.service.dto.PhuLucDTO;
import com.mycompany.myapp.service.mapper.PhuLucMapper;
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
 * Integration tests for the {@link PhuLucResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhuLucResourceIT {

    private static final String DEFAULT_SO_THU_TU = "AAAAAAAAAA";
    private static final String UPDATED_SO_THU_TU = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_BIEU_MAU = "AAAAAAAAAA";
    private static final String UPDATED_TEN_BIEU_MAU = "BBBBBBBBBB";

    private static final String DEFAULT_MA_HIEU = "AAAAAAAAAA";
    private static final String UPDATED_MA_HIEU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/phu-lucs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhuLucRepository phuLucRepository;

    @Autowired
    private PhuLucMapper phuLucMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhuLucMockMvc;

    private PhuLuc phuLuc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhuLuc createEntity(EntityManager em) {
        PhuLuc phuLuc = new PhuLuc().soThuTu(DEFAULT_SO_THU_TU).tenBieuMau(DEFAULT_TEN_BIEU_MAU).maHieu(DEFAULT_MA_HIEU);
        return phuLuc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhuLuc createUpdatedEntity(EntityManager em) {
        PhuLuc phuLuc = new PhuLuc().soThuTu(UPDATED_SO_THU_TU).tenBieuMau(UPDATED_TEN_BIEU_MAU).maHieu(UPDATED_MA_HIEU);
        return phuLuc;
    }

    @BeforeEach
    public void initTest() {
        phuLuc = createEntity(em);
    }

    @Test
    @Transactional
    void createPhuLuc() throws Exception {
        int databaseSizeBeforeCreate = phuLucRepository.findAll().size();
        // Create the PhuLuc
        PhuLucDTO phuLucDTO = phuLucMapper.toDto(phuLuc);
        restPhuLucMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phuLucDTO)))
            .andExpect(status().isCreated());

        // Validate the PhuLuc in the database
        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeCreate + 1);
        PhuLuc testPhuLuc = phuLucList.get(phuLucList.size() - 1);
        assertThat(testPhuLuc.getSoThuTu()).isEqualTo(DEFAULT_SO_THU_TU);
        assertThat(testPhuLuc.getTenBieuMau()).isEqualTo(DEFAULT_TEN_BIEU_MAU);
        assertThat(testPhuLuc.getMaHieu()).isEqualTo(DEFAULT_MA_HIEU);
    }

    @Test
    @Transactional
    void createPhuLucWithExistingId() throws Exception {
        // Create the PhuLuc with an existing ID
        phuLuc.setId(1L);
        PhuLucDTO phuLucDTO = phuLucMapper.toDto(phuLuc);

        int databaseSizeBeforeCreate = phuLucRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhuLucMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phuLucDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhuLuc in the database
        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaHieuIsRequired() throws Exception {
        int databaseSizeBeforeTest = phuLucRepository.findAll().size();
        // set the field null
        phuLuc.setMaHieu(null);

        // Create the PhuLuc, which fails.
        PhuLucDTO phuLucDTO = phuLucMapper.toDto(phuLuc);

        restPhuLucMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phuLucDTO)))
            .andExpect(status().isBadRequest());

        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPhuLucs() throws Exception {
        // Initialize the database
        phuLucRepository.saveAndFlush(phuLuc);

        // Get all the phuLucList
        restPhuLucMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phuLuc.getId().intValue())))
            .andExpect(jsonPath("$.[*].soThuTu").value(hasItem(DEFAULT_SO_THU_TU)))
            .andExpect(jsonPath("$.[*].tenBieuMau").value(hasItem(DEFAULT_TEN_BIEU_MAU)))
            .andExpect(jsonPath("$.[*].maHieu").value(hasItem(DEFAULT_MA_HIEU)));
    }

    @Test
    @Transactional
    void getPhuLuc() throws Exception {
        // Initialize the database
        phuLucRepository.saveAndFlush(phuLuc);

        // Get the phuLuc
        restPhuLucMockMvc
            .perform(get(ENTITY_API_URL_ID, phuLuc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phuLuc.getId().intValue()))
            .andExpect(jsonPath("$.soThuTu").value(DEFAULT_SO_THU_TU))
            .andExpect(jsonPath("$.tenBieuMau").value(DEFAULT_TEN_BIEU_MAU))
            .andExpect(jsonPath("$.maHieu").value(DEFAULT_MA_HIEU));
    }

    @Test
    @Transactional
    void getNonExistingPhuLuc() throws Exception {
        // Get the phuLuc
        restPhuLucMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPhuLuc() throws Exception {
        // Initialize the database
        phuLucRepository.saveAndFlush(phuLuc);

        int databaseSizeBeforeUpdate = phuLucRepository.findAll().size();

        // Update the phuLuc
        PhuLuc updatedPhuLuc = phuLucRepository.findById(phuLuc.getId()).get();
        // Disconnect from session so that the updates on updatedPhuLuc are not directly saved in db
        em.detach(updatedPhuLuc);
        updatedPhuLuc.soThuTu(UPDATED_SO_THU_TU).tenBieuMau(UPDATED_TEN_BIEU_MAU).maHieu(UPDATED_MA_HIEU);
        PhuLucDTO phuLucDTO = phuLucMapper.toDto(updatedPhuLuc);

        restPhuLucMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phuLucDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phuLucDTO))
            )
            .andExpect(status().isOk());

        // Validate the PhuLuc in the database
        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeUpdate);
        PhuLuc testPhuLuc = phuLucList.get(phuLucList.size() - 1);
        assertThat(testPhuLuc.getSoThuTu()).isEqualTo(UPDATED_SO_THU_TU);
        assertThat(testPhuLuc.getTenBieuMau()).isEqualTo(UPDATED_TEN_BIEU_MAU);
        assertThat(testPhuLuc.getMaHieu()).isEqualTo(UPDATED_MA_HIEU);
    }

    @Test
    @Transactional
    void putNonExistingPhuLuc() throws Exception {
        int databaseSizeBeforeUpdate = phuLucRepository.findAll().size();
        phuLuc.setId(count.incrementAndGet());

        // Create the PhuLuc
        PhuLucDTO phuLucDTO = phuLucMapper.toDto(phuLuc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhuLucMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phuLucDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phuLucDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhuLuc in the database
        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhuLuc() throws Exception {
        int databaseSizeBeforeUpdate = phuLucRepository.findAll().size();
        phuLuc.setId(count.incrementAndGet());

        // Create the PhuLuc
        PhuLucDTO phuLucDTO = phuLucMapper.toDto(phuLuc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhuLucMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phuLucDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhuLuc in the database
        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhuLuc() throws Exception {
        int databaseSizeBeforeUpdate = phuLucRepository.findAll().size();
        phuLuc.setId(count.incrementAndGet());

        // Create the PhuLuc
        PhuLucDTO phuLucDTO = phuLucMapper.toDto(phuLuc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhuLucMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phuLucDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhuLuc in the database
        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhuLucWithPatch() throws Exception {
        // Initialize the database
        phuLucRepository.saveAndFlush(phuLuc);

        int databaseSizeBeforeUpdate = phuLucRepository.findAll().size();

        // Update the phuLuc using partial update
        PhuLuc partialUpdatedPhuLuc = new PhuLuc();
        partialUpdatedPhuLuc.setId(phuLuc.getId());

        partialUpdatedPhuLuc.soThuTu(UPDATED_SO_THU_TU).tenBieuMau(UPDATED_TEN_BIEU_MAU).maHieu(UPDATED_MA_HIEU);

        restPhuLucMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhuLuc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhuLuc))
            )
            .andExpect(status().isOk());

        // Validate the PhuLuc in the database
        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeUpdate);
        PhuLuc testPhuLuc = phuLucList.get(phuLucList.size() - 1);
        assertThat(testPhuLuc.getSoThuTu()).isEqualTo(UPDATED_SO_THU_TU);
        assertThat(testPhuLuc.getTenBieuMau()).isEqualTo(UPDATED_TEN_BIEU_MAU);
        assertThat(testPhuLuc.getMaHieu()).isEqualTo(UPDATED_MA_HIEU);
    }

    @Test
    @Transactional
    void fullUpdatePhuLucWithPatch() throws Exception {
        // Initialize the database
        phuLucRepository.saveAndFlush(phuLuc);

        int databaseSizeBeforeUpdate = phuLucRepository.findAll().size();

        // Update the phuLuc using partial update
        PhuLuc partialUpdatedPhuLuc = new PhuLuc();
        partialUpdatedPhuLuc.setId(phuLuc.getId());

        partialUpdatedPhuLuc.soThuTu(UPDATED_SO_THU_TU).tenBieuMau(UPDATED_TEN_BIEU_MAU).maHieu(UPDATED_MA_HIEU);

        restPhuLucMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhuLuc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhuLuc))
            )
            .andExpect(status().isOk());

        // Validate the PhuLuc in the database
        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeUpdate);
        PhuLuc testPhuLuc = phuLucList.get(phuLucList.size() - 1);
        assertThat(testPhuLuc.getSoThuTu()).isEqualTo(UPDATED_SO_THU_TU);
        assertThat(testPhuLuc.getTenBieuMau()).isEqualTo(UPDATED_TEN_BIEU_MAU);
        assertThat(testPhuLuc.getMaHieu()).isEqualTo(UPDATED_MA_HIEU);
    }

    @Test
    @Transactional
    void patchNonExistingPhuLuc() throws Exception {
        int databaseSizeBeforeUpdate = phuLucRepository.findAll().size();
        phuLuc.setId(count.incrementAndGet());

        // Create the PhuLuc
        PhuLucDTO phuLucDTO = phuLucMapper.toDto(phuLuc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhuLucMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phuLucDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phuLucDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhuLuc in the database
        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhuLuc() throws Exception {
        int databaseSizeBeforeUpdate = phuLucRepository.findAll().size();
        phuLuc.setId(count.incrementAndGet());

        // Create the PhuLuc
        PhuLucDTO phuLucDTO = phuLucMapper.toDto(phuLuc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhuLucMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phuLucDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhuLuc in the database
        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhuLuc() throws Exception {
        int databaseSizeBeforeUpdate = phuLucRepository.findAll().size();
        phuLuc.setId(count.incrementAndGet());

        // Create the PhuLuc
        PhuLucDTO phuLucDTO = phuLucMapper.toDto(phuLuc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhuLucMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(phuLucDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhuLuc in the database
        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhuLuc() throws Exception {
        // Initialize the database
        phuLucRepository.saveAndFlush(phuLuc);

        int databaseSizeBeforeDelete = phuLucRepository.findAll().size();

        // Delete the phuLuc
        restPhuLucMockMvc
            .perform(delete(ENTITY_API_URL_ID, phuLuc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhuLuc> phuLucList = phuLucRepository.findAll();
        assertThat(phuLucList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
