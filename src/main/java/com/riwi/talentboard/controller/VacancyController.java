package com.riwi.talentboard.controller;

import com.riwi.talentboard.dto.VacancyRequestDTO;
import com.riwi.talentboard.dto.VacancyResponseDTO;
import com.riwi.talentboard.enums.VacancyStatus;
import com.riwi.talentboard.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping
    public ResponseEntity<VacancyResponseDTO> createVacancy(@Valid @RequestBody VacancyRequestDTO request) {
        VacancyResponseDTO response = vacancyService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VacancyResponseDTO>> getAllVacancies() {
        return ResponseEntity.ok(vacancyService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacancyResponseDTO> getVacancyById(@PathVariable Long id) {
        return ResponseEntity.ok(vacancyService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacancyResponseDTO> updateVacancy(
            @PathVariable Long id,
            @Valid @RequestBody VacancyRequestDTO request) {
        return ResponseEntity.ok(vacancyService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<VacancyResponseDTO> changeVacancyStatus(
            @PathVariable Long id,
            @RequestParam VacancyStatus status) {
        return ResponseEntity.ok(vacancyService.changeStatus(id, status));
    }
}