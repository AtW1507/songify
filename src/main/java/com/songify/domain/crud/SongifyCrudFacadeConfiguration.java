package com.songify.domain.crud;

class SongifyCrudFacadeConfiguration {

    public static SongifyCrudFacade createSongifyCrudFacade(final SongRepository songRepository,
                                                            final GenreRepository genreRepository,
                                                            final ArtistRepository artistRepository,
                                                            final AlbumRepository albumRepository) {
        SongRetriever songRetriever = new SongRetriever(songRepository);
        SongUpdater songUpdater = new SongUpdater(songRepository, songRetriever);
        GenreRetriever genreRetriever = new GenreRetriever(genreRepository);
        GenreAssigner genreAssigner = new GenreAssigner(songRetriever, genreRetriever);
        SongAdder songAdder = new SongAdder(songRepository, genreAssigner);
        AlbumAdder albumAdder = new AlbumAdder(songRetriever,albumRepository);
        AlbumRetriever albumRetriever = new AlbumRetriever(albumRepository);
        GenreDeleter genreDeleter = new GenreDeleter(genreRepository);
        SongDeleter songDeleter = new SongDeleter(songRepository, songRetriever, genreDeleter);
        ArtistAdder artistAdder = new ArtistAdder(artistRepository, albumAdder,songAdder);
        GenreAdder genreAdder = new GenreAdder(genreRepository);
        AlbumDeleter albumDeleter = new AlbumDeleter(albumRepository);
        ArtistRetriever artistRetriever = new ArtistRetriever(artistRepository);
        SongAssigner songAssigner = new SongAssigner(albumRetriever, songRetriever);
        ArtistDeleter artistDeleter = new ArtistDeleter(artistRepository, artistRetriever, albumRetriever, albumDeleter, songDeleter);
        ArtistAssigner artistAssigner = new ArtistAssigner(artistRetriever ,albumRetriever);
        ArtistUpdater artistUpdater = new ArtistUpdater(artistRetriever);
        return new SongifyCrudFacade(
                songAdder,
                songRetriever,
                songDeleter,
                songUpdater,
                artistAdder,
                genreAdder,
                albumAdder,
                artistRetriever,
                albumRetriever,
                artistDeleter,
                artistAssigner,
                artistUpdater,
                genreRetriever,
                genreAssigner,
                songAssigner




        );
    }
}
