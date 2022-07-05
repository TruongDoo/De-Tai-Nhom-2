package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PhuLucRepository;
import com.mycompany.myapp.service.PhuLucService;
import com.mycompany.myapp.service.dto.PhuLucDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PhuLuc}.
 */
@RestController
@RequestMapping("/api")
public class PhuLucResource {

    private final Logger log = LoggerFactory.getLogger(PhuLucResource.class);

    private static final String ENTITY_NAME = "phuLuc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhuLucService phuLucService;

    private final PhuLucRepository phuLucRepository;

    public PhuLucResource(PhuLucService phuLucService, PhuLucRepository phuLucRepository) {
        this.phuLucService = phuLucService;
        this.phuLucRepository = phuLucRepository;
    }

    /**
     * {@code POST  /phu-lucs} : Create a new phuLuc.
     *
     * @param phuLucDTO the phuLucDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phuLucDTO, or with status {@code 400 (Bad Request)} if the phuLuc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phu-lucs")
    public ResponseEntity<PhuLucDTO> createPhuLuc(@Valid @RequestBody PhuLucDTO phuLucDTO) throws URISyntaxException {
        log.debug("REST request to save PhuLuc : {}", phuLucDTO);
        if (phuLucDTO.getId() != null) {
            throw new BadRequestAlertException("A new phuLuc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhuLucDTO result = phuLucService.save(phuLucDTO);
        return ResponseEntity
            .created(new URI("/api/phu-lucs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phu-lucs/:id} : Updates an existing phuLuc.
     *
     * @param id the id of the phuLucDTO to save.
     * @param phuLucDTO the phuLucDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phuLucDTO,
     * or with status {@code 400 (Bad Request)} if the phuLucDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phuLucDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phu-lucs/{id}")
    public ResponseEntity<PhuLucDTO> updatePhuLuc(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PhuLucDTO phuLucDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PhuLuc : {}, {}", id, phuLucDTO);
        if (phuLucDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phuLucDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phuLucRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PhuLucDTO result = phuLucService.update(phuLucDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phuLucDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /phu-lucs/:id} : Partial updates given fields of an existing phuLuc, field will ignore if it is null
     *
     * @param id the id of the phuLucDTO to save.
     * @param phuLucDTO the phuLucDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phuLucDTO,
     * or with status {@code 400 (Bad Request)} if the phuLucDTO is not valid,
     * or with status {@code 404 (Not Found)} if the phuLucDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the phuLucDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/phu-lucs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PhuLucDTO> partialUpdatePhuLuc(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PhuLucDTO phuLucDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PhuLuc partially : {}, {}", id, phuLucDTO);
        if (phuLucDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phuLucDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phuLucRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PhuLucDTO> result = phuLucService.partialUpdate(phuLucDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phuLucDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /phu-lucs} : get all the phuLucs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phuLucs in body.
     */
    @GetMapping("/phu-lucs")
    public ResponseEntity<List<PhuLucDTO>> getAllPhuLucs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PhuLucs");
        Page<PhuLucDTO> page = phuLucService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phu-lucs/:id} : get the "id" phuLuc.
     *
     * @param id the id of the phuLucDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phuLucDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phu-lucs/{id}")
    public ResponseEntity<PhuLucDTO> getPhuLuc(@PathVariable Long id) {
        log.debug("REST request to get PhuLuc : {}", id);
        Optional<PhuLucDTO> phuLucDTO = phuLucService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phuLucDTO);
    }

    /**
     * {@code DELETE  /phu-lucs/:id} : delete the "id" phuLuc.
     *
     * @param id the id of the phuLucDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phu-lucs/{id}")
    public ResponseEntity<Void> deletePhuLuc(@PathVariable Long id) {
        log.debug("REST request to delete PhuLuc : {}", id);
        phuLucService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
