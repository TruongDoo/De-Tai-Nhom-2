package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.PhuLuc;
import com.mycompany.myapp.repository.PhuLucRepository;
import com.mycompany.myapp.service.PhuLucService;
import com.mycompany.myapp.service.dto.PhuLucDTO;
import com.mycompany.myapp.service.mapper.PhuLucMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PhuLuc}.
 */
@Service
@Transactional
public class PhuLucServiceImpl implements PhuLucService {

    private final Logger log = LoggerFactory.getLogger(PhuLucServiceImpl.class);

    private final PhuLucRepository phuLucRepository;

    private final PhuLucMapper phuLucMapper;

    public PhuLucServiceImpl(PhuLucRepository phuLucRepository, PhuLucMapper phuLucMapper) {
        this.phuLucRepository = phuLucRepository;
        this.phuLucMapper = phuLucMapper;
    }

    @Override
    public PhuLucDTO save(PhuLucDTO phuLucDTO) {
        log.debug("Request to save PhuLuc : {}", phuLucDTO);
        PhuLuc phuLuc = phuLucMapper.toEntity(phuLucDTO);
        phuLuc = phuLucRepository.save(phuLuc);
        return phuLucMapper.toDto(phuLuc);
    }

    @Override
    public PhuLucDTO update(PhuLucDTO phuLucDTO) {
        log.debug("Request to save PhuLuc : {}", phuLucDTO);
        PhuLuc phuLuc = phuLucMapper.toEntity(phuLucDTO);
        phuLuc = phuLucRepository.save(phuLuc);
        return phuLucMapper.toDto(phuLuc);
    }

    @Override
    public Optional<PhuLucDTO> partialUpdate(PhuLucDTO phuLucDTO) {
        log.debug("Request to partially update PhuLuc : {}", phuLucDTO);

        return phuLucRepository
            .findById(phuLucDTO.getId())
            .map(existingPhuLuc -> {
                phuLucMapper.partialUpdate(existingPhuLuc, phuLucDTO);

                return existingPhuLuc;
            })
            .map(phuLucRepository::save)
            .map(phuLucMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhuLucDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PhuLucs");
        return phuLucRepository.findAll(pageable).map(phuLucMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PhuLucDTO> findOne(Long id) {
        log.debug("Request to get PhuLuc : {}", id);
        return phuLucRepository.findById(id).map(phuLucMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PhuLuc : {}", id);
        phuLucRepository.deleteById(id);
    }
}
