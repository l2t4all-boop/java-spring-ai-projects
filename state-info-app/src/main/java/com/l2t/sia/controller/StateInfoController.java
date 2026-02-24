package com.l2t.sia.controller;

import com.l2t.sia.dto.StateInfoDto;
import com.l2t.sia.service.StateInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/state-info")
@RequiredArgsConstructor
public class StateInfoController {

    private final StateInfoService stateInfoService;

    @PostMapping
    public ResponseEntity<StateInfoDto> create(@RequestBody StateInfoDto dto) {
        StateInfoDto created = stateInfoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateInfoDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(stateInfoService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<StateInfoDto>> getAll() {
        return ResponseEntity.ok(stateInfoService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateInfoDto> update(@PathVariable Integer id, @RequestBody StateInfoDto dto) {
        return ResponseEntity.ok(stateInfoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        stateInfoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<StateInfoDto>> createBulk(@RequestBody List<StateInfoDto> dtos) {
        List<StateInfoDto> created = stateInfoService.createBulk(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
