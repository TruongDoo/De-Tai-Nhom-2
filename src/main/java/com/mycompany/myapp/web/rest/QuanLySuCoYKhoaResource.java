package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.QuanLySuCoYKhoaRepository;
import com.mycompany.myapp.service.QuanLySuCoYKhoaService;
import com.mycompany.myapp.service.dto.QuanLySuCoYKhoaDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.QuanLySuCoYKhoa}.
 */
@RestController
@RequestMapping("/api")
public class QuanLySuCoYKhoaResource {

    private final Logger log = LoggerFactory.getLogger(QuanLySuCoYKhoaResource.class);

    private static final String ENTITY_NAME = "quanLySuCoYKhoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuanLySuCoYKhoaService quanLySuCoYKhoaService;

    private final QuanLySuCoYKhoaRepository quanLySuCoYKhoaRepository;

    public QuanLySuCoYKhoaResource(QuanLySuCoYKhoaService quanLySuCoYKhoaService, QuanLySuCoYKhoaRepository quanLySuCoYKhoaRepository) {
        this.quanLySuCoYKhoaService = quanLySuCoYKhoaService;
        this.quanLySuCoYKhoaRepository = quanLySuCoYKhoaRepository;
    }

    /**
     * {@code POST  /quan-ly-su-co-y-khoas} : Create a new quanLySuCoYKhoa.
     *
     * @param quanLySuCoYKhoaDTO the quanLySuCoYKhoaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quanLySuCoYKhoaDTO, or with status {@code 400 (Bad Request)} if the quanLySuCoYKhoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quan-ly-su-co-y-khoas")
    public ResponseEntity<QuanLySuCoYKhoaDTO> createQuanLySuCoYKhoa(@Valid @RequestBody QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO)
        throws URISyntaxException {
        log.debug("REST request to save QuanLySuCoYKhoa : {}", quanLySuCoYKhoaDTO);
        if (quanLySuCoYKhoaDTO.getId() != null) {
            throw new BadRequestAlertException("A new quanLySuCoYKhoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuanLySuCoYKhoaDTO result = quanLySuCoYKhoaService.save(quanLySuCoYKhoaDTO);
        return ResponseEntity
            .created(new URI("/api/quan-ly-su-co-y-khoas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quan-ly-su-co-y-khoas/:id} : Updates an existing quanLySuCoYKhoa.
     *
     * @param id the id of the quanLySuCoYKhoaDTO to save.
     * @param quanLySuCoYKhoaDTO the quanLySuCoYKhoaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quanLySuCoYKhoaDTO,
     * or with status {@code 400 (Bad Request)} if the quanLySuCoYKhoaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quanLySuCoYKhoaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quan-ly-su-co-y-khoas/{id}")
    public ResponseEntity<QuanLySuCoYKhoaDTO> updateQuanLySuCoYKhoa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update QuanLySuCoYKhoa : {}, {}", id, quanLySuCoYKhoaDTO);
        if (quanLySuCoYKhoaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quanLySuCoYKhoaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quanLySuCoYKhoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuanLySuCoYKhoaDTO result = quanLySuCoYKhoaService.update(quanLySuCoYKhoaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quanLySuCoYKhoaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /quan-ly-su-co-y-khoas/:id} : Partial updates given fields of an existing quanLySuCoYKhoa, field will ignore if it is null
     *
     * @param id the id of the quanLySuCoYKhoaDTO to save.
     * @param quanLySuCoYKhoaDTO the quanLySuCoYKhoaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quanLySuCoYKhoaDTO,
     * or with status {@code 400 (Bad Request)} if the quanLySuCoYKhoaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the quanLySuCoYKhoaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the quanLySuCoYKhoaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/quan-ly-su-co-y-khoas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QuanLySuCoYKhoaDTO> partialUpdateQuanLySuCoYKhoa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update QuanLySuCoYKhoa partially : {}, {}", id, quanLySuCoYKhoaDTO);
        if (quanLySuCoYKhoaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quanLySuCoYKhoaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quanLySuCoYKhoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuanLySuCoYKhoaDTO> result = quanLySuCoYKhoaService.partialUpdate(quanLySuCoYKhoaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quanLySuCoYKhoaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /quan-ly-su-co-y-khoas} : get all the quanLySuCoYKhoas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quanLySuCoYKhoas in body.
     */
    @GetMapping("/quan-ly-su-co-y-khoas")
    public ResponseEntity<List<QuanLySuCoYKhoaDTO>> getAllQuanLySuCoYKhoas(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of QuanLySuCoYKhoas");
        Page<QuanLySuCoYKhoaDTO> page = quanLySuCoYKhoaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quan-ly-su-co-y-khoas/:id} : get the "id" quanLySuCoYKhoa.
     *
     * @param id the id of the quanLySuCoYKhoaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quanLySuCoYKhoaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quan-ly-su-co-y-khoas/{id}")
    public ResponseEntity<QuanLySuCoYKhoaDTO> getQuanLySuCoYKhoa(@PathVariable Long id) {
        log.debug("REST request to get QuanLySuCoYKhoa : {}", id);
        Optional<QuanLySuCoYKhoaDTO> quanLySuCoYKhoaDTO = quanLySuCoYKhoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quanLySuCoYKhoaDTO);
    }

    /**
     * {@code DELETE  /quan-ly-su-co-y-khoas/:id} : delete the "id" quanLySuCoYKhoa.
     *
     * @param id the id of the quanLySuCoYKhoaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quan-ly-su-co-y-khoas/{id}")
    public ResponseEntity<Void> deleteQuanLySuCoYKhoa(@PathVariable Long id) {
        log.debug("REST request to delete QuanLySuCoYKhoa : {}", id);
        quanLySuCoYKhoaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
