package com.underwriting.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssessmentRequest(
        @NotBlank String applicantName,
        @NotBlank @Email String applicantEmail,
        @NotNull @Valid PropertyDetailsRequest propertyDetails
) {}
