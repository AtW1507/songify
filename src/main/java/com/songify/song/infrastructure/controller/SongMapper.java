package com.songify.song.infrastructure.controller;

import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.domain.model.SongEntity;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class SongMapper {

    public static @NonNull SongEntity mapFromCreateSongRequestDtoToSong(CreateSongRequestDto dto) {
        SongEntity song = new SongEntity(dto.songName(), dto.artist());
        return song;
    }
    public static @NonNull CreateSongResponseDto mapFromSongToCreateSongResponseDto(SongEntity song) {
        CreateSongResponseDto body = new CreateSongResponseDto(song);
        return body;
    }
    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK);
    }
    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(SongEntity newsong) {
        return new UpdateSongResponseDto(newsong.getName(), newsong.getArtist());
    }
    public static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(SongEntity updateSong) {
        return new PartiallyUpdateSongResponseDto(updateSong);
    }
    public static GetSongResponseDto mapFromSongToGetSongResponseDto(SongEntity song) {
        return new GetSongResponseDto(song);
    }
    public static SongEntity mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto dto){
        return new SongEntity(dto.songName(), dto.artist());
    }
    public static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(List<SongEntity> database){
        return new GetAllSongsResponseDto(database);
    }
    public static SongEntity mapFromUpdateSongRequestDtoToSong(UpdateSongRequestDto dto){
        return new SongEntity(dto.songName(), dto.artist());
    }

}
