package com.riwi.talentboard.controller;

import com.riwi.talentboard.dto.ApplicationRequestDTO;
import com.riwi.talentboard.dto.ApplicationResponseDTO;
import com.riwi.talentboard.enums.ApplicationStatus;
import com.riwi.talentboard.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;


    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> applyToVacancy(@Valid @RequestBody ApplicationRequestDTO request) {
        ApplicationResponseDTO response = applicationService.apply(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getById(id));
    }


    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsByCandidate(@PathVariable Long candidateId) {
        return ResponseEntity.ok(applicationService.getByCandidateId(candidateId));
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponseDTO> changeApplicationStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        return ResponseEntity.ok(applicationService.changeStatus(id, status));
    }
}
