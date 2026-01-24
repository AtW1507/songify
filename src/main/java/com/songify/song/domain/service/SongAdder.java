package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SongAdder {

    public SongAdder(final SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    private final SongRepository songRepository;

    public SongEntity addSong(SongEntity song) {
        log.info("Song added: " + song);
        songRepository.saveToDatabase(song);

        return song;
    }


}
