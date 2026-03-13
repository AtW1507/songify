package com.songify.infrastructure.crud.album;

import org.springframework.http.HttpStatus;

public record ErrorAlbumsResponseDto(String message, HttpStatus status) {
}
