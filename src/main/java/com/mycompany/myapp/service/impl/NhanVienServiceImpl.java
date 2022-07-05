package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.NhanVien;
import com.mycompany.myapp.repository.NhanVienRepository;
import com.mycompany.myapp.service.NhanVienService;
import com.mycompany.myapp.service.dto.NhanVienDTO;
import com.mycompany.myapp.service.mapper.NhanVienMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NhanVien}.
 */
@Service
@Transactional
public class NhanVienServiceImpl implements NhanVienService {

    private final Logger log = LoggerFactory.getLogger(NhanVienServiceImpl.class);

    private final NhanVienRepository nhanVienRepository;

    private final NhanVienMapper nhanVienMapper;

    public NhanVienServiceImpl(NhanVienRepository nhanVienRepository, NhanVienMapper nhanVienMapper) {
        this.nhanVienRepository = nhanVienRepository;
        this.nhanVienMapper = nhanVienMapper;
    }

    @Override
    public NhanVienDTO save(NhanVienDTO nhanVienDTO) {
        log.debug("Request to save NhanVien : {}", nhanVienDTO);
        NhanVien nhanVien = nhanVienMapper.toEntity(nhanVienDTO);
        nhanVien = nhanVienRepository.save(nhanVien);
        return nhanVienMapper.toDto(nhanVien);
    }

    @Override
    public NhanVienDTO update(NhanVienDTO nhanVienDTO) {
        log.debug("Request to save NhanVien : {}", nhanVienDTO);
        NhanVien nhanVien = nhanVienMapper.toEntity(nhanVienDTO);
        nhanVien = nhanVienRepository.save(nhanVien);
        return nhanVienMapper.toDto(nhanVien);
    }

    @Override
    public Optional<NhanVienDTO> partialUpdate(NhanVienDTO nhanVienDTO) {
        log.debug("Request to partially update NhanVien : {}", nhanVienDTO);

        return nhanVienRepository
            .findById(nhanVienDTO.getId())
            .map(existingNhanVien -> {
                nhanVienMapper.partialUpdate(existingNhanVien, nhanVienDTO);

                return existingNhanVien;
            })
            .map(nhanVienRepository::save)
            .map(nhanVienMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NhanVienDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NhanViens");
        return nhanVienRepository.findAll(pageable).map(nhanVienMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NhanVienDTO> findOne(Long id) {
        log.debug("Request to get NhanVien : {}", id);
        return nhanVienRepository.findById(id).map(nhanVienMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NhanVien : {}", id);
        nhanVienRepository.deleteById(id);
    }
}
