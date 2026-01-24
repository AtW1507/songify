package com.songify.song.infrastructure.controller;

import com.songify.song.domain.service.SongAdder;
import com.songify.song.domain.service.SongRetriever;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.model.SongEntity;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/songs")
public class SongRestController {


    private final SongAdder songAdder;

    public SongRestController(SongAdder songAdder, SongRetriever songRetriewer) {
        this.songAdder = songAdder;
        this.songRetriewer = songRetriewer;
    }

    private final SongRetriever songRetriewer;


    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {

        Map<Integer, SongEntity> allSongs = songRetriewer.findAll();

        if (limit != null) {
            Map<Integer, SongEntity> dataBaseLimit = songRetriewer.findAllLimitedBy(limit);
            GetAllSongsResponseDto response = new GetAllSongsResponseDto(dataBaseLimit);
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = SongMapper.mapFromSongToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Integer id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        Map<Integer, SongEntity> allSongs = songRetriewer.findAll();
        if (!allSongs.containsKey(id)) {

            throw new SongNotFoundException("Song with id: " + id + " not found");

        }
        SongEntity song = allSongs.get(id);
        GetSongResponseDto response = SongMapper.mapFromSongToGetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        SongEntity song = SongMapper.mapFromCreateSongRequestDtoToSong(request);
        songAdder.addSong(song);
        CreateSongResponseDto body = SongMapper.mapFromSongToCreateSongResponseDto(song);
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongIdUsingPathVariable(@PathVariable Integer id) {
        Map<Integer, SongEntity> allSongs = songRetriewer.findAll();
        if (!allSongs.containsKey(id)) {

            throw new SongNotFoundException("Song with id: " + id + " not found");

        }
        allSongs.remove(id);
        DeleteSongResponseDto body = SongMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Integer id, @RequestBody @Valid UpdateSongRequestDto request) {
        Map<Integer, SongEntity> allSongs = songRetriewer.findAll();
        if (!allSongs.containsKey(id)) {

            throw new SongNotFoundException("Song with id: " + id + " not found");

        }
        SongEntity newSong = SongMapper.mapFromUpdateSongRequestDtoToSong(request);

        SongEntity oldSonge = allSongs.put(id, newSong);
        log.info("Updated song with id: " + id +
                " with oldSongName: " + oldSonge.name() +
                " to newSongName: " + newSong.name() + " oldArtist: " + oldSonge.artist() +
                " to newArtist: " + newSong.artist());

        UpdateSongResponseDto body = SongMapper.mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Integer id,
                                                                              @RequestBody PartiallyUpdateSongRequestDto request) {

        Map<Integer, SongEntity> allSongs = songRetriewer.findAll();
        if (!allSongs.containsKey(id)) {

            throw new SongNotFoundException("Song with id: " + id + " not found");

        }
        SongEntity songFromDataBase = allSongs.get(id);
        SongEntity updatedSong = SongMapper.mapFromPartiallyUpdateSongRequestDtoToSong(request);
        SongEntity.SongEntityBuilder builder = SongEntity.builder();
        if (request.songName() != null) {
            builder.name(request.songName());
        } else {
            builder.name(songFromDataBase.name());
        }
        if (request.artist() != null) {
            builder.artist(request.artist());
        } else {
            builder.artist(songFromDataBase.artist());
        }


        allSongs.put(id, updatedSong);
        PartiallyUpdateSongResponseDto body = SongMapper.mapFromSongToPartiallyUpdateSongResponseDto(updatedSong);
        return ResponseEntity.ok(body);


    }
}

