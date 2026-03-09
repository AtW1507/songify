package com.songify.domain.crud;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
class AlbumAdder {

    private final SongRetriever songRetriever;
    private final AlbumRepository albumRepository;

    AlbumDto addAlbum(final Set<Long> songIds, String title, final Instant instant){
//        Song song = songRetriever.findSongById(songId);
        Set<Song> songs = songIds.stream()
                .map(songRetriever::findSongById)
                .collect(Collectors.toSet());
        //todo refactor write songRetriever.finAllSongsByIs(songIsd)
        Album album = new Album();
        album.setTitle(title);
        album.addSongsToAlbum(songs);
        album.setReleaseDate(instant);
        Album saveAlbum = albumRepository.save(album);
        return new AlbumDto(saveAlbum.getId(), saveAlbum.getTitle());
    }
    Album addAlbum(String title, final Instant instant){
        Album album = new Album();
        album.setTitle(title);
        album.setReleaseDate(instant);
        return albumRepository.save(album);

    }
}
