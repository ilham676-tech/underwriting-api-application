package com.underwriting.api.dto;

import jakarta.validation.constraints.*;

public record PropertyDetailsRequest(
        @NotBlank String propertyType,
        @NotBlank String location,
        @Min(1800) @Max(2026) int yearBuilt,
        @Positive double propertyValue,
        @Min(0) int roofAge,
        @Min(0) int previousClaims,
        boolean fireProtection,
        boolean securitySystem,
        boolean swimmingPool,
        boolean trampoline
) {}
