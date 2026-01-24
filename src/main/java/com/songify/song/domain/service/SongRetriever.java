package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.repository.SongRepository;
import com.songify.song.infrastructure.controller.dto.response.GetAllSongsResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
public class SongRetriever {

    private final SongRepository songRepository;

    public SongRetriever(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Map<Integer, SongEntity> findAll(){
        log.info("retrieving all songs: ");
        return songRepository.findAll();
    }

    public Map<Integer, SongEntity> findAllLimitedBy(Integer limit){
            return songRepository.findAll().entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


    }
}
