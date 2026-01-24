package com.songify.song.infrastructure.controller.error;

import org.springframework.http.HttpStatus;

public record ErrorSongResponseDTO(String message, HttpStatus status) {
}
