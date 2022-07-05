package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.HoSo;
import com.mycompany.myapp.repository.HoSoRepository;
import com.mycompany.myapp.service.HoSoService;
import com.mycompany.myapp.service.dto.HoSoDTO;
import com.mycompany.myapp.service.mapper.HoSoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HoSo}.
 */
@Service
@Transactional
public class HoSoServiceImpl implements HoSoService {

    private final Logger log = LoggerFactory.getLogger(HoSoServiceImpl.class);

    private final HoSoRepository hoSoRepository;

    private final HoSoMapper hoSoMapper;

    public HoSoServiceImpl(HoSoRepository hoSoRepository, HoSoMapper hoSoMapper) {
        this.hoSoRepository = hoSoRepository;
        this.hoSoMapper = hoSoMapper;
    }

    @Override
    public HoSoDTO save(HoSoDTO hoSoDTO) {
        log.debug("Request to save HoSo : {}", hoSoDTO);
        HoSo hoSo = hoSoMapper.toEntity(hoSoDTO);
        hoSo = hoSoRepository.save(hoSo);
        return hoSoMapper.toDto(hoSo);
    }

    @Override
    public HoSoDTO update(HoSoDTO hoSoDTO) {
        log.debug("Request to save HoSo : {}", hoSoDTO);
        HoSo hoSo = hoSoMapper.toEntity(hoSoDTO);
        hoSo = hoSoRepository.save(hoSo);
        return hoSoMapper.toDto(hoSo);
    }

    @Override
    public Optional<HoSoDTO> partialUpdate(HoSoDTO hoSoDTO) {
        log.debug("Request to partially update HoSo : {}", hoSoDTO);

        return hoSoRepository
            .findById(hoSoDTO.getId())
            .map(existingHoSo -> {
                hoSoMapper.partialUpdate(existingHoSo, hoSoDTO);

                return existingHoSo;
            })
            .map(hoSoRepository::save)
            .map(hoSoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HoSoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HoSos");
        return hoSoRepository.findAll(pageable).map(hoSoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HoSoDTO> findOne(Long id) {
        log.debug("Request to get HoSo : {}", id);
        return hoSoRepository.findById(id).map(hoSoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HoSo : {}", id);
        hoSoRepository.deleteById(id);
    }
}
