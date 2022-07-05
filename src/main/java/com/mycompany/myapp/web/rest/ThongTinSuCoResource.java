package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ThongTinSuCoRepository;
import com.mycompany.myapp.service.ThongTinSuCoService;
import com.mycompany.myapp.service.dto.ThongTinSuCoDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ThongTinSuCo}.
 */
@RestController
@RequestMapping("/api")
public class ThongTinSuCoResource {

    private final Logger log = LoggerFactory.getLogger(ThongTinSuCoResource.class);

    private static final String ENTITY_NAME = "thongTinSuCo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThongTinSuCoService thongTinSuCoService;

    private final ThongTinSuCoRepository thongTinSuCoRepository;

    public ThongTinSuCoResource(ThongTinSuCoService thongTinSuCoService, ThongTinSuCoRepository thongTinSuCoRepository) {
        this.thongTinSuCoService = thongTinSuCoService;
        this.thongTinSuCoRepository = thongTinSuCoRepository;
    }

    /**
     * {@code POST  /thong-tin-su-cos} : Create a new thongTinSuCo.
     *
     * @param thongTinSuCoDTO the thongTinSuCoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thongTinSuCoDTO, or with status {@code 400 (Bad Request)} if the thongTinSuCo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thong-tin-su-cos")
    public ResponseEntity<ThongTinSuCoDTO> createThongTinSuCo(@RequestBody ThongTinSuCoDTO thongTinSuCoDTO) throws URISyntaxException {
        log.debug("REST request to save ThongTinSuCo : {}", thongTinSuCoDTO);
        if (thongTinSuCoDTO.getId() != null) {
            throw new BadRequestAlertException("A new thongTinSuCo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThongTinSuCoDTO result = thongTinSuCoService.save(thongTinSuCoDTO);
        return ResponseEntity
            .created(new URI("/api/thong-tin-su-cos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thong-tin-su-cos/:id} : Updates an existing thongTinSuCo.
     *
     * @param id the id of the thongTinSuCoDTO to save.
     * @param thongTinSuCoDTO the thongTinSuCoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thongTinSuCoDTO,
     * or with status {@code 400 (Bad Request)} if the thongTinSuCoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thongTinSuCoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thong-tin-su-cos/{id}")
    public ResponseEntity<ThongTinSuCoDTO> updateThongTinSuCo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThongTinSuCoDTO thongTinSuCoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ThongTinSuCo : {}, {}", id, thongTinSuCoDTO);
        if (thongTinSuCoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thongTinSuCoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thongTinSuCoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ThongTinSuCoDTO result = thongTinSuCoService.update(thongTinSuCoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thongTinSuCoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /thong-tin-su-cos/:id} : Partial updates given fields of an existing thongTinSuCo, field will ignore if it is null
     *
     * @param id the id of the thongTinSuCoDTO to save.
     * @param thongTinSuCoDTO the thongTinSuCoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thongTinSuCoDTO,
     * or with status {@code 400 (Bad Request)} if the thongTinSuCoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the thongTinSuCoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the thongTinSuCoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/thong-tin-su-cos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ThongTinSuCoDTO> partialUpdateThongTinSuCo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThongTinSuCoDTO thongTinSuCoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ThongTinSuCo partially : {}, {}", id, thongTinSuCoDTO);
        if (thongTinSuCoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thongTinSuCoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thongTinSuCoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThongTinSuCoDTO> result = thongTinSuCoService.partialUpdate(thongTinSuCoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thongTinSuCoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /thong-tin-su-cos} : get all the thongTinSuCos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thongTinSuCos in body.
     */
    @GetMapping("/thong-tin-su-cos")
    public ResponseEntity<List<ThongTinSuCoDTO>> getAllThongTinSuCos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ThongTinSuCos");
        Page<ThongTinSuCoDTO> page = thongTinSuCoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /thong-tin-su-cos/:id} : get the "id" thongTinSuCo.
     *
     * @param id the id of the thongTinSuCoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thongTinSuCoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thong-tin-su-cos/{id}")
    public ResponseEntity<ThongTinSuCoDTO> getThongTinSuCo(@PathVariable Long id) {
        log.debug("REST request to get ThongTinSuCo : {}", id);
        Optional<ThongTinSuCoDTO> thongTinSuCoDTO = thongTinSuCoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thongTinSuCoDTO);
    }

    /**
     * {@code DELETE  /thong-tin-su-cos/:id} : delete the "id" thongTinSuCo.
     *
     * @param id the id of the thongTinSuCoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thong-tin-su-cos/{id}")
    public ResponseEntity<Void> deleteThongTinSuCo(@PathVariable Long id) {
        log.debug("REST request to delete ThongTinSuCo : {}", id);
        thongTinSuCoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
