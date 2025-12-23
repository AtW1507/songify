package com.songify;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import java.util.Map;
import java.util.stream.Collectors;
@Log4j2
@RestController
public class SongsController {
    Map<Integer, String> dataBase = new HashMap<>();

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs (@RequestParam(required = false) Integer limit) {
        dataBase.put(1, "Song 1");
        dataBase.put(2, "Song 2");
        dataBase.put(3, "Song 3");
        dataBase.put(4, "Song 4");
        if(limit != null) {
            Map<Integer, String> dataBaseLimit = dataBase.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            SongResponseDto response = new SongResponseDto(dataBaseLimit);
            return ResponseEntity.ok(response);
        }
        SongResponseDto response = new SongResponseDto(dataBase);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongById (@PathVariable Integer id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        String song = dataBase.get(id);
        if(song == null) {
            return ResponseEntity.notFound().build();
        }
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

}

