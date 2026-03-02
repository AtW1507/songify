package com.songify.infrastructure.crud.song.controller.dto;

import com.songify.domain.crud.dto.GenreDto;
import lombok.Builder;

@Builder
public record SongDtoForJson(
        Long id,
        String name,
        GenreDto genre
) {


}
