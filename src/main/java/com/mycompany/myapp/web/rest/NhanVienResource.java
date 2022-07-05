package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.NhanVienRepository;
import com.mycompany.myapp.service.NhanVienService;
import com.mycompany.myapp.service.dto.NhanVienDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.NhanVien}.
 */
@RestController
@RequestMapping("/api")
public class NhanVienResource {

    private final Logger log = LoggerFactory.getLogger(NhanVienResource.class);

    private static final String ENTITY_NAME = "nhanVien";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NhanVienService nhanVienService;

    private final NhanVienRepository nhanVienRepository;

    public NhanVienResource(NhanVienService nhanVienService, NhanVienRepository nhanVienRepository) {
        this.nhanVienService = nhanVienService;
        this.nhanVienRepository = nhanVienRepository;
    }

    /**
     * {@code POST  /nhan-viens} : Create a new nhanVien.
     *
     * @param nhanVienDTO the nhanVienDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nhanVienDTO, or with status {@code 400 (Bad Request)} if the nhanVien has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nhan-viens")
    public ResponseEntity<NhanVienDTO> createNhanVien(@RequestBody NhanVienDTO nhanVienDTO) throws URISyntaxException {
        log.debug("REST request to save NhanVien : {}", nhanVienDTO);
        if (nhanVienDTO.getId() != null) {
            throw new BadRequestAlertException("A new nhanVien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhanVienDTO result = nhanVienService.save(nhanVienDTO);
        return ResponseEntity
            .created(new URI("/api/nhan-viens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nhan-viens/:id} : Updates an existing nhanVien.
     *
     * @param id the id of the nhanVienDTO to save.
     * @param nhanVienDTO the nhanVienDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhanVienDTO,
     * or with status {@code 400 (Bad Request)} if the nhanVienDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nhanVienDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nhan-viens/{id}")
    public ResponseEntity<NhanVienDTO> updateNhanVien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NhanVienDTO nhanVienDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NhanVien : {}, {}", id, nhanVienDTO);
        if (nhanVienDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhanVienDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhanVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NhanVienDTO result = nhanVienService.update(nhanVienDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhanVienDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nhan-viens/:id} : Partial updates given fields of an existing nhanVien, field will ignore if it is null
     *
     * @param id the id of the nhanVienDTO to save.
     * @param nhanVienDTO the nhanVienDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhanVienDTO,
     * or with status {@code 400 (Bad Request)} if the nhanVienDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nhanVienDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nhanVienDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nhan-viens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NhanVienDTO> partialUpdateNhanVien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NhanVienDTO nhanVienDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NhanVien partially : {}, {}", id, nhanVienDTO);
        if (nhanVienDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhanVienDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhanVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NhanVienDTO> result = nhanVienService.partialUpdate(nhanVienDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhanVienDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nhan-viens} : get all the nhanViens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nhanViens in body.
     */
    @GetMapping("/nhan-viens")
    public ResponseEntity<List<NhanVienDTO>> getAllNhanViens(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of NhanViens");
        Page<NhanVienDTO> page = nhanVienService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nhan-viens/:id} : get the "id" nhanVien.
     *
     * @param id the id of the nhanVienDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nhanVienDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nhan-viens/{id}")
    public ResponseEntity<NhanVienDTO> getNhanVien(@PathVariable Long id) {
        log.debug("REST request to get NhanVien : {}", id);
        Optional<NhanVienDTO> nhanVienDTO = nhanVienService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhanVienDTO);
    }

    /**
     * {@code DELETE  /nhan-viens/:id} : delete the "id" nhanVien.
     *
     * @param id the id of the nhanVienDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nhan-viens/{id}")
    public ResponseEntity<Void> deleteNhanVien(@PathVariable Long id) {
        log.debug("REST request to delete NhanVien : {}", id);
        nhanVienService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
