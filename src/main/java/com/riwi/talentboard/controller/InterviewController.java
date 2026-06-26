
package com.riwi.talentboard.controller;

import com.riwi.talentboard.dto.InterviewRequestDTO;
import com.riwi.talentboard.dto.InterviewResponseDTO;
import com.riwi.talentboard.service.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    // Registrar/Programar una nueva entrevista
    @PostMapping
    public ResponseEntity<InterviewResponseDTO> scheduleInterview(@Valid @RequestBody InterviewRequestDTO request) {
        InterviewResponseDTO response = interviewService.schedule(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InterviewResponseDTO>> getAllInterviews() {
        return ResponseEntity.ok(interviewService.getAll());
    }

    // Obtener detalle de una entrevista específica
    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponseDTO> getInterviewById(@PathVariable Long id) {
        return ResponseEntity.ok(interviewService.getById(id));
    }

    // Consultar todas las entrevistas de una postulación (historial del candidato)
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<InterviewResponseDTO>> getInterviewsByApplication(@PathVariable Long applicationId) {
        return ResponseEntity.ok(interviewService.getByApplicationId(applicationId));
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<InterviewResponseDTO>> getInterviewsByCandidate(@PathVariable Long candidateId) {
        return ResponseEntity.ok(interviewService.getByCandidateId(candidateId));
    }

    // Registrar el resultado y feedback de la entrevista una vez finalizada
    @PatchMapping("/{id}/result")
    public ResponseEntity<InterviewResponseDTO> updateInterviewResult(
            @PathVariable Long id,
            @RequestParam String result,
            @RequestParam(required = false) String notes) {
        return ResponseEntity.ok(interviewService.updateResult(id, result, notes));
    }
}