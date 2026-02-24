package com.l2t.sia.service;

import com.l2t.sia.domain.StateInfo;
import com.l2t.sia.dto.StateInfoDto;
import com.l2t.sia.exception.StateInfoAlreadyExistsException;
import com.l2t.sia.exception.StateInfoNotFoundException;
import com.l2t.sia.repository.StateInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StateInfoServiceImpl implements StateInfoService {

    private final StateInfoRepository repository;

    @Override
    @Transactional
    public StateInfoDto create(StateInfoDto dto) {
        log.info("Creating StateInfo with id: {}", dto.getId());
        if (repository.existsById(dto.getId())) {
            throw new StateInfoAlreadyExistsException(dto.getId());
        }
        StateInfo saved = repository.save(toEntity(dto));
        log.info("Created StateInfo with id: {}", saved.getId());
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StateInfoDto getById(Integer id) {
        log.info("Fetching StateInfo with id: {}", id);
        StateInfo entity = repository.findById(id)
                .orElseThrow(() -> new StateInfoNotFoundException(id));
        return toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StateInfoDto> getAll() {
        log.info("Fetching all StateInfo records");
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional
    public StateInfoDto update(Integer id, StateInfoDto dto) {
        log.info("Updating StateInfo with id: {}", id);
        StateInfo entity = repository.findById(id)
                .orElseThrow(() -> new StateInfoNotFoundException(id));
        entity.setState(dto.getState());
        entity.setCapital(dto.getCapital());
        entity.setRegion(dto.getRegion());
        entity.setContent(dto.getContent());
        StateInfo saved = repository.save(entity);
        log.info("Updated StateInfo with id: {}", saved.getId());
        return toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("Deleting StateInfo with id: {}", id);
        if (!repository.existsById(id)) {
            throw new StateInfoNotFoundException(id);
        }
        repository.deleteById(id);
        log.info("Deleted StateInfo with id: {}", id);
    }

    @Override
    @Transactional
    public List<StateInfoDto> createBulk(List<StateInfoDto> dtos) {
        log.info("Bulk creating {} StateInfo records", dtos.size());
        List<StateInfo> entities = dtos.stream()
                .map(this::toEntity)
                .toList();
        List<StateInfo> saved = repository.saveAll(entities);
        log.info("Bulk created {} StateInfo records", saved.size());
        return saved.stream()
                .map(this::toDto)
                .toList();
    }

    private StateInfo toEntity(StateInfoDto dto) {
        return new StateInfo(
                dto.getId(),
                dto.getState(),
                dto.getCapital(),
                dto.getRegion(),
                dto.getContent()
        );
    }

    private StateInfoDto toDto(StateInfo entity) {
        return new StateInfoDto(
                entity.getId(),
                entity.getState(),
                entity.getCapital(),
                entity.getRegion(),
                entity.getContent()
        );
    }
}
