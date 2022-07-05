package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ThongTinNguoiBenh;
import com.mycompany.myapp.repository.ThongTinNguoiBenhRepository;
import com.mycompany.myapp.service.dto.ThongTinNguoiBenhDTO;
import com.mycompany.myapp.service.mapper.ThongTinNguoiBenhMapper;
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
 * Integration tests for the {@link ThongTinNguoiBenhResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThongTinNguoiBenhResourceIT {

    private static final String DEFAULT_HO_VA_TEN = "AAAAAAAAAA";
    private static final String UPDATED_HO_VA_TEN = "BBBBBBBBBB";

    private static final String DEFAULT_SO_BENH_AN = "AAAAAAAAAA";
    private static final String UPDATED_SO_BENH_AN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GIOI_TINH = false;
    private static final Boolean UPDATED_GIOI_TINH = true;

    private static final String ENTITY_API_URL = "/api/thong-tin-nguoi-benhs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ThongTinNguoiBenhRepository thongTinNguoiBenhRepository;

    @Autowired
    private ThongTinNguoiBenhMapper thongTinNguoiBenhMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThongTinNguoiBenhMockMvc;

    private ThongTinNguoiBenh thongTinNguoiBenh;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThongTinNguoiBenh createEntity(EntityManager em) {
        ThongTinNguoiBenh thongTinNguoiBenh = new ThongTinNguoiBenh()
            .hoVaTen(DEFAULT_HO_VA_TEN)
            .soBenhAn(DEFAULT_SO_BENH_AN)
            .gioiTinh(DEFAULT_GIOI_TINH);
        return thongTinNguoiBenh;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThongTinNguoiBenh createUpdatedEntity(EntityManager em) {
        ThongTinNguoiBenh thongTinNguoiBenh = new ThongTinNguoiBenh()
            .hoVaTen(UPDATED_HO_VA_TEN)
            .soBenhAn(UPDATED_SO_BENH_AN)
            .gioiTinh(UPDATED_GIOI_TINH);
        return thongTinNguoiBenh;
    }

    @BeforeEach
    public void initTest() {
        thongTinNguoiBenh = createEntity(em);
    }

    @Test
    @Transactional
    void createThongTinNguoiBenh() throws Exception {
        int databaseSizeBeforeCreate = thongTinNguoiBenhRepository.findAll().size();
        // Create the ThongTinNguoiBenh
        ThongTinNguoiBenhDTO thongTinNguoiBenhDTO = thongTinNguoiBenhMapper.toDto(thongTinNguoiBenh);
        restThongTinNguoiBenhMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thongTinNguoiBenhDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ThongTinNguoiBenh in the database
        List<ThongTinNguoiBenh> thongTinNguoiBenhList = thongTinNguoiBenhRepository.findAll();
        assertThat(thongTinNguoiBenhList).hasSize(databaseSizeBeforeCreate + 1);
        ThongTinNguoiBenh testThongTinNguoiBenh = thongTinNguoiBenhList.get(thongTinNguoiBenhList.size() - 1);
        assertThat(testThongTinNguoiBenh.getHoVaTen()).isEqualTo(DEFAULT_HO_VA_TEN);
        assertThat(testThongTinNguoiBenh.getSoBenhAn()).isEqualTo(DEFAULT_SO_BENH_AN);
        assertThat(testThongTinNguoiBenh.getGioiTinh()).isEqualTo(DEFAULT_GIOI_TINH);
    }

    @Test
    @Transactional
    void createThongTinNguoiBenhWithExistingId() throws Exception {
        // Create the ThongTinNguoiBenh with an existing ID
        thongTinNguoiBenh.setId(1L);
        ThongTinNguoiBenhDTO thongTinNguoiBenhDTO = thongTinNguoiBenhMapper.toDto(thongTinNguoiBenh);

        int databaseSizeBeforeCreate = thongTinNguoiBenhRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThongTinNguoiBenhMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thongTinNguoiBenhDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinNguoiBenh in the database
        List<ThongTinNguoiBenh> thongTinNguoiBenhList = thongTinNguoiBenhRepository.findAll();
        assertThat(thongTinNguoiBenhList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllThongTinNguoiBenhs() throws Exception {
        // Initialize the database
        thongTinNguoiBenhRepository.saveAndFlush(thongTinNguoiBenh);

        // Get all the thongTinNguoiBenhList
        restThongTinNguoiBenhMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thongTinNguoiBenh.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoVaTen").value(hasItem(DEFAULT_HO_VA_TEN)))
            .andExpect(jsonPath("$.[*].soBenhAn").value(hasItem(DEFAULT_SO_BENH_AN)))
            .andExpect(jsonPath("$.[*].gioiTinh").value(hasItem(DEFAULT_GIOI_TINH.booleanValue())));
    }

    @Test
    @Transactional
    void getThongTinNguoiBenh() throws Exception {
        // Initialize the database
        thongTinNguoiBenhRepository.saveAndFlush(thongTinNguoiBenh);

        // Get the thongTinNguoiBenh
        restThongTinNguoiBenhMockMvc
            .perform(get(ENTITY_API_URL_ID, thongTinNguoiBenh.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thongTinNguoiBenh.getId().intValue()))
            .andExpect(jsonPath("$.hoVaTen").value(DEFAULT_HO_VA_TEN))
            .andExpect(jsonPath("$.soBenhAn").value(DEFAULT_SO_BENH_AN))
            .andExpect(jsonPath("$.gioiTinh").value(DEFAULT_GIOI_TINH.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingThongTinNguoiBenh() throws Exception {
        // Get the thongTinNguoiBenh
        restThongTinNguoiBenhMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewThongTinNguoiBenh() throws Exception {
        // Initialize the database
        thongTinNguoiBenhRepository.saveAndFlush(thongTinNguoiBenh);

        int databaseSizeBeforeUpdate = thongTinNguoiBenhRepository.findAll().size();

        // Update the thongTinNguoiBenh
        ThongTinNguoiBenh updatedThongTinNguoiBenh = thongTinNguoiBenhRepository.findById(thongTinNguoiBenh.getId()).get();
        // Disconnect from session so that the updates on updatedThongTinNguoiBenh are not directly saved in db
        em.detach(updatedThongTinNguoiBenh);
        updatedThongTinNguoiBenh.hoVaTen(UPDATED_HO_VA_TEN).soBenhAn(UPDATED_SO_BENH_AN).gioiTinh(UPDATED_GIOI_TINH);
        ThongTinNguoiBenhDTO thongTinNguoiBenhDTO = thongTinNguoiBenhMapper.toDto(updatedThongTinNguoiBenh);

        restThongTinNguoiBenhMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thongTinNguoiBenhDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thongTinNguoiBenhDTO))
            )
            .andExpect(status().isOk());

        // Validate the ThongTinNguoiBenh in the database
        List<ThongTinNguoiBenh> thongTinNguoiBenhList = thongTinNguoiBenhRepository.findAll();
        assertThat(thongTinNguoiBenhList).hasSize(databaseSizeBeforeUpdate);
        ThongTinNguoiBenh testThongTinNguoiBenh = thongTinNguoiBenhList.get(thongTinNguoiBenhList.size() - 1);
        assertThat(testThongTinNguoiBenh.getHoVaTen()).isEqualTo(UPDATED_HO_VA_TEN);
        assertThat(testThongTinNguoiBenh.getSoBenhAn()).isEqualTo(UPDATED_SO_BENH_AN);
        assertThat(testThongTinNguoiBenh.getGioiTinh()).isEqualTo(UPDATED_GIOI_TINH);
    }

    @Test
    @Transactional
    void putNonExistingThongTinNguoiBenh() throws Exception {
        int databaseSizeBeforeUpdate = thongTinNguoiBenhRepository.findAll().size();
        thongTinNguoiBenh.setId(count.incrementAndGet());

        // Create the ThongTinNguoiBenh
        ThongTinNguoiBenhDTO thongTinNguoiBenhDTO = thongTinNguoiBenhMapper.toDto(thongTinNguoiBenh);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThongTinNguoiBenhMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thongTinNguoiBenhDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thongTinNguoiBenhDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinNguoiBenh in the database
        List<ThongTinNguoiBenh> thongTinNguoiBenhList = thongTinNguoiBenhRepository.findAll();
        assertThat(thongTinNguoiBenhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThongTinNguoiBenh() throws Exception {
        int databaseSizeBeforeUpdate = thongTinNguoiBenhRepository.findAll().size();
        thongTinNguoiBenh.setId(count.incrementAndGet());

        // Create the ThongTinNguoiBenh
        ThongTinNguoiBenhDTO thongTinNguoiBenhDTO = thongTinNguoiBenhMapper.toDto(thongTinNguoiBenh);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThongTinNguoiBenhMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thongTinNguoiBenhDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinNguoiBenh in the database
        List<ThongTinNguoiBenh> thongTinNguoiBenhList = thongTinNguoiBenhRepository.findAll();
        assertThat(thongTinNguoiBenhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThongTinNguoiBenh() throws Exception {
        int databaseSizeBeforeUpdate = thongTinNguoiBenhRepository.findAll().size();
        thongTinNguoiBenh.setId(count.incrementAndGet());

        // Create the ThongTinNguoiBenh
        ThongTinNguoiBenhDTO thongTinNguoiBenhDTO = thongTinNguoiBenhMapper.toDto(thongTinNguoiBenh);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThongTinNguoiBenhMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thongTinNguoiBenhDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThongTinNguoiBenh in the database
        List<ThongTinNguoiBenh> thongTinNguoiBenhList = thongTinNguoiBenhRepository.findAll();
        assertThat(thongTinNguoiBenhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThongTinNguoiBenhWithPatch() throws Exception {
        // Initialize the database
        thongTinNguoiBenhRepository.saveAndFlush(thongTinNguoiBenh);

        int databaseSizeBeforeUpdate = thongTinNguoiBenhRepository.findAll().size();

        // Update the thongTinNguoiBenh using partial update
        ThongTinNguoiBenh partialUpdatedThongTinNguoiBenh = new ThongTinNguoiBenh();
        partialUpdatedThongTinNguoiBenh.setId(thongTinNguoiBenh.getId());

        partialUpdatedThongTinNguoiBenh.hoVaTen(UPDATED_HO_VA_TEN).soBenhAn(UPDATED_SO_BENH_AN).gioiTinh(UPDATED_GIOI_TINH);

        restThongTinNguoiBenhMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThongTinNguoiBenh.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThongTinNguoiBenh))
            )
            .andExpect(status().isOk());

        // Validate the ThongTinNguoiBenh in the database
        List<ThongTinNguoiBenh> thongTinNguoiBenhList = thongTinNguoiBenhRepository.findAll();
        assertThat(thongTinNguoiBenhList).hasSize(databaseSizeBeforeUpdate);
        ThongTinNguoiBenh testThongTinNguoiBenh = thongTinNguoiBenhList.get(thongTinNguoiBenhList.size() - 1);
        assertThat(testThongTinNguoiBenh.getHoVaTen()).isEqualTo(UPDATED_HO_VA_TEN);
        assertThat(testThongTinNguoiBenh.getSoBenhAn()).isEqualTo(UPDATED_SO_BENH_AN);
        assertThat(testThongTinNguoiBenh.getGioiTinh()).isEqualTo(UPDATED_GIOI_TINH);
    }

    @Test
    @Transactional
    void fullUpdateThongTinNguoiBenhWithPatch() throws Exception {
        // Initialize the database
        thongTinNguoiBenhRepository.saveAndFlush(thongTinNguoiBenh);

        int databaseSizeBeforeUpdate = thongTinNguoiBenhRepository.findAll().size();

        // Update the thongTinNguoiBenh using partial update
        ThongTinNguoiBenh partialUpdatedThongTinNguoiBenh = new ThongTinNguoiBenh();
        partialUpdatedThongTinNguoiBenh.setId(thongTinNguoiBenh.getId());

        partialUpdatedThongTinNguoiBenh.hoVaTen(UPDATED_HO_VA_TEN).soBenhAn(UPDATED_SO_BENH_AN).gioiTinh(UPDATED_GIOI_TINH);

        restThongTinNguoiBenhMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThongTinNguoiBenh.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThongTinNguoiBenh))
            )
            .andExpect(status().isOk());

        // Validate the ThongTinNguoiBenh in the database
        List<ThongTinNguoiBenh> thongTinNguoiBenhList = thongTinNguoiBenhRepository.findAll();
        assertThat(thongTinNguoiBenhList).hasSize(databaseSizeBeforeUpdate);
        ThongTinNguoiBenh testThongTinNguoiBenh = thongTinNguoiBenhList.get(thongTinNguoiBenhList.size() - 1);
        assertThat(testThongTinNguoiBenh.getHoVaTen()).isEqualTo(UPDATED_HO_VA_TEN);
        assertThat(testThongTinNguoiBenh.getSoBenhAn()).isEqualTo(UPDATED_SO_BENH_AN);
        assertThat(testThongTinNguoiBenh.getGioiTinh()).isEqualTo(UPDATED_GIOI_TINH);
    }

    @Test
    @Transactional
    void patchNonExistingThongTinNguoiBenh() throws Exception {
        int databaseSizeBeforeUpdate = thongTinNguoiBenhRepository.findAll().size();
        thongTinNguoiBenh.setId(count.incrementAndGet());

        // Create the ThongTinNguoiBenh
        ThongTinNguoiBenhDTO thongTinNguoiBenhDTO = thongTinNguoiBenhMapper.toDto(thongTinNguoiBenh);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThongTinNguoiBenhMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thongTinNguoiBenhDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thongTinNguoiBenhDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinNguoiBenh in the database
        List<ThongTinNguoiBenh> thongTinNguoiBenhList = thongTinNguoiBenhRepository.findAll();
        assertThat(thongTinNguoiBenhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThongTinNguoiBenh() throws Exception {
        int databaseSizeBeforeUpdate = thongTinNguoiBenhRepository.findAll().size();
        thongTinNguoiBenh.setId(count.incrementAndGet());

        // Create the ThongTinNguoiBenh
        ThongTinNguoiBenhDTO thongTinNguoiBenhDTO = thongTinNguoiBenhMapper.toDto(thongTinNguoiBenh);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThongTinNguoiBenhMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thongTinNguoiBenhDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinNguoiBenh in the database
        List<ThongTinNguoiBenh> thongTinNguoiBenhList = thongTinNguoiBenhRepository.findAll();
        assertThat(thongTinNguoiBenhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThongTinNguoiBenh() throws Exception {
        int databaseSizeBeforeUpdate = thongTinNguoiBenhRepository.findAll().size();
        thongTinNguoiBenh.setId(count.incrementAndGet());

        // Create the ThongTinNguoiBenh
        ThongTinNguoiBenhDTO thongTinNguoiBenhDTO = thongTinNguoiBenhMapper.toDto(thongTinNguoiBenh);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThongTinNguoiBenhMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thongTinNguoiBenhDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThongTinNguoiBenh in the database
        List<ThongTinNguoiBenh> thongTinNguoiBenhList = thongTinNguoiBenhRepository.findAll();
        assertThat(thongTinNguoiBenhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThongTinNguoiBenh() throws Exception {
        // Initialize the database
        thongTinNguoiBenhRepository.saveAndFlush(thongTinNguoiBenh);

        int databaseSizeBeforeDelete = thongTinNguoiBenhRepository.findAll().size();

        // Delete the thongTinNguoiBenh
        restThongTinNguoiBenhMockMvc
            .perform(delete(ENTITY_API_URL_ID, thongTinNguoiBenh.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ThongTinNguoiBenh> thongTinNguoiBenhList = thongTinNguoiBenhRepository.findAll();
        assertThat(thongTinNguoiBenhList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
