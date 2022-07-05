package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.QuanLySuCoYKhoa;
import com.mycompany.myapp.repository.QuanLySuCoYKhoaRepository;
import com.mycompany.myapp.service.dto.QuanLySuCoYKhoaDTO;
import com.mycompany.myapp.service.mapper.QuanLySuCoYKhoaMapper;
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
 * Integration tests for the {@link QuanLySuCoYKhoaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuanLySuCoYKhoaResourceIT {

    private static final String DEFAULT_MA_SO_SU_CO = "AAAAAAAAAA";
    private static final String UPDATED_MA_SO_SU_CO = "BBBBBBBBBB";

    private static final Instant DEFAULT_NGAY_BAO_CAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_BAO_CAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DON_VI_BAO_CAO = "AAAAAAAAAA";
    private static final String UPDATED_DON_VI_BAO_CAO = "BBBBBBBBBB";

    private static final String DEFAULT_SO_THU_TU = "AAAAAAAAAA";
    private static final String UPDATED_SO_THU_TU = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_SU_CO = "AAAAAAAAAA";
    private static final String UPDATED_TEN_SU_CO = "BBBBBBBBBB";

    private static final String DEFAULT_NHOM_SU_CO = "AAAAAAAAAA";
    private static final String UPDATED_NHOM_SU_CO = "BBBBBBBBBB";

    private static final String DEFAULT_MUC_DO_SU_CO = "AAAAAAAAAA";
    private static final String UPDATED_MUC_DO_SU_CO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/quan-ly-su-co-y-khoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuanLySuCoYKhoaRepository quanLySuCoYKhoaRepository;

    @Autowired
    private QuanLySuCoYKhoaMapper quanLySuCoYKhoaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuanLySuCoYKhoaMockMvc;

    private QuanLySuCoYKhoa quanLySuCoYKhoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuanLySuCoYKhoa createEntity(EntityManager em) {
        QuanLySuCoYKhoa quanLySuCoYKhoa = new QuanLySuCoYKhoa()
            .maSoSuCo(DEFAULT_MA_SO_SU_CO)
            .ngayBaoCao(DEFAULT_NGAY_BAO_CAO)
            .donViBaoCao(DEFAULT_DON_VI_BAO_CAO)
            .soThuTu(DEFAULT_SO_THU_TU)
            .tenSuCo(DEFAULT_TEN_SU_CO)
            .nhomSuCo(DEFAULT_NHOM_SU_CO)
            .mucDoSuCo(DEFAULT_MUC_DO_SU_CO);
        return quanLySuCoYKhoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuanLySuCoYKhoa createUpdatedEntity(EntityManager em) {
        QuanLySuCoYKhoa quanLySuCoYKhoa = new QuanLySuCoYKhoa()
            .maSoSuCo(UPDATED_MA_SO_SU_CO)
            .ngayBaoCao(UPDATED_NGAY_BAO_CAO)
            .donViBaoCao(UPDATED_DON_VI_BAO_CAO)
            .soThuTu(UPDATED_SO_THU_TU)
            .tenSuCo(UPDATED_TEN_SU_CO)
            .nhomSuCo(UPDATED_NHOM_SU_CO)
            .mucDoSuCo(UPDATED_MUC_DO_SU_CO);
        return quanLySuCoYKhoa;
    }

    @BeforeEach
    public void initTest() {
        quanLySuCoYKhoa = createEntity(em);
    }

    @Test
    @Transactional
    void createQuanLySuCoYKhoa() throws Exception {
        int databaseSizeBeforeCreate = quanLySuCoYKhoaRepository.findAll().size();
        // Create the QuanLySuCoYKhoa
        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO = quanLySuCoYKhoaMapper.toDto(quanLySuCoYKhoa);
        restQuanLySuCoYKhoaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quanLySuCoYKhoaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the QuanLySuCoYKhoa in the database
        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeCreate + 1);
        QuanLySuCoYKhoa testQuanLySuCoYKhoa = quanLySuCoYKhoaList.get(quanLySuCoYKhoaList.size() - 1);
        assertThat(testQuanLySuCoYKhoa.getMaSoSuCo()).isEqualTo(DEFAULT_MA_SO_SU_CO);
        assertThat(testQuanLySuCoYKhoa.getNgayBaoCao()).isEqualTo(DEFAULT_NGAY_BAO_CAO);
        assertThat(testQuanLySuCoYKhoa.getDonViBaoCao()).isEqualTo(DEFAULT_DON_VI_BAO_CAO);
        assertThat(testQuanLySuCoYKhoa.getSoThuTu()).isEqualTo(DEFAULT_SO_THU_TU);
        assertThat(testQuanLySuCoYKhoa.getTenSuCo()).isEqualTo(DEFAULT_TEN_SU_CO);
        assertThat(testQuanLySuCoYKhoa.getNhomSuCo()).isEqualTo(DEFAULT_NHOM_SU_CO);
        assertThat(testQuanLySuCoYKhoa.getMucDoSuCo()).isEqualTo(DEFAULT_MUC_DO_SU_CO);
    }

    @Test
    @Transactional
    void createQuanLySuCoYKhoaWithExistingId() throws Exception {
        // Create the QuanLySuCoYKhoa with an existing ID
        quanLySuCoYKhoa.setId(1L);
        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO = quanLySuCoYKhoaMapper.toDto(quanLySuCoYKhoa);

        int databaseSizeBeforeCreate = quanLySuCoYKhoaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuanLySuCoYKhoaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quanLySuCoYKhoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuanLySuCoYKhoa in the database
        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaSoSuCoIsRequired() throws Exception {
        int databaseSizeBeforeTest = quanLySuCoYKhoaRepository.findAll().size();
        // set the field null
        quanLySuCoYKhoa.setMaSoSuCo(null);

        // Create the QuanLySuCoYKhoa, which fails.
        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO = quanLySuCoYKhoaMapper.toDto(quanLySuCoYKhoa);

        restQuanLySuCoYKhoaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quanLySuCoYKhoaDTO))
            )
            .andExpect(status().isBadRequest());

        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQuanLySuCoYKhoas() throws Exception {
        // Initialize the database
        quanLySuCoYKhoaRepository.saveAndFlush(quanLySuCoYKhoa);

        // Get all the quanLySuCoYKhoaList
        restQuanLySuCoYKhoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quanLySuCoYKhoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].maSoSuCo").value(hasItem(DEFAULT_MA_SO_SU_CO)))
            .andExpect(jsonPath("$.[*].ngayBaoCao").value(hasItem(DEFAULT_NGAY_BAO_CAO.toString())))
            .andExpect(jsonPath("$.[*].donViBaoCao").value(hasItem(DEFAULT_DON_VI_BAO_CAO)))
            .andExpect(jsonPath("$.[*].soThuTu").value(hasItem(DEFAULT_SO_THU_TU)))
            .andExpect(jsonPath("$.[*].tenSuCo").value(hasItem(DEFAULT_TEN_SU_CO)))
            .andExpect(jsonPath("$.[*].nhomSuCo").value(hasItem(DEFAULT_NHOM_SU_CO)))
            .andExpect(jsonPath("$.[*].mucDoSuCo").value(hasItem(DEFAULT_MUC_DO_SU_CO)));
    }

    @Test
    @Transactional
    void getQuanLySuCoYKhoa() throws Exception {
        // Initialize the database
        quanLySuCoYKhoaRepository.saveAndFlush(quanLySuCoYKhoa);

        // Get the quanLySuCoYKhoa
        restQuanLySuCoYKhoaMockMvc
            .perform(get(ENTITY_API_URL_ID, quanLySuCoYKhoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quanLySuCoYKhoa.getId().intValue()))
            .andExpect(jsonPath("$.maSoSuCo").value(DEFAULT_MA_SO_SU_CO))
            .andExpect(jsonPath("$.ngayBaoCao").value(DEFAULT_NGAY_BAO_CAO.toString()))
            .andExpect(jsonPath("$.donViBaoCao").value(DEFAULT_DON_VI_BAO_CAO))
            .andExpect(jsonPath("$.soThuTu").value(DEFAULT_SO_THU_TU))
            .andExpect(jsonPath("$.tenSuCo").value(DEFAULT_TEN_SU_CO))
            .andExpect(jsonPath("$.nhomSuCo").value(DEFAULT_NHOM_SU_CO))
            .andExpect(jsonPath("$.mucDoSuCo").value(DEFAULT_MUC_DO_SU_CO));
    }

    @Test
    @Transactional
    void getNonExistingQuanLySuCoYKhoa() throws Exception {
        // Get the quanLySuCoYKhoa
        restQuanLySuCoYKhoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuanLySuCoYKhoa() throws Exception {
        // Initialize the database
        quanLySuCoYKhoaRepository.saveAndFlush(quanLySuCoYKhoa);

        int databaseSizeBeforeUpdate = quanLySuCoYKhoaRepository.findAll().size();

        // Update the quanLySuCoYKhoa
        QuanLySuCoYKhoa updatedQuanLySuCoYKhoa = quanLySuCoYKhoaRepository.findById(quanLySuCoYKhoa.getId()).get();
        // Disconnect from session so that the updates on updatedQuanLySuCoYKhoa are not directly saved in db
        em.detach(updatedQuanLySuCoYKhoa);
        updatedQuanLySuCoYKhoa
            .maSoSuCo(UPDATED_MA_SO_SU_CO)
            .ngayBaoCao(UPDATED_NGAY_BAO_CAO)
            .donViBaoCao(UPDATED_DON_VI_BAO_CAO)
            .soThuTu(UPDATED_SO_THU_TU)
            .tenSuCo(UPDATED_TEN_SU_CO)
            .nhomSuCo(UPDATED_NHOM_SU_CO)
            .mucDoSuCo(UPDATED_MUC_DO_SU_CO);
        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO = quanLySuCoYKhoaMapper.toDto(updatedQuanLySuCoYKhoa);

        restQuanLySuCoYKhoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quanLySuCoYKhoaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quanLySuCoYKhoaDTO))
            )
            .andExpect(status().isOk());

        // Validate the QuanLySuCoYKhoa in the database
        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeUpdate);
        QuanLySuCoYKhoa testQuanLySuCoYKhoa = quanLySuCoYKhoaList.get(quanLySuCoYKhoaList.size() - 1);
        assertThat(testQuanLySuCoYKhoa.getMaSoSuCo()).isEqualTo(UPDATED_MA_SO_SU_CO);
        assertThat(testQuanLySuCoYKhoa.getNgayBaoCao()).isEqualTo(UPDATED_NGAY_BAO_CAO);
        assertThat(testQuanLySuCoYKhoa.getDonViBaoCao()).isEqualTo(UPDATED_DON_VI_BAO_CAO);
        assertThat(testQuanLySuCoYKhoa.getSoThuTu()).isEqualTo(UPDATED_SO_THU_TU);
        assertThat(testQuanLySuCoYKhoa.getTenSuCo()).isEqualTo(UPDATED_TEN_SU_CO);
        assertThat(testQuanLySuCoYKhoa.getNhomSuCo()).isEqualTo(UPDATED_NHOM_SU_CO);
        assertThat(testQuanLySuCoYKhoa.getMucDoSuCo()).isEqualTo(UPDATED_MUC_DO_SU_CO);
    }

    @Test
    @Transactional
    void putNonExistingQuanLySuCoYKhoa() throws Exception {
        int databaseSizeBeforeUpdate = quanLySuCoYKhoaRepository.findAll().size();
        quanLySuCoYKhoa.setId(count.incrementAndGet());

        // Create the QuanLySuCoYKhoa
        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO = quanLySuCoYKhoaMapper.toDto(quanLySuCoYKhoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuanLySuCoYKhoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quanLySuCoYKhoaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quanLySuCoYKhoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuanLySuCoYKhoa in the database
        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuanLySuCoYKhoa() throws Exception {
        int databaseSizeBeforeUpdate = quanLySuCoYKhoaRepository.findAll().size();
        quanLySuCoYKhoa.setId(count.incrementAndGet());

        // Create the QuanLySuCoYKhoa
        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO = quanLySuCoYKhoaMapper.toDto(quanLySuCoYKhoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuanLySuCoYKhoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quanLySuCoYKhoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuanLySuCoYKhoa in the database
        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuanLySuCoYKhoa() throws Exception {
        int databaseSizeBeforeUpdate = quanLySuCoYKhoaRepository.findAll().size();
        quanLySuCoYKhoa.setId(count.incrementAndGet());

        // Create the QuanLySuCoYKhoa
        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO = quanLySuCoYKhoaMapper.toDto(quanLySuCoYKhoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuanLySuCoYKhoaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quanLySuCoYKhoaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuanLySuCoYKhoa in the database
        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuanLySuCoYKhoaWithPatch() throws Exception {
        // Initialize the database
        quanLySuCoYKhoaRepository.saveAndFlush(quanLySuCoYKhoa);

        int databaseSizeBeforeUpdate = quanLySuCoYKhoaRepository.findAll().size();

        // Update the quanLySuCoYKhoa using partial update
        QuanLySuCoYKhoa partialUpdatedQuanLySuCoYKhoa = new QuanLySuCoYKhoa();
        partialUpdatedQuanLySuCoYKhoa.setId(quanLySuCoYKhoa.getId());

        partialUpdatedQuanLySuCoYKhoa
            .ngayBaoCao(UPDATED_NGAY_BAO_CAO)
            .soThuTu(UPDATED_SO_THU_TU)
            .tenSuCo(UPDATED_TEN_SU_CO)
            .nhomSuCo(UPDATED_NHOM_SU_CO)
            .mucDoSuCo(UPDATED_MUC_DO_SU_CO);

        restQuanLySuCoYKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuanLySuCoYKhoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuanLySuCoYKhoa))
            )
            .andExpect(status().isOk());

        // Validate the QuanLySuCoYKhoa in the database
        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeUpdate);
        QuanLySuCoYKhoa testQuanLySuCoYKhoa = quanLySuCoYKhoaList.get(quanLySuCoYKhoaList.size() - 1);
        assertThat(testQuanLySuCoYKhoa.getMaSoSuCo()).isEqualTo(DEFAULT_MA_SO_SU_CO);
        assertThat(testQuanLySuCoYKhoa.getNgayBaoCao()).isEqualTo(UPDATED_NGAY_BAO_CAO);
        assertThat(testQuanLySuCoYKhoa.getDonViBaoCao()).isEqualTo(DEFAULT_DON_VI_BAO_CAO);
        assertThat(testQuanLySuCoYKhoa.getSoThuTu()).isEqualTo(UPDATED_SO_THU_TU);
        assertThat(testQuanLySuCoYKhoa.getTenSuCo()).isEqualTo(UPDATED_TEN_SU_CO);
        assertThat(testQuanLySuCoYKhoa.getNhomSuCo()).isEqualTo(UPDATED_NHOM_SU_CO);
        assertThat(testQuanLySuCoYKhoa.getMucDoSuCo()).isEqualTo(UPDATED_MUC_DO_SU_CO);
    }

    @Test
    @Transactional
    void fullUpdateQuanLySuCoYKhoaWithPatch() throws Exception {
        // Initialize the database
        quanLySuCoYKhoaRepository.saveAndFlush(quanLySuCoYKhoa);

        int databaseSizeBeforeUpdate = quanLySuCoYKhoaRepository.findAll().size();

        // Update the quanLySuCoYKhoa using partial update
        QuanLySuCoYKhoa partialUpdatedQuanLySuCoYKhoa = new QuanLySuCoYKhoa();
        partialUpdatedQuanLySuCoYKhoa.setId(quanLySuCoYKhoa.getId());

        partialUpdatedQuanLySuCoYKhoa
            .maSoSuCo(UPDATED_MA_SO_SU_CO)
            .ngayBaoCao(UPDATED_NGAY_BAO_CAO)
            .donViBaoCao(UPDATED_DON_VI_BAO_CAO)
            .soThuTu(UPDATED_SO_THU_TU)
            .tenSuCo(UPDATED_TEN_SU_CO)
            .nhomSuCo(UPDATED_NHOM_SU_CO)
            .mucDoSuCo(UPDATED_MUC_DO_SU_CO);

        restQuanLySuCoYKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuanLySuCoYKhoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuanLySuCoYKhoa))
            )
            .andExpect(status().isOk());

        // Validate the QuanLySuCoYKhoa in the database
        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeUpdate);
        QuanLySuCoYKhoa testQuanLySuCoYKhoa = quanLySuCoYKhoaList.get(quanLySuCoYKhoaList.size() - 1);
        assertThat(testQuanLySuCoYKhoa.getMaSoSuCo()).isEqualTo(UPDATED_MA_SO_SU_CO);
        assertThat(testQuanLySuCoYKhoa.getNgayBaoCao()).isEqualTo(UPDATED_NGAY_BAO_CAO);
        assertThat(testQuanLySuCoYKhoa.getDonViBaoCao()).isEqualTo(UPDATED_DON_VI_BAO_CAO);
        assertThat(testQuanLySuCoYKhoa.getSoThuTu()).isEqualTo(UPDATED_SO_THU_TU);
        assertThat(testQuanLySuCoYKhoa.getTenSuCo()).isEqualTo(UPDATED_TEN_SU_CO);
        assertThat(testQuanLySuCoYKhoa.getNhomSuCo()).isEqualTo(UPDATED_NHOM_SU_CO);
        assertThat(testQuanLySuCoYKhoa.getMucDoSuCo()).isEqualTo(UPDATED_MUC_DO_SU_CO);
    }

    @Test
    @Transactional
    void patchNonExistingQuanLySuCoYKhoa() throws Exception {
        int databaseSizeBeforeUpdate = quanLySuCoYKhoaRepository.findAll().size();
        quanLySuCoYKhoa.setId(count.incrementAndGet());

        // Create the QuanLySuCoYKhoa
        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO = quanLySuCoYKhoaMapper.toDto(quanLySuCoYKhoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuanLySuCoYKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quanLySuCoYKhoaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quanLySuCoYKhoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuanLySuCoYKhoa in the database
        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuanLySuCoYKhoa() throws Exception {
        int databaseSizeBeforeUpdate = quanLySuCoYKhoaRepository.findAll().size();
        quanLySuCoYKhoa.setId(count.incrementAndGet());

        // Create the QuanLySuCoYKhoa
        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO = quanLySuCoYKhoaMapper.toDto(quanLySuCoYKhoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuanLySuCoYKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quanLySuCoYKhoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuanLySuCoYKhoa in the database
        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuanLySuCoYKhoa() throws Exception {
        int databaseSizeBeforeUpdate = quanLySuCoYKhoaRepository.findAll().size();
        quanLySuCoYKhoa.setId(count.incrementAndGet());

        // Create the QuanLySuCoYKhoa
        QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO = quanLySuCoYKhoaMapper.toDto(quanLySuCoYKhoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuanLySuCoYKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quanLySuCoYKhoaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuanLySuCoYKhoa in the database
        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuanLySuCoYKhoa() throws Exception {
        // Initialize the database
        quanLySuCoYKhoaRepository.saveAndFlush(quanLySuCoYKhoa);

        int databaseSizeBeforeDelete = quanLySuCoYKhoaRepository.findAll().size();

        // Delete the quanLySuCoYKhoa
        restQuanLySuCoYKhoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, quanLySuCoYKhoa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuanLySuCoYKhoa> quanLySuCoYKhoaList = quanLySuCoYKhoaRepository.findAll();
        assertThat(quanLySuCoYKhoaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
