package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ArtistAssigner {

    private final ArtistRepository artistRepository;
    private final AlbumRetriever albumRetriever;


    void addArtistToAlbum(final Long artistId, final Long albumId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException(artistId.toString()));
        Album album = albumRetriever.findById(albumId);
        artist.addAlbum(album);
    }
}
