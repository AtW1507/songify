package com.songify.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@Transactional
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongDeleter {

    private final SongRepository songRepository;
    private final SongRetriever songRetriever;
    private final GenreDeleter genreDeleter;


    void deleteById(Long id) {
        log.info("Song deleted with id: " + id);
        songRepository.deleteById(id);
    }

//    void deleteSongAndGenreById(Long songId) {
//        Song songById = songRetriever.findSongById(songId);
//        Long genreId = songById.getGenre().getId();
//
//        deleteById(songId);
//        genreDeleter.deleteById(genreId);
//    }
}
