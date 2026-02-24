package com.songify.domain.crud;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
@Transactional
class AlbumAdder {

    private final SongRetriever songRetriever;
    private final AlbumRepository albumRepository;

    AlbumDto addAlbum(final Long songId, String title, final Instant instant){
        Song song = songRetriever.findSongById(songId);
        Album album = new Album();
        album.setTitle(title);
        album.addSongToAlbum(song);
        album.setReleaseDate(instant);
        Album saveAlbum = albumRepository.save(album);
        return new AlbumDto(saveAlbum.getId(), saveAlbum.getTitle());
    }
}
