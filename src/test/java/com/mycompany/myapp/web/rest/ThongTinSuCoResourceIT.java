package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ThongTinSuCo;
import com.mycompany.myapp.repository.ThongTinSuCoRepository;
import com.mycompany.myapp.service.dto.ThongTinSuCoDTO;
import com.mycompany.myapp.service.mapper.ThongTinSuCoMapper;
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
 * Integration tests for the {@link ThongTinSuCoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThongTinSuCoResourceIT {

    private static final String DEFAULT_VI_TRI_XAY_RA_SU_CO = "AAAAAAAAAA";
    private static final String UPDATED_VI_TRI_XAY_RA_SU_CO = "BBBBBBBBBB";

    private static final Instant DEFAULT_NGAY_XAY_RA_SU_CO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_XAY_RA_SU_CO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_THOI_GIAN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THOI_GIAN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/thong-tin-su-cos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ThongTinSuCoRepository thongTinSuCoRepository;

    @Autowired
    private ThongTinSuCoMapper thongTinSuCoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThongTinSuCoMockMvc;

    private ThongTinSuCo thongTinSuCo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThongTinSuCo createEntity(EntityManager em) {
        ThongTinSuCo thongTinSuCo = new ThongTinSuCo()
            .viTriXayRaSuCo(DEFAULT_VI_TRI_XAY_RA_SU_CO)
            .ngayXayRaSuCo(DEFAULT_NGAY_XAY_RA_SU_CO)
            .thoiGian(DEFAULT_THOI_GIAN);
        return thongTinSuCo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThongTinSuCo createUpdatedEntity(EntityManager em) {
        ThongTinSuCo thongTinSuCo = new ThongTinSuCo()
            .viTriXayRaSuCo(UPDATED_VI_TRI_XAY_RA_SU_CO)
            .ngayXayRaSuCo(UPDATED_NGAY_XAY_RA_SU_CO)
            .thoiGian(UPDATED_THOI_GIAN);
        return thongTinSuCo;
    }

    @BeforeEach
    public void initTest() {
        thongTinSuCo = createEntity(em);
    }

    @Test
    @Transactional
    void createThongTinSuCo() throws Exception {
        int databaseSizeBeforeCreate = thongTinSuCoRepository.findAll().size();
        // Create the ThongTinSuCo
        ThongTinSuCoDTO thongTinSuCoDTO = thongTinSuCoMapper.toDto(thongTinSuCo);
        restThongTinSuCoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thongTinSuCoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ThongTinSuCo in the database
        List<ThongTinSuCo> thongTinSuCoList = thongTinSuCoRepository.findAll();
        assertThat(thongTinSuCoList).hasSize(databaseSizeBeforeCreate + 1);
        ThongTinSuCo testThongTinSuCo = thongTinSuCoList.get(thongTinSuCoList.size() - 1);
        assertThat(testThongTinSuCo.getViTriXayRaSuCo()).isEqualTo(DEFAULT_VI_TRI_XAY_RA_SU_CO);
        assertThat(testThongTinSuCo.getNgayXayRaSuCo()).isEqualTo(DEFAULT_NGAY_XAY_RA_SU_CO);
        assertThat(testThongTinSuCo.getThoiGian()).isEqualTo(DEFAULT_THOI_GIAN);
    }

    @Test
    @Transactional
    void createThongTinSuCoWithExistingId() throws Exception {
        // Create the ThongTinSuCo with an existing ID
        thongTinSuCo.setId(1L);
        ThongTinSuCoDTO thongTinSuCoDTO = thongTinSuCoMapper.toDto(thongTinSuCo);

        int databaseSizeBeforeCreate = thongTinSuCoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThongTinSuCoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thongTinSuCoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinSuCo in the database
        List<ThongTinSuCo> thongTinSuCoList = thongTinSuCoRepository.findAll();
        assertThat(thongTinSuCoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllThongTinSuCos() throws Exception {
        // Initialize the database
        thongTinSuCoRepository.saveAndFlush(thongTinSuCo);

        // Get all the thongTinSuCoList
        restThongTinSuCoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thongTinSuCo.getId().intValue())))
            .andExpect(jsonPath("$.[*].viTriXayRaSuCo").value(hasItem(DEFAULT_VI_TRI_XAY_RA_SU_CO)))
            .andExpect(jsonPath("$.[*].ngayXayRaSuCo").value(hasItem(DEFAULT_NGAY_XAY_RA_SU_CO.toString())))
            .andExpect(jsonPath("$.[*].thoiGian").value(hasItem(DEFAULT_THOI_GIAN.toString())));
    }

    @Test
    @Transactional
    void getThongTinSuCo() throws Exception {
        // Initialize the database
        thongTinSuCoRepository.saveAndFlush(thongTinSuCo);

        // Get the thongTinSuCo
        restThongTinSuCoMockMvc
            .perform(get(ENTITY_API_URL_ID, thongTinSuCo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thongTinSuCo.getId().intValue()))
            .andExpect(jsonPath("$.viTriXayRaSuCo").value(DEFAULT_VI_TRI_XAY_RA_SU_CO))
            .andExpect(jsonPath("$.ngayXayRaSuCo").value(DEFAULT_NGAY_XAY_RA_SU_CO.toString()))
            .andExpect(jsonPath("$.thoiGian").value(DEFAULT_THOI_GIAN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingThongTinSuCo() throws Exception {
        // Get the thongTinSuCo
        restThongTinSuCoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewThongTinSuCo() throws Exception {
        // Initialize the database
        thongTinSuCoRepository.saveAndFlush(thongTinSuCo);

        int databaseSizeBeforeUpdate = thongTinSuCoRepository.findAll().size();

        // Update the thongTinSuCo
        ThongTinSuCo updatedThongTinSuCo = thongTinSuCoRepository.findById(thongTinSuCo.getId()).get();
        // Disconnect from session so that the updates on updatedThongTinSuCo are not directly saved in db
        em.detach(updatedThongTinSuCo);
        updatedThongTinSuCo
            .viTriXayRaSuCo(UPDATED_VI_TRI_XAY_RA_SU_CO)
            .ngayXayRaSuCo(UPDATED_NGAY_XAY_RA_SU_CO)
            .thoiGian(UPDATED_THOI_GIAN);
        ThongTinSuCoDTO thongTinSuCoDTO = thongTinSuCoMapper.toDto(updatedThongTinSuCo);

        restThongTinSuCoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thongTinSuCoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thongTinSuCoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ThongTinSuCo in the database
        List<ThongTinSuCo> thongTinSuCoList = thongTinSuCoRepository.findAll();
        assertThat(thongTinSuCoList).hasSize(databaseSizeBeforeUpdate);
        ThongTinSuCo testThongTinSuCo = thongTinSuCoList.get(thongTinSuCoList.size() - 1);
        assertThat(testThongTinSuCo.getViTriXayRaSuCo()).isEqualTo(UPDATED_VI_TRI_XAY_RA_SU_CO);
        assertThat(testThongTinSuCo.getNgayXayRaSuCo()).isEqualTo(UPDATED_NGAY_XAY_RA_SU_CO);
        assertThat(testThongTinSuCo.getThoiGian()).isEqualTo(UPDATED_THOI_GIAN);
    }

    @Test
    @Transactional
    void putNonExistingThongTinSuCo() throws Exception {
        int databaseSizeBeforeUpdate = thongTinSuCoRepository.findAll().size();
        thongTinSuCo.setId(count.incrementAndGet());

        // Create the ThongTinSuCo
        ThongTinSuCoDTO thongTinSuCoDTO = thongTinSuCoMapper.toDto(thongTinSuCo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThongTinSuCoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thongTinSuCoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thongTinSuCoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinSuCo in the database
        List<ThongTinSuCo> thongTinSuCoList = thongTinSuCoRepository.findAll();
        assertThat(thongTinSuCoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThongTinSuCo() throws Exception {
        int databaseSizeBeforeUpdate = thongTinSuCoRepository.findAll().size();
        thongTinSuCo.setId(count.incrementAndGet());

        // Create the ThongTinSuCo
        ThongTinSuCoDTO thongTinSuCoDTO = thongTinSuCoMapper.toDto(thongTinSuCo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThongTinSuCoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thongTinSuCoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinSuCo in the database
        List<ThongTinSuCo> thongTinSuCoList = thongTinSuCoRepository.findAll();
        assertThat(thongTinSuCoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThongTinSuCo() throws Exception {
        int databaseSizeBeforeUpdate = thongTinSuCoRepository.findAll().size();
        thongTinSuCo.setId(count.incrementAndGet());

        // Create the ThongTinSuCo
        ThongTinSuCoDTO thongTinSuCoDTO = thongTinSuCoMapper.toDto(thongTinSuCo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThongTinSuCoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thongTinSuCoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThongTinSuCo in the database
        List<ThongTinSuCo> thongTinSuCoList = thongTinSuCoRepository.findAll();
        assertThat(thongTinSuCoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThongTinSuCoWithPatch() throws Exception {
        // Initialize the database
        thongTinSuCoRepository.saveAndFlush(thongTinSuCo);

        int databaseSizeBeforeUpdate = thongTinSuCoRepository.findAll().size();

        // Update the thongTinSuCo using partial update
        ThongTinSuCo partialUpdatedThongTinSuCo = new ThongTinSuCo();
        partialUpdatedThongTinSuCo.setId(thongTinSuCo.getId());

        partialUpdatedThongTinSuCo
            .viTriXayRaSuCo(UPDATED_VI_TRI_XAY_RA_SU_CO)
            .ngayXayRaSuCo(UPDATED_NGAY_XAY_RA_SU_CO)
            .thoiGian(UPDATED_THOI_GIAN);

        restThongTinSuCoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThongTinSuCo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThongTinSuCo))
            )
            .andExpect(status().isOk());

        // Validate the ThongTinSuCo in the database
        List<ThongTinSuCo> thongTinSuCoList = thongTinSuCoRepository.findAll();
        assertThat(thongTinSuCoList).hasSize(databaseSizeBeforeUpdate);
        ThongTinSuCo testThongTinSuCo = thongTinSuCoList.get(thongTinSuCoList.size() - 1);
        assertThat(testThongTinSuCo.getViTriXayRaSuCo()).isEqualTo(UPDATED_VI_TRI_XAY_RA_SU_CO);
        assertThat(testThongTinSuCo.getNgayXayRaSuCo()).isEqualTo(UPDATED_NGAY_XAY_RA_SU_CO);
        assertThat(testThongTinSuCo.getThoiGian()).isEqualTo(UPDATED_THOI_GIAN);
    }

    @Test
    @Transactional
    void fullUpdateThongTinSuCoWithPatch() throws Exception {
        // Initialize the database
        thongTinSuCoRepository.saveAndFlush(thongTinSuCo);

        int databaseSizeBeforeUpdate = thongTinSuCoRepository.findAll().size();

        // Update the thongTinSuCo using partial update
        ThongTinSuCo partialUpdatedThongTinSuCo = new ThongTinSuCo();
        partialUpdatedThongTinSuCo.setId(thongTinSuCo.getId());

        partialUpdatedThongTinSuCo
            .viTriXayRaSuCo(UPDATED_VI_TRI_XAY_RA_SU_CO)
            .ngayXayRaSuCo(UPDATED_NGAY_XAY_RA_SU_CO)
            .thoiGian(UPDATED_THOI_GIAN);

        restThongTinSuCoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThongTinSuCo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThongTinSuCo))
            )
            .andExpect(status().isOk());

        // Validate the ThongTinSuCo in the database
        List<ThongTinSuCo> thongTinSuCoList = thongTinSuCoRepository.findAll();
        assertThat(thongTinSuCoList).hasSize(databaseSizeBeforeUpdate);
        ThongTinSuCo testThongTinSuCo = thongTinSuCoList.get(thongTinSuCoList.size() - 1);
        assertThat(testThongTinSuCo.getViTriXayRaSuCo()).isEqualTo(UPDATED_VI_TRI_XAY_RA_SU_CO);
        assertThat(testThongTinSuCo.getNgayXayRaSuCo()).isEqualTo(UPDATED_NGAY_XAY_RA_SU_CO);
        assertThat(testThongTinSuCo.getThoiGian()).isEqualTo(UPDATED_THOI_GIAN);
    }

    @Test
    @Transactional
    void patchNonExistingThongTinSuCo() throws Exception {
        int databaseSizeBeforeUpdate = thongTinSuCoRepository.findAll().size();
        thongTinSuCo.setId(count.incrementAndGet());

        // Create the ThongTinSuCo
        ThongTinSuCoDTO thongTinSuCoDTO = thongTinSuCoMapper.toDto(thongTinSuCo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThongTinSuCoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thongTinSuCoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thongTinSuCoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinSuCo in the database
        List<ThongTinSuCo> thongTinSuCoList = thongTinSuCoRepository.findAll();
        assertThat(thongTinSuCoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThongTinSuCo() throws Exception {
        int databaseSizeBeforeUpdate = thongTinSuCoRepository.findAll().size();
        thongTinSuCo.setId(count.incrementAndGet());

        // Create the ThongTinSuCo
        ThongTinSuCoDTO thongTinSuCoDTO = thongTinSuCoMapper.toDto(thongTinSuCo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThongTinSuCoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thongTinSuCoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinSuCo in the database
        List<ThongTinSuCo> thongTinSuCoList = thongTinSuCoRepository.findAll();
        assertThat(thongTinSuCoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThongTinSuCo() throws Exception {
        int databaseSizeBeforeUpdate = thongTinSuCoRepository.findAll().size();
        thongTinSuCo.setId(count.incrementAndGet());

        // Create the ThongTinSuCo
        ThongTinSuCoDTO thongTinSuCoDTO = thongTinSuCoMapper.toDto(thongTinSuCo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThongTinSuCoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thongTinSuCoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThongTinSuCo in the database
        List<ThongTinSuCo> thongTinSuCoList = thongTinSuCoRepository.findAll();
        assertThat(thongTinSuCoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThongTinSuCo() throws Exception {
        // Initialize the database
        thongTinSuCoRepository.saveAndFlush(thongTinSuCo);

        int databaseSizeBeforeDelete = thongTinSuCoRepository.findAll().size();

        // Delete the thongTinSuCo
        restThongTinSuCoMockMvc
            .perform(delete(ENTITY_API_URL_ID, thongTinSuCo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ThongTinSuCo> thongTinSuCoList = thongTinSuCoRepository.findAll();
        assertThat(thongTinSuCoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
