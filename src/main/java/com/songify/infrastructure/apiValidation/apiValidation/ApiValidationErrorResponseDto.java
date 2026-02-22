package com.songify.infrastructure.apiValidation.apiValidation;

import org.springframework.http.HttpStatus;

import java.util.List;

record ApiValidationErrorResponseDto(List<String> errors, HttpStatus status) {
}
