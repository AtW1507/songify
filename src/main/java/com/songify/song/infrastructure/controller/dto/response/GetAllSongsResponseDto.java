package com.songify.song.infrastructure.controller.dto.response;


import com.songify.song.domain.model.SongEntity;

import java.util.List;
import java.util.Map;

public record GetAllSongsResponseDto(List<SongEntity> songs) {
}
