package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ThongTinNguoiBenh;
import com.mycompany.myapp.repository.ThongTinNguoiBenhRepository;
import com.mycompany.myapp.service.ThongTinNguoiBenhService;
import com.mycompany.myapp.service.dto.ThongTinNguoiBenhDTO;
import com.mycompany.myapp.service.mapper.ThongTinNguoiBenhMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ThongTinNguoiBenh}.
 */
@Service
@Transactional
public class ThongTinNguoiBenhServiceImpl implements ThongTinNguoiBenhService {

    private final Logger log = LoggerFactory.getLogger(ThongTinNguoiBenhServiceImpl.class);

    private final ThongTinNguoiBenhRepository thongTinNguoiBenhRepository;

    private final ThongTinNguoiBenhMapper thongTinNguoiBenhMapper;

    public ThongTinNguoiBenhServiceImpl(
        ThongTinNguoiBenhRepository thongTinNguoiBenhRepository,
        ThongTinNguoiBenhMapper thongTinNguoiBenhMapper
    ) {
        this.thongTinNguoiBenhRepository = thongTinNguoiBenhRepository;
        this.thongTinNguoiBenhMapper = thongTinNguoiBenhMapper;
    }

    @Override
    public ThongTinNguoiBenhDTO save(ThongTinNguoiBenhDTO thongTinNguoiBenhDTO) {
        log.debug("Request to save ThongTinNguoiBenh : {}", thongTinNguoiBenhDTO);
        ThongTinNguoiBenh thongTinNguoiBenh = thongTinNguoiBenhMapper.toEntity(thongTinNguoiBenhDTO);
        thongTinNguoiBenh = thongTinNguoiBenhRepository.save(thongTinNguoiBenh);
        return thongTinNguoiBenhMapper.toDto(thongTinNguoiBenh);
    }

    @Override
    public ThongTinNguoiBenhDTO update(ThongTinNguoiBenhDTO thongTinNguoiBenhDTO) {
        log.debug("Request to save ThongTinNguoiBenh : {}", thongTinNguoiBenhDTO);
        ThongTinNguoiBenh thongTinNguoiBenh = thongTinNguoiBenhMapper.toEntity(thongTinNguoiBenhDTO);
        thongTinNguoiBenh = thongTinNguoiBenhRepository.save(thongTinNguoiBenh);
        return thongTinNguoiBenhMapper.toDto(thongTinNguoiBenh);
    }

    @Override
    public Optional<ThongTinNguoiBenhDTO> partialUpdate(ThongTinNguoiBenhDTO thongTinNguoiBenhDTO) {
        log.debug("Request to partially update ThongTinNguoiBenh : {}", thongTinNguoiBenhDTO);

        return thongTinNguoiBenhRepository
            .findById(thongTinNguoiBenhDTO.getId())
            .map(existingThongTinNguoiBenh -> {
                thongTinNguoiBenhMapper.partialUpdate(existingThongTinNguoiBenh, thongTinNguoiBenhDTO);

                return existingThongTinNguoiBenh;
            })
            .map(thongTinNguoiBenhRepository::save)
            .map(thongTinNguoiBenhMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThongTinNguoiBenhDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ThongTinNguoiBenhs");
        return thongTinNguoiBenhRepository.findAll(pageable).map(thongTinNguoiBenhMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThongTinNguoiBenhDTO> findOne(Long id) {
        log.debug("Request to get ThongTinNguoiBenh : {}", id);
        return thongTinNguoiBenhRepository.findById(id).map(thongTinNguoiBenhMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThongTinNguoiBenh : {}", id);
        thongTinNguoiBenhRepository.deleteById(id);
    }
}
