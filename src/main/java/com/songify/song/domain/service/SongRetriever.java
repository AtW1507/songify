package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class SongRetriever {

    private final SongRepository songRepository;

    public SongRetriever(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<SongEntity> findAll(){
        log.info("retrieving all songs: ");
        return songRepository.findAll();
    }

    public List<SongEntity> findAllLimitedBy(Integer limit){
            return songRepository.findAll()
                    .stream()
                    .limit(limit)
                    .toList();


    }

    public SongEntity findSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(()-> new SongNotFoundException("Song with id: " + id + " not found"));
    }

    public void existById(Long id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }


    }



}
