package com.songify.infrastructure.crud.song.controller.dto.response;


import com.songify.infrastructure.crud.song.controller.dto.SongDtoForJson;

import java.util.List;


public record GetAllSongsResponseDto(List<SongDtoForJson> songs) {
}
