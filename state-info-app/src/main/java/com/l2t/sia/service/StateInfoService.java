package com.l2t.sia.service;

import com.l2t.sia.dto.StateInfoDto;

import java.util.List;

public interface StateInfoService {

    StateInfoDto create(StateInfoDto dto);

    StateInfoDto getById(Integer id);

    List<StateInfoDto> getAll();

    StateInfoDto update(Integer id, StateInfoDto dto);

    void delete(Integer id);

    List<StateInfoDto> createBulk(List<StateInfoDto> dtos);
}
