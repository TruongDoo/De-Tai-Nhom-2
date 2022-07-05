package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.HoSoRepository;
import com.mycompany.myapp.service.HoSoService;
import com.mycompany.myapp.service.dto.HoSoDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.HoSo}.
 */
@RestController
@RequestMapping("/api")
public class HoSoResource {

    private final Logger log = LoggerFactory.getLogger(HoSoResource.class);

    private static final String ENTITY_NAME = "hoSo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoSoService hoSoService;

    private final HoSoRepository hoSoRepository;

    public HoSoResource(HoSoService hoSoService, HoSoRepository hoSoRepository) {
        this.hoSoService = hoSoService;
        this.hoSoRepository = hoSoRepository;
    }

    /**
     * {@code POST  /ho-sos} : Create a new hoSo.
     *
     * @param hoSoDTO the hoSoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hoSoDTO, or with status {@code 400 (Bad Request)} if the hoSo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ho-sos")
    public ResponseEntity<HoSoDTO> createHoSo(@RequestBody HoSoDTO hoSoDTO) throws URISyntaxException {
        log.debug("REST request to save HoSo : {}", hoSoDTO);
        if (hoSoDTO.getId() != null) {
            throw new BadRequestAlertException("A new hoSo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HoSoDTO result = hoSoService.save(hoSoDTO);
        return ResponseEntity
            .created(new URI("/api/ho-sos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ho-sos/:id} : Updates an existing hoSo.
     *
     * @param id the id of the hoSoDTO to save.
     * @param hoSoDTO the hoSoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoSoDTO,
     * or with status {@code 400 (Bad Request)} if the hoSoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hoSoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ho-sos/{id}")
    public ResponseEntity<HoSoDTO> updateHoSo(@PathVariable(value = "id", required = false) final Long id, @RequestBody HoSoDTO hoSoDTO)
        throws URISyntaxException {
        log.debug("REST request to update HoSo : {}, {}", id, hoSoDTO);
        if (hoSoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hoSoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hoSoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HoSoDTO result = hoSoService.update(hoSoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hoSoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ho-sos/:id} : Partial updates given fields of an existing hoSo, field will ignore if it is null
     *
     * @param id the id of the hoSoDTO to save.
     * @param hoSoDTO the hoSoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoSoDTO,
     * or with status {@code 400 (Bad Request)} if the hoSoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hoSoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hoSoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ho-sos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HoSoDTO> partialUpdateHoSo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HoSoDTO hoSoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HoSo partially : {}, {}", id, hoSoDTO);
        if (hoSoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hoSoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hoSoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HoSoDTO> result = hoSoService.partialUpdate(hoSoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hoSoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ho-sos} : get all the hoSos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hoSos in body.
     */
    @GetMapping("/ho-sos")
    public ResponseEntity<List<HoSoDTO>> getAllHoSos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of HoSos");
        Page<HoSoDTO> page = hoSoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ho-sos/:id} : get the "id" hoSo.
     *
     * @param id the id of the hoSoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hoSoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ho-sos/{id}")
    public ResponseEntity<HoSoDTO> getHoSo(@PathVariable Long id) {
        log.debug("REST request to get HoSo : {}", id);
        Optional<HoSoDTO> hoSoDTO = hoSoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hoSoDTO);
    }

    /**
     * {@code DELETE  /ho-sos/:id} : delete the "id" hoSo.
     *
     * @param id the id of the hoSoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ho-sos/{id}")
    public ResponseEntity<Void> deleteHoSo(@PathVariable Long id) {
        log.debug("REST request to delete HoSo : {}", id);
        hoSoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
