package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ThongTinNguoiBenhRepository;
import com.mycompany.myapp.service.ThongTinNguoiBenhService;
import com.mycompany.myapp.service.dto.ThongTinNguoiBenhDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ThongTinNguoiBenh}.
 */
@RestController
@RequestMapping("/api")
public class ThongTinNguoiBenhResource {

    private final Logger log = LoggerFactory.getLogger(ThongTinNguoiBenhResource.class);

    private static final String ENTITY_NAME = "thongTinNguoiBenh";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThongTinNguoiBenhService thongTinNguoiBenhService;

    private final ThongTinNguoiBenhRepository thongTinNguoiBenhRepository;

    public ThongTinNguoiBenhResource(
        ThongTinNguoiBenhService thongTinNguoiBenhService,
        ThongTinNguoiBenhRepository thongTinNguoiBenhRepository
    ) {
        this.thongTinNguoiBenhService = thongTinNguoiBenhService;
        this.thongTinNguoiBenhRepository = thongTinNguoiBenhRepository;
    }

    /**
     * {@code POST  /thong-tin-nguoi-benhs} : Create a new thongTinNguoiBenh.
     *
     * @param thongTinNguoiBenhDTO the thongTinNguoiBenhDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thongTinNguoiBenhDTO, or with status {@code 400 (Bad Request)} if the thongTinNguoiBenh has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thong-tin-nguoi-benhs")
    public ResponseEntity<ThongTinNguoiBenhDTO> createThongTinNguoiBenh(@RequestBody ThongTinNguoiBenhDTO thongTinNguoiBenhDTO)
        throws URISyntaxException {
        log.debug("REST request to save ThongTinNguoiBenh : {}", thongTinNguoiBenhDTO);
        if (thongTinNguoiBenhDTO.getId() != null) {
            throw new BadRequestAlertException("A new thongTinNguoiBenh cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThongTinNguoiBenhDTO result = thongTinNguoiBenhService.save(thongTinNguoiBenhDTO);
        return ResponseEntity
            .created(new URI("/api/thong-tin-nguoi-benhs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thong-tin-nguoi-benhs/:id} : Updates an existing thongTinNguoiBenh.
     *
     * @param id the id of the thongTinNguoiBenhDTO to save.
     * @param thongTinNguoiBenhDTO the thongTinNguoiBenhDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thongTinNguoiBenhDTO,
     * or with status {@code 400 (Bad Request)} if the thongTinNguoiBenhDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thongTinNguoiBenhDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thong-tin-nguoi-benhs/{id}")
    public ResponseEntity<ThongTinNguoiBenhDTO> updateThongTinNguoiBenh(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThongTinNguoiBenhDTO thongTinNguoiBenhDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ThongTinNguoiBenh : {}, {}", id, thongTinNguoiBenhDTO);
        if (thongTinNguoiBenhDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thongTinNguoiBenhDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thongTinNguoiBenhRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ThongTinNguoiBenhDTO result = thongTinNguoiBenhService.update(thongTinNguoiBenhDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thongTinNguoiBenhDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /thong-tin-nguoi-benhs/:id} : Partial updates given fields of an existing thongTinNguoiBenh, field will ignore if it is null
     *
     * @param id the id of the thongTinNguoiBenhDTO to save.
     * @param thongTinNguoiBenhDTO the thongTinNguoiBenhDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thongTinNguoiBenhDTO,
     * or with status {@code 400 (Bad Request)} if the thongTinNguoiBenhDTO is not valid,
     * or with status {@code 404 (Not Found)} if the thongTinNguoiBenhDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the thongTinNguoiBenhDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/thong-tin-nguoi-benhs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ThongTinNguoiBenhDTO> partialUpdateThongTinNguoiBenh(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThongTinNguoiBenhDTO thongTinNguoiBenhDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ThongTinNguoiBenh partially : {}, {}", id, thongTinNguoiBenhDTO);
        if (thongTinNguoiBenhDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thongTinNguoiBenhDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thongTinNguoiBenhRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThongTinNguoiBenhDTO> result = thongTinNguoiBenhService.partialUpdate(thongTinNguoiBenhDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thongTinNguoiBenhDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /thong-tin-nguoi-benhs} : get all the thongTinNguoiBenhs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thongTinNguoiBenhs in body.
     */
    @GetMapping("/thong-tin-nguoi-benhs")
    public ResponseEntity<List<ThongTinNguoiBenhDTO>> getAllThongTinNguoiBenhs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ThongTinNguoiBenhs");
        Page<ThongTinNguoiBenhDTO> page = thongTinNguoiBenhService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /thong-tin-nguoi-benhs/:id} : get the "id" thongTinNguoiBenh.
     *
     * @param id the id of the thongTinNguoiBenhDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thongTinNguoiBenhDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thong-tin-nguoi-benhs/{id}")
    public ResponseEntity<ThongTinNguoiBenhDTO> getThongTinNguoiBenh(@PathVariable Long id) {
        log.debug("REST request to get ThongTinNguoiBenh : {}", id);
        Optional<ThongTinNguoiBenhDTO> thongTinNguoiBenhDTO = thongTinNguoiBenhService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thongTinNguoiBenhDTO);
    }

    /**
     * {@code DELETE  /thong-tin-nguoi-benhs/:id} : delete the "id" thongTinNguoiBenh.
     *
     * @param id the id of the thongTinNguoiBenhDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thong-tin-nguoi-benhs/{id}")
    public ResponseEntity<Void> deleteThongTinNguoiBenh(@PathVariable Long id) {
        log.debug("REST request to delete ThongTinNguoiBenh : {}", id);
        thongTinNguoiBenhService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
