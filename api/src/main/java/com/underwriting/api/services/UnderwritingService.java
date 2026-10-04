package com.underwriting.api.services;

import com.underwriting.api.dto.*;
import com.underwriting.api.models.RiskLevel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UnderwritingService {

    private final Map<String, AssessmentResponse> assessments =
            new ConcurrentHashMap<>();

    public AssessmentResponse assess(AssessmentRequest request) {
        PropertyDetailsRequest property = request.propertyDetails();

        int score = 10;
        List<String> risks = new ArrayList<>();
        List<String> protections = new ArrayList<>();

        if (property.yearBuilt() < 2000) {
            score += 15;
            risks.add("Older property construction");
        }

        if (property.roofAge() > 15) {
            score += 20;
            risks.add("Aging roof");
        }

        if (property.previousClaims() > 1) {
            score += 20;
            risks.add("Multiple previous claims");
        }

        if (property.swimmingPool()) {
            score += 10;
            risks.add("Swimming pool present");
        }

        if (property.trampoline()) {
            score += 15;
            risks.add("Trampoline present");
        }

        if (property.fireProtection()) {
            score -= 10;
            protections.add("Fire protection available");
        }

        if (property.securitySystem()) {
            score -= 5;
            protections.add("Security system installed");
        }

        score = Math.max(0, Math.min(score, 100));

        RiskLevel level;
        if (score < 30) {
            level = RiskLevel.LOW;
        } else if (score < 60) {
            level = RiskLevel.MEDIUM;
        } else {
            level = RiskLevel.HIGH;
        }

        String recommendation = switch (level) {
            case LOW -> "Proceed with standard review";
            case MEDIUM -> "Conduct additional risk review";
            case HIGH -> "Refer for manual underwriting review";
        };

        String id = UUID.randomUUID().toString();
        System.out.println(property);

        AssessmentResponse response = new AssessmentResponse(
                id,
                request.applicantName(),
                request.applicantEmail(),
                property.propertyType(),
                score,
                level,
                List.copyOf(risks),
                List.copyOf(protections),
                recommendation,
                LocalDateTime.now()
        );

        assessments.put(id, response);
        return response;
    }

    public List<AssessmentResponse> getAll() {
        return assessments.values().stream()
                .sorted(Comparator.comparing(
                        AssessmentResponse::assessedAt).reversed())
                .toList();
    }

    public Optional<AssessmentResponse> getById(String id) {
        return Optional.ofNullable(assessments.get(id));
    }
}
