package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.QuanLySuCoYKhoa;
import com.mycompany.myapp.repository.QuanLySuCoYKhoaRepository;
import com.mycompany.myapp.service.QuanLySuCoYKhoaService;
import com.mycompany.myapp.service.dto.QuanLySuCoYKhoaDTO;
import com.mycompany.myapp.service.mapper.QuanLySuCoYKhoaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QuanLySuCoYKhoa}.
 */
@Service
@Transactional
public class QuanLySuCoYKhoaServiceImpl implements QuanLySuCoYKhoaService {

    private final Logger log = LoggerFactory.getLogger(QuanLySuCoYKhoaServiceImpl.class);

    private final QuanLySuCoYKhoaRepository quanLySuCoYKhoaRepository;

    private final QuanLySuCoYKhoaMapper quanLySuCoYKhoaMapper;

    public QuanLySuCoYKhoaServiceImpl(QuanLySuCoYKhoaRepository quanLySuCoYKhoaRepository, QuanLySuCoYKhoaMapper quanLySuCoYKhoaMapper) {
        this.quanLySuCoYKhoaRepository = quanLySuCoYKhoaRepository;
        this.quanLySuCoYKhoaMapper = quanLySuCoYKhoaMapper;
    }

    @Override
    public QuanLySuCoYKhoaDTO save(QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO) {
        log.debug("Request to save QuanLySuCoYKhoa : {}", quanLySuCoYKhoaDTO);
        QuanLySuCoYKhoa quanLySuCoYKhoa = quanLySuCoYKhoaMapper.toEntity(quanLySuCoYKhoaDTO);
        quanLySuCoYKhoa = quanLySuCoYKhoaRepository.save(quanLySuCoYKhoa);
        return quanLySuCoYKhoaMapper.toDto(quanLySuCoYKhoa);
    }

    @Override
    public QuanLySuCoYKhoaDTO update(QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO) {
        log.debug("Request to save QuanLySuCoYKhoa : {}", quanLySuCoYKhoaDTO);
        QuanLySuCoYKhoa quanLySuCoYKhoa = quanLySuCoYKhoaMapper.toEntity(quanLySuCoYKhoaDTO);
        quanLySuCoYKhoa = quanLySuCoYKhoaRepository.save(quanLySuCoYKhoa);
        return quanLySuCoYKhoaMapper.toDto(quanLySuCoYKhoa);
    }

    @Override
    public Optional<QuanLySuCoYKhoaDTO> partialUpdate(QuanLySuCoYKhoaDTO quanLySuCoYKhoaDTO) {
        log.debug("Request to partially update QuanLySuCoYKhoa : {}", quanLySuCoYKhoaDTO);

        return quanLySuCoYKhoaRepository
            .findById(quanLySuCoYKhoaDTO.getId())
            .map(existingQuanLySuCoYKhoa -> {
                quanLySuCoYKhoaMapper.partialUpdate(existingQuanLySuCoYKhoa, quanLySuCoYKhoaDTO);

                return existingQuanLySuCoYKhoa;
            })
            .map(quanLySuCoYKhoaRepository::save)
            .map(quanLySuCoYKhoaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuanLySuCoYKhoaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuanLySuCoYKhoas");
        return quanLySuCoYKhoaRepository.findAll(pageable).map(quanLySuCoYKhoaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuanLySuCoYKhoaDTO> findOne(Long id) {
        log.debug("Request to get QuanLySuCoYKhoa : {}", id);
        return quanLySuCoYKhoaRepository.findById(id).map(quanLySuCoYKhoaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuanLySuCoYKhoa : {}", id);
        quanLySuCoYKhoaRepository.deleteById(id);
    }
}
