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
    public static  SongDto mapFromSongToSongDto(SongEntity song) {
        return new SongDto(song.getId(), song.getName(), song.getArtist());

    }
    public static @NonNull CreateSongResponseDto mapFromSongToCreateSongResponseDto(SongEntity song) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(song);
        return new CreateSongResponseDto(songDto);
    }
    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK);
    }
    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(SongEntity newsong) {
        return new UpdateSongResponseDto(newsong.getName(), newsong.getArtist());
    }
    public static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(SongEntity song) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(song);
        return new PartiallyUpdateSongResponseDto(songDto);
    }
    public static GetSongResponseDto mapFromSongToGetSongResponseDto(SongEntity song) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(song);
        return new GetSongResponseDto(songDto);
    }
    public static SongEntity mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto dto){
        return new SongEntity(dto.songName(), dto.artist());
    }
    public static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(List<SongEntity> songs){
        List<SongDto> songDtos = songs.stream().map(SongMapper::mapFromSongToSongDto).toList();
        return new GetAllSongsResponseDto(songDtos);
    }
    public static SongEntity mapFromUpdateSongRequestDtoToSong(UpdateSongRequestDto dto){
        return new SongEntity(dto.songName(), dto.artist());
    }

}
