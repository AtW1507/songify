package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SongifyCrudFacadeTest {

    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfiguration.createSongifyCrudFacade(
            new InmemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository()
    );


    @Test
    @DisplayName("Should add artist 'amigo' with id:0 When amigo was sent")
    public void should_add_artist_amigo_with_id_zero_when_amigo_was_sent() {
        // given
        ArtistRequestDto artist = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertTrue(allArtists.isEmpty());
        // when
        ArtistDto response = songifyCrudFacade.addArtist(artist);
        // then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("amigo");
        int size = songifyCrudFacade.findAllArtists(Pageable.unpaged()).size();
        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("Should add artist 'shawn menes' with id:0 When shawn menes was sent")
    public void should_add_artist_shawn_menes_with_id_zero_when_shawn_menes_was_sent() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        // when
        ArtistDto response = songifyCrudFacade.addArtist(shawnMendes);
        // then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("shawn mendes");
    }

    @Test
    @DisplayName("Should throw exception ArtistNotFound When id was: 0")
    public void should_throw_exception_artist_not_found_when_id_was_one() {
        // given
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(0L));
        //then
        assertThat(throwable).isInstanceOf(ArtistNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("artist with id: " + 0 + " not found");
    }

    @Test
    @DisplayName("Should delete artist by id When he have no album")
    public void should_delete_artist_by_id_when_he_have_no_album() {
        //given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(shawnMendes).id();
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId)).isEmpty();
        //when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        //then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();

    }

    @Test
    @DisplayName("Should delete artist with album and songs by id When artist had one album and he was only artist in album")
    public void should_delete_artist_with_album_and_songs_by_id_when_artist_had_one_album_and_he_was_only_artist_in_album() {
        //given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(shawnMendes).id();
        SongRequestDto song = SongRequestDto.builder()
                .name("song")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        Long songId = songDto.id();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .songIds(Set.of(songId))
                .title("album 1 ")
                .build());
        Long albumId = albumDto.id();
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId)).size().isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistByAlbumId(albumId)).isEqualTo(1);
        //when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        //then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongDtoById(songId));
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Song with id: 0 not found");
        Throwable throwable2 = catchThrowable(() -> songifyCrudFacade.findAlbumById(albumId));
        assertThat(throwable2).isInstanceOf(AlbumNotFoundException.class);
        assertThat(throwable2.getMessage()).isEqualTo("Album with id 0 not found");
    }

    @Test
    public void should_add_album_with_song() {
        //given
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(songRequestDto);
        AlbumRequestDto album = AlbumRequestDto
                .builder()
                .songIds(Set.of(songDto.id()))
                .title("album title 1")
                .build();
        assertThat(songifyCrudFacade.finAllAlbums()).isEmpty();
        //when
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album);
        //then
        assertThat(songifyCrudFacade.finAllAlbums()).isNotEmpty();
        AlbumInfo albumWithSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id());
        Set<AlbumInfo.SongInfo> songs = albumWithSongs.getSongs();
        assertTrue(songs.stream().anyMatch(song -> song.getId().equals(songDto.id())));
    }

    @Test
    @DisplayName("Should add song")
    public void should_add_song() {
        //given
        SongRequestDto song = SongRequestDto.builder()
                .name("song")
                .language(SongLanguageDto.ENGLISH)
                .build();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        //when
        SongDto songDto = songifyCrudFacade.addSong(song);
        //then
        List<SongDto> allSongs = songifyCrudFacade.findAllSongs(Pageable.unpaged());
        assertThat(allSongs)
                .extracting(SongDto::id)
                .containsExactly(0L);
    }

    @Test
    @DisplayName("should add artist to album")
    public void should_add_artist_to_album() {
        //given
        ArtistRequestDto artist = ArtistRequestDto.builder()
                .name("artist")
                .build();
        SongRequestDto song = SongRequestDto.builder()
                .name("song")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        AlbumRequestDto album = AlbumRequestDto.builder()
                .title("album 1")
                .songIds(Set.of(songDto.id()))
                .build();
        assertThat(songifyCrudFacade.finAllAlbums()).isEmpty();
        ArtistDto artistDto = songifyCrudFacade.addArtist(artist);
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album);
        //when
        songifyCrudFacade.addArtistToAlbum(artistDto.id(), albumDto.id());
        //then
        Set<AlbumDto> albumsByArtistId = songifyCrudFacade.findAlbumsByArtistId(artistDto.id());
        assertThat(albumsByArtistId).isNotEmpty();
        assertThat(albumsByArtistId)
                .extracting(AlbumDto::id)
                .containsExactly(albumDto.id());
    }

    @Test
    public void should_return_album_by_id() {
        //given
        SongRequestDto song = SongRequestDto.builder()
                .name("song")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        AlbumDto album = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .songIds(Set.of(songDto.id()))
                .title("album 1")
                .build());
        Long albumId = album.id();
        //when
        AlbumDto albumById = songifyCrudFacade.findAlbumById(albumId);
        //then
        assertThat(albumById).isEqualTo(new AlbumDto(albumId, "album 1", Set.of(songDto.id())));
    }

    @Test
    public void should_throw_exception_when_album_not_found_by_id() {
        //given

        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findAlbumById(0L));
        //then
        assertThat(throwable).isInstanceOf(AlbumNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Album with id 0 not found");
    }

    @Test
    public void should_throw_exception_when_song_not_found_by_id() {
        //given
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongDtoById(1L));
        //then
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Song with id: 1 not found");
    }

    @Test
    @DisplayName("Should delete only artist from album by id When there were more than 1 artist in album")
    public void should_delete_only_artist_from_album_by_id_when_there_were_more_than_one_artist_in_album() {
        //given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long shawnMendesId = songifyCrudFacade.addArtist(shawnMendes).id();
        ArtistRequestDto linkinPark = ArtistRequestDto.builder()
                .name("linking park")
                .build();
        Long linkinParkId = songifyCrudFacade.addArtist(linkinPark).id();

        SongRequestDto song = SongRequestDto.builder()
                .name("song")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        Long songId = songDto.id();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .songIds(Set.of(songId))
                .title("album 1 ")
                .build());
        Long albumId = albumDto.id();
        songifyCrudFacade.addArtistToAlbum(shawnMendesId, albumId);
        songifyCrudFacade.addArtistToAlbum(linkinParkId, albumId);
        assertThat(songifyCrudFacade.countArtistByAlbumId(albumId)).isEqualTo(2);
        //when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(shawnMendesId);
        //then
        assertThat(songifyCrudFacade.countArtistByAlbumId(albumId)).isEqualTo(1);
        AlbumInfo album = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        Set<AlbumInfo.ArtistInfo> artists = album.getArtists();
        assertThat(artists)
                .extracting("id")
                .containsOnly(linkinParkId);
    }

    @Test
    @DisplayName("Should delete artist with all albums and all songs by id When artist was than only artist in albums")
    public void should_delete_artist_with_albums_and_songs_by_id_when_artist_was_than_only_artist_in_album() {
        //given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long shawnMendesId = songifyCrudFacade.addArtist(shawnMendes).id();
        SongRequestDto song = SongRequestDto.builder()
                .name("song")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongRequestDto song1 = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongRequestDto song2 = SongRequestDto.builder()
                .name("song2")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongRequestDto song3 = SongRequestDto.builder()
                .name("song3")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        SongDto song1Dto = songifyCrudFacade.addSong(song1);
        SongDto song2Dto = songifyCrudFacade.addSong(song2);
        SongDto song3Dto = songifyCrudFacade.addSong(song3);
        Long songId = songDto.id();
        Long song1Id = song1Dto.id();
        Long song2Id = song2Dto.id();
        Long song3Id = song3Dto.id();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .songIds(Set.of(songId, song1Id))
                .title("album 1 ")
                .build());

        AlbumDto album2Dto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .songIds(Set.of(song2Id, song3Id))
                .title("album 2 ")
                .build());
        Long albumId = albumDto.id();
        Long album2Id = album2Dto.id();
        songifyCrudFacade.addArtistToAlbum(shawnMendesId, albumId);
        songifyCrudFacade.addArtistToAlbum(shawnMendesId, album2Id);
        assertThat(songifyCrudFacade.countArtistByAlbumId(albumId)).isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistByAlbumId(album2Id)).isEqualTo(1);
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).size().isEqualTo(1);
        assertThat(songifyCrudFacade.finAllAlbums()).size().isEqualTo(2);
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).size().isEqualTo(4);
        //when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(shawnMendesId);

        //then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.finAllAlbums()).isEmpty();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAlbumsByArtistId(shawnMendesId)).isEmpty();
    }

    @Test
    @DisplayName("should retrieve song with genre")
    public void should_retrieve_song() {
        // given
        SongRequestDto song = SongRequestDto.builder()
                .name("song")
                .language(SongLanguageDto.ENGLISH)
                .build();
        songifyCrudFacade.addSong(song);
        // when
        SongDto songDto = songifyCrudFacade.findSongDtoById(0L);
        // then
        assertThat(songDto.name()).isEqualTo("song");
        assertThat(songDto.id()).isEqualTo(0L);
        assertThat(songDto.genre().name()).isEqualTo("default");
        assertThat(songDto.genre().id()).isEqualTo(1L);


    }

    @Test
    @DisplayName("should_add_genre")
    public void should_add_genre() {
         //given

        //when
        GenreDto genreDto = songifyCrudFacade.addGenre(new GenreRequestDto("Rock"));
        //then
        assertThat(genreDto.name()).isEqualTo("Rock");
        assertThat(genreDto.id()).isEqualTo(2L);



    }

    @Test
    @DisplayName("should return all genres")
    public void should_return_all_genres() {
        // we have "default" genre in db.
        //given
        GenreDto genre1 = songifyCrudFacade.addGenre(new GenreRequestDto("Rock"));
        GenreDto genre2 = songifyCrudFacade.addGenre(new GenreRequestDto("Pop"));
        //when
        Set<GenreDto> allGenres = songifyCrudFacade.retrieveGenres();
        //then
        assertThat(allGenres.size()).isEqualTo(3);
        assertThat(allGenres.stream().map(GenreDto::name)).containsExactlyInAnyOrder("default", "Rock", "Pop");


    }
}