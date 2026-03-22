package com.songify.domain.crud;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SongAssigner {

    private final AlbumRetriever albumRetriever;
    private final SongRetriever songRetriever;

    AlbumDto addSongToAlbum(final Long albumId, final Long songId){
        Album album = albumRetriever.findById(albumId);
        Song song = songRetriever.findSongById(songId);
        album.addSongToAlbum(song);
        return new AlbumDto(
                album.getId(),
                album.getTitle(),
                album.getSongsIds()
        );

    }
}
