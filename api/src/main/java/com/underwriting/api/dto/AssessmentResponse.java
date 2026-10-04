package com.underwriting.api.dto;

import com.underwriting.api.models.RiskLevel;
import java.time.LocalDateTime;
import java.util.List;

public record AssessmentResponse(
        String applicationId,
        String applicantName,
        String applicantEmail,
        String propertyType,
        int riskScore,
        RiskLevel riskLevel,
        List<String> riskFactors,
        List<String> protectiveFactors,
        String recommendation,
        LocalDateTime assessedAt
) {}
