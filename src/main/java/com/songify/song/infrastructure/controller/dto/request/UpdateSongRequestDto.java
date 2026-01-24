package com.songify.song.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateSongRequestDto(

        @NotNull(message = "Song name cannot be null")
        @NotEmpty(message = "Song name cannot be empty")
        String songName,

        @NotNull(message = "Artist name cannot be null")
        @NotEmpty(message = "Artist name cannot be empty")
        String artist) {
}
