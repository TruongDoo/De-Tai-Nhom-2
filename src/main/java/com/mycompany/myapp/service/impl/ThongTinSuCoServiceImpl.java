package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ThongTinSuCo;
import com.mycompany.myapp.repository.ThongTinSuCoRepository;
import com.mycompany.myapp.service.ThongTinSuCoService;
import com.mycompany.myapp.service.dto.ThongTinSuCoDTO;
import com.mycompany.myapp.service.mapper.ThongTinSuCoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ThongTinSuCo}.
 */
@Service
@Transactional
public class ThongTinSuCoServiceImpl implements ThongTinSuCoService {

    private final Logger log = LoggerFactory.getLogger(ThongTinSuCoServiceImpl.class);

    private final ThongTinSuCoRepository thongTinSuCoRepository;

    private final ThongTinSuCoMapper thongTinSuCoMapper;

    public ThongTinSuCoServiceImpl(ThongTinSuCoRepository thongTinSuCoRepository, ThongTinSuCoMapper thongTinSuCoMapper) {
        this.thongTinSuCoRepository = thongTinSuCoRepository;
        this.thongTinSuCoMapper = thongTinSuCoMapper;
    }

    @Override
    public ThongTinSuCoDTO save(ThongTinSuCoDTO thongTinSuCoDTO) {
        log.debug("Request to save ThongTinSuCo : {}", thongTinSuCoDTO);
        ThongTinSuCo thongTinSuCo = thongTinSuCoMapper.toEntity(thongTinSuCoDTO);
        thongTinSuCo = thongTinSuCoRepository.save(thongTinSuCo);
        return thongTinSuCoMapper.toDto(thongTinSuCo);
    }

    @Override
    public ThongTinSuCoDTO update(ThongTinSuCoDTO thongTinSuCoDTO) {
        log.debug("Request to save ThongTinSuCo : {}", thongTinSuCoDTO);
        ThongTinSuCo thongTinSuCo = thongTinSuCoMapper.toEntity(thongTinSuCoDTO);
        thongTinSuCo = thongTinSuCoRepository.save(thongTinSuCo);
        return thongTinSuCoMapper.toDto(thongTinSuCo);
    }

    @Override
    public Optional<ThongTinSuCoDTO> partialUpdate(ThongTinSuCoDTO thongTinSuCoDTO) {
        log.debug("Request to partially update ThongTinSuCo : {}", thongTinSuCoDTO);

        return thongTinSuCoRepository
            .findById(thongTinSuCoDTO.getId())
            .map(existingThongTinSuCo -> {
                thongTinSuCoMapper.partialUpdate(existingThongTinSuCo, thongTinSuCoDTO);

                return existingThongTinSuCo;
            })
            .map(thongTinSuCoRepository::save)
            .map(thongTinSuCoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThongTinSuCoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ThongTinSuCos");
        return thongTinSuCoRepository.findAll(pageable).map(thongTinSuCoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThongTinSuCoDTO> findOne(Long id) {
        log.debug("Request to get ThongTinSuCo : {}", id);
        return thongTinSuCoRepository.findById(id).map(thongTinSuCoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThongTinSuCo : {}", id);
        thongTinSuCoRepository.deleteById(id);
    }
}
