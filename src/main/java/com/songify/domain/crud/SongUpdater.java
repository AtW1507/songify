package com.songify.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@Transactional
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongUpdater {

    private final SongRepository songRepository;
    private final SongRetriever songRetriever;


    void updateById(Long id, Song newSong) {
        songRetriever.existById(id);
        songRepository.updateById(id, newSong);

    }

    Song updatePartiallyById(Long id, Song songFromRequest) {
        Song songFromDataBase = songRetriever.findSongByDtoId(id);
        Song.SongBuilder builder = Song.builder();
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
        Song toSave = builder.build();
        updateById(id, toSave);
        return toSave;

    }


}
