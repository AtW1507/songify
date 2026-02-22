package com.songify.infrastructure.crud.song.controller.dto;

import lombok.Builder;

@Builder
public record SongDtoForJson(
        Long id,
        String name
) {


}
