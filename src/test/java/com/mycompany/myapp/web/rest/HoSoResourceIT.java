package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.HoSo;
import com.mycompany.myapp.repository.HoSoRepository;
import com.mycompany.myapp.service.dto.HoSoDTO;
import com.mycompany.myapp.service.mapper.HoSoMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link HoSoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HoSoResourceIT {

    private static final String DEFAULT_SO_THU_TU = "AAAAAAAAAA";
    private static final String UPDATED_SO_THU_TU = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_HO_SO = "AAAAAAAAAA";
    private static final String UPDATED_TEN_HO_SO = "BBBBBBBBBB";

    private static final String DEFAULT_NOI_LUU = "AAAAAAAAAA";
    private static final String UPDATED_NOI_LUU = "BBBBBBBBBB";

    private static final Instant DEFAULT_THOI_GIAN_LUU = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THOI_GIAN_LUU = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ho-sos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HoSoRepository hoSoRepository;

    @Autowired
    private HoSoMapper hoSoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHoSoMockMvc;

    private HoSo hoSo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoSo createEntity(EntityManager em) {
        HoSo hoSo = new HoSo()
            .soThuTu(DEFAULT_SO_THU_TU)
            .tenHoSo(DEFAULT_TEN_HO_SO)
            .noiLuu(DEFAULT_NOI_LUU)
            .thoiGianLuu(DEFAULT_THOI_GIAN_LUU);
        return hoSo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoSo createUpdatedEntity(EntityManager em) {
        HoSo hoSo = new HoSo()
            .soThuTu(UPDATED_SO_THU_TU)
            .tenHoSo(UPDATED_TEN_HO_SO)
            .noiLuu(UPDATED_NOI_LUU)
            .thoiGianLuu(UPDATED_THOI_GIAN_LUU);
        return hoSo;
    }

    @BeforeEach
    public void initTest() {
        hoSo = createEntity(em);
    }

    @Test
    @Transactional
    void createHoSo() throws Exception {
        int databaseSizeBeforeCreate = hoSoRepository.findAll().size();
        // Create the HoSo
        HoSoDTO hoSoDTO = hoSoMapper.toDto(hoSo);
        restHoSoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoSoDTO)))
            .andExpect(status().isCreated());

        // Validate the HoSo in the database
        List<HoSo> hoSoList = hoSoRepository.findAll();
        assertThat(hoSoList).hasSize(databaseSizeBeforeCreate + 1);
        HoSo testHoSo = hoSoList.get(hoSoList.size() - 1);
        assertThat(testHoSo.getSoThuTu()).isEqualTo(DEFAULT_SO_THU_TU);
        assertThat(testHoSo.getTenHoSo()).isEqualTo(DEFAULT_TEN_HO_SO);
        assertThat(testHoSo.getNoiLuu()).isEqualTo(DEFAULT_NOI_LUU);
        assertThat(testHoSo.getThoiGianLuu()).isEqualTo(DEFAULT_THOI_GIAN_LUU);
    }

    @Test
    @Transactional
    void createHoSoWithExistingId() throws Exception {
        // Create the HoSo with an existing ID
        hoSo.setId(1L);
        HoSoDTO hoSoDTO = hoSoMapper.toDto(hoSo);

        int databaseSizeBeforeCreate = hoSoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoSoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoSoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HoSo in the database
        List<HoSo> hoSoList = hoSoRepository.findAll();
        assertThat(hoSoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHoSos() throws Exception {
        // Initialize the database
        hoSoRepository.saveAndFlush(hoSo);

        // Get all the hoSoList
        restHoSoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hoSo.getId().intValue())))
            .andExpect(jsonPath("$.[*].soThuTu").value(hasItem(DEFAULT_SO_THU_TU)))
            .andExpect(jsonPath("$.[*].tenHoSo").value(hasItem(DEFAULT_TEN_HO_SO)))
            .andExpect(jsonPath("$.[*].noiLuu").value(hasItem(DEFAULT_NOI_LUU)))
            .andExpect(jsonPath("$.[*].thoiGianLuu").value(hasItem(DEFAULT_THOI_GIAN_LUU.toString())));
    }

    @Test
    @Transactional
    void getHoSo() throws Exception {
        // Initialize the database
        hoSoRepository.saveAndFlush(hoSo);

        // Get the hoSo
        restHoSoMockMvc
            .perform(get(ENTITY_API_URL_ID, hoSo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hoSo.getId().intValue()))
            .andExpect(jsonPath("$.soThuTu").value(DEFAULT_SO_THU_TU))
            .andExpect(jsonPath("$.tenHoSo").value(DEFAULT_TEN_HO_SO))
            .andExpect(jsonPath("$.noiLuu").value(DEFAULT_NOI_LUU))
            .andExpect(jsonPath("$.thoiGianLuu").value(DEFAULT_THOI_GIAN_LUU.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHoSo() throws Exception {
        // Get the hoSo
        restHoSoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHoSo() throws Exception {
        // Initialize the database
        hoSoRepository.saveAndFlush(hoSo);

        int databaseSizeBeforeUpdate = hoSoRepository.findAll().size();

        // Update the hoSo
        HoSo updatedHoSo = hoSoRepository.findById(hoSo.getId()).get();
        // Disconnect from session so that the updates on updatedHoSo are not directly saved in db
        em.detach(updatedHoSo);
        updatedHoSo.soThuTu(UPDATED_SO_THU_TU).tenHoSo(UPDATED_TEN_HO_SO).noiLuu(UPDATED_NOI_LUU).thoiGianLuu(UPDATED_THOI_GIAN_LUU);
        HoSoDTO hoSoDTO = hoSoMapper.toDto(updatedHoSo);

        restHoSoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hoSoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hoSoDTO))
            )
            .andExpect(status().isOk());

        // Validate the HoSo in the database
        List<HoSo> hoSoList = hoSoRepository.findAll();
        assertThat(hoSoList).hasSize(databaseSizeBeforeUpdate);
        HoSo testHoSo = hoSoList.get(hoSoList.size() - 1);
        assertThat(testHoSo.getSoThuTu()).isEqualTo(UPDATED_SO_THU_TU);
        assertThat(testHoSo.getTenHoSo()).isEqualTo(UPDATED_TEN_HO_SO);
        assertThat(testHoSo.getNoiLuu()).isEqualTo(UPDATED_NOI_LUU);
        assertThat(testHoSo.getThoiGianLuu()).isEqualTo(UPDATED_THOI_GIAN_LUU);
    }

    @Test
    @Transactional
    void putNonExistingHoSo() throws Exception {
        int databaseSizeBeforeUpdate = hoSoRepository.findAll().size();
        hoSo.setId(count.incrementAndGet());

        // Create the HoSo
        HoSoDTO hoSoDTO = hoSoMapper.toDto(hoSo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoSoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hoSoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hoSoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoSo in the database
        List<HoSo> hoSoList = hoSoRepository.findAll();
        assertThat(hoSoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHoSo() throws Exception {
        int databaseSizeBeforeUpdate = hoSoRepository.findAll().size();
        hoSo.setId(count.incrementAndGet());

        // Create the HoSo
        HoSoDTO hoSoDTO = hoSoMapper.toDto(hoSo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoSoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hoSoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoSo in the database
        List<HoSo> hoSoList = hoSoRepository.findAll();
        assertThat(hoSoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHoSo() throws Exception {
        int databaseSizeBeforeUpdate = hoSoRepository.findAll().size();
        hoSo.setId(count.incrementAndGet());

        // Create the HoSo
        HoSoDTO hoSoDTO = hoSoMapper.toDto(hoSo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoSoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoSoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoSo in the database
        List<HoSo> hoSoList = hoSoRepository.findAll();
        assertThat(hoSoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHoSoWithPatch() throws Exception {
        // Initialize the database
        hoSoRepository.saveAndFlush(hoSo);

        int databaseSizeBeforeUpdate = hoSoRepository.findAll().size();

        // Update the hoSo using partial update
        HoSo partialUpdatedHoSo = new HoSo();
        partialUpdatedHoSo.setId(hoSo.getId());

        partialUpdatedHoSo.soThuTu(UPDATED_SO_THU_TU).tenHoSo(UPDATED_TEN_HO_SO);

        restHoSoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoSo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoSo))
            )
            .andExpect(status().isOk());

        // Validate the HoSo in the database
        List<HoSo> hoSoList = hoSoRepository.findAll();
        assertThat(hoSoList).hasSize(databaseSizeBeforeUpdate);
        HoSo testHoSo = hoSoList.get(hoSoList.size() - 1);
        assertThat(testHoSo.getSoThuTu()).isEqualTo(UPDATED_SO_THU_TU);
        assertThat(testHoSo.getTenHoSo()).isEqualTo(UPDATED_TEN_HO_SO);
        assertThat(testHoSo.getNoiLuu()).isEqualTo(DEFAULT_NOI_LUU);
        assertThat(testHoSo.getThoiGianLuu()).isEqualTo(DEFAULT_THOI_GIAN_LUU);
    }

    @Test
    @Transactional
    void fullUpdateHoSoWithPatch() throws Exception {
        // Initialize the database
        hoSoRepository.saveAndFlush(hoSo);

        int databaseSizeBeforeUpdate = hoSoRepository.findAll().size();

        // Update the hoSo using partial update
        HoSo partialUpdatedHoSo = new HoSo();
        partialUpdatedHoSo.setId(hoSo.getId());

        partialUpdatedHoSo.soThuTu(UPDATED_SO_THU_TU).tenHoSo(UPDATED_TEN_HO_SO).noiLuu(UPDATED_NOI_LUU).thoiGianLuu(UPDATED_THOI_GIAN_LUU);

        restHoSoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoSo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoSo))
            )
            .andExpect(status().isOk());

        // Validate the HoSo in the database
        List<HoSo> hoSoList = hoSoRepository.findAll();
        assertThat(hoSoList).hasSize(databaseSizeBeforeUpdate);
        HoSo testHoSo = hoSoList.get(hoSoList.size() - 1);
        assertThat(testHoSo.getSoThuTu()).isEqualTo(UPDATED_SO_THU_TU);
        assertThat(testHoSo.getTenHoSo()).isEqualTo(UPDATED_TEN_HO_SO);
        assertThat(testHoSo.getNoiLuu()).isEqualTo(UPDATED_NOI_LUU);
        assertThat(testHoSo.getThoiGianLuu()).isEqualTo(UPDATED_THOI_GIAN_LUU);
    }

    @Test
    @Transactional
    void patchNonExistingHoSo() throws Exception {
        int databaseSizeBeforeUpdate = hoSoRepository.findAll().size();
        hoSo.setId(count.incrementAndGet());

        // Create the HoSo
        HoSoDTO hoSoDTO = hoSoMapper.toDto(hoSo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoSoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hoSoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hoSoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoSo in the database
        List<HoSo> hoSoList = hoSoRepository.findAll();
        assertThat(hoSoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHoSo() throws Exception {
        int databaseSizeBeforeUpdate = hoSoRepository.findAll().size();
        hoSo.setId(count.incrementAndGet());

        // Create the HoSo
        HoSoDTO hoSoDTO = hoSoMapper.toDto(hoSo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoSoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hoSoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoSo in the database
        List<HoSo> hoSoList = hoSoRepository.findAll();
        assertThat(hoSoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHoSo() throws Exception {
        int databaseSizeBeforeUpdate = hoSoRepository.findAll().size();
        hoSo.setId(count.incrementAndGet());

        // Create the HoSo
        HoSoDTO hoSoDTO = hoSoMapper.toDto(hoSo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoSoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hoSoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoSo in the database
        List<HoSo> hoSoList = hoSoRepository.findAll();
        assertThat(hoSoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHoSo() throws Exception {
        // Initialize the database
        hoSoRepository.saveAndFlush(hoSo);

        int databaseSizeBeforeDelete = hoSoRepository.findAll().size();

        // Delete the hoSo
        restHoSoMockMvc
            .perform(delete(ENTITY_API_URL_ID, hoSo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HoSo> hoSoList = hoSoRepository.findAll();
        assertThat(hoSoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
