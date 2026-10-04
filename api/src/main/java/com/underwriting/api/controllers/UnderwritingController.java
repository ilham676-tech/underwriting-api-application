package com.underwriting.api.controllers;

import com.underwriting.api.dto.*;
import com.underwriting.api.services.UnderwritingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/underwriting/assessments")
@CrossOrigin(origins = "http://localhost:4200")
public class UnderwritingController {

    private final UnderwritingService service;

    public UnderwritingController(UnderwritingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AssessmentResponse> assess(
            @Valid @RequestBody AssessmentRequest request) {
        return ResponseEntity.ok(service.assess(request));
    }

    @GetMapping
    public ResponseEntity<List<AssessmentResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentResponse> getById(
            @PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
