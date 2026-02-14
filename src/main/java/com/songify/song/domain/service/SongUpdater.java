package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.repository.SongRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@Transactional
public class SongUpdater {

    private final SongRepository songRepository;
    private final SongRetriever songRetriever;

    public SongUpdater(SongRepository songRepository, SongRetriever songRetriever) {
        this.songRepository = songRepository;
        this.songRetriever = songRetriever;
    }


    public void updateById(Long id, SongEntity newSong) {
        songRetriever.existById(id);
        songRepository.updateById(id, newSong);

    }

        public SongEntity updatePartiallyById(Long id, SongEntity songFromRequest) {
            SongEntity songFromDataBase = songRetriever.findSongById(id);
            SongEntity.SongEntityBuilder builder = SongEntity.builder();
            if (songFromRequest.getName() != null) {
                builder.name(songFromRequest.getName());
            } else {
                builder.name(songFromDataBase.getName());
            }
            if (songFromRequest.getArtist() != null) {
                builder.artist(songFromRequest.getArtist());
            } else {
                builder.artist(songFromDataBase.getArtist());
            }
            SongEntity toSave = builder.build();
            updateById(id, toSave);
            return toSave;

        }



}
