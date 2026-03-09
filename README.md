# Songify

REST API application for managing artists, albums, songs, and music genres, built with Spring Boot and PostgreSQL.

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.5**
  - Spring Data JPA
  - Spring Web
  - Spring Validation
- **PostgreSQL**
- **Flyway** - database migrations
- **Lombok** - boilerplate code reduction
- **SpringDoc OpenAPI** (Swagger) - API documentation
- **Docker Compose** - database containerization
- **Maven** - dependency management

## Architecture

The project uses a layered architecture with clear separation:

- **Domain Layer** (`com.songify.domain.crud`) - business logic, entities, repositories
- **Infrastructure Layer** (`com.songify.infrastructure.crud`) - REST controllers, DTOs, mappers
- **Facade Pattern** - `SongifyCrudFacade` as entry point to business logic

### Main Entities

- **Song** - song (name, release date, duration, language)
- **Artist** - artist (name)
- **Album** - album (title, release date)
- **Genre** - music genre (name)

### Relationships

- **Song ↔ Genre**: 1:1 relationship (song has one genre)
- **Song ↔ Album**: N:1 relationship (many songs in an album)
- **Artist ↔ Album**: N:N relationship (many artists can have many albums)

## Features

### Song Management
- ✅ Add song
- ✅ Get all songs (with pagination)
- ✅ Get song by ID
- ✅ Update song (PUT - full, PATCH - partial)
- ✅ Delete song

### Artist Management
- ✅ Add artist
- ✅ Add artist with default album and song
- ✅ Get all artists (with pagination)
- ✅ Update artist name
- ✅ Assign artist to album
- ✅ Delete artist (with albums and songs)

### Album Management
- ✅ Add album with song
- ✅ Get album by ID (with artists and songs)

### Genre Management
- ✅ Add music genre

## API Endpoints

### Songs (`/songs`)
```
GET    /songs              - list all songs (pageable)
GET    /songs/{id}         - get song details
POST   /songs              - create new song
PUT    /songs/{id}         - full update of song
PATCH  /songs/{id}         - partial update of song
DELETE /songs/{id}         - delete song
```

### Artists (`/artists`)
```
GET    /artists                      - list all artists (pageable)
POST   /artists                      - create artist
POST   /artists/album/song           - create artist with default album and song
PATCH  /artists/{artistId}           - update artist name
PUT    /artists/{artistId}/{albumId} - assign artist to album
DELETE /artists/{artistId}           - delete artist with albums and songs
```

### Albums (`/albums`)
```
GET  /albums/{albumId}  - get album details with artists and songs
POST /albums            - create album with song
```

### Genres (`/genres`)
```
POST /genres  - create music genre
```

## System Requirements

- Java 17+
- Docker & Docker Compose
- Maven 3.6+
- Ports 54320 (PostgreSQL) and 5050 (pgAdmin) must be available

## Installation and Setup

### 1. Clone repository
```bash
git clone <repository-url>
cd songify
```

### 2. Start database
```bash
docker-compose up -d
```

This will start:
- **PostgreSQL** on port `54320`
  - user: `user`
  - password: `admin`
  - database: `postgres`
- **pgAdmin** on port `5050` (http://localhost:5050)
  - email: `raj@nola.com`
  - password: `admin`

### 3. Run application

#### Using Maven
```bash
./mvnw spring-boot:run
```

#### Using IDE
Run `SongifyApplication.java` class

Application will be available at: `http://localhost:8080`

### 4. API Documentation (Swagger)
```
http://localhost:8080/swagger-ui.html
```

## Configuration

Application configuration in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:54320/postgres
spring.datasource.username=user
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=none
spring.flyway.enabled=true
```

## Database Migrations (Flyway)

Migrations are located in `src/main/resources/db.migration/`:

- `V1` - create `song` table
- `V2` - add UUID to entities
- `V3` - add timestamps
- `V4` - create `genre` table
- `V5` - create `album` table
- `V6` - create `artist` table
- `V7` - add UUID and created_on to album, artist, genre
- `V8` - 1:1 relationship between song and genre
- `V9` - add foreign key album_id to song
- `V10` - join table for N:N artist-album relationship
- `V11-V15` - initial data (albums, artists, genres, songs)
- `V16` - optimistic locking (version column)
- `V17` - index on name column in song table

## Design Patterns

### Facade Pattern
`SongifyCrudFacade` - centralizes access to business logic

### Repository Pattern
Interfaces extending `JpaRepository`:
- `SongRepository`
- `ArtistRepository`
- `AlbumRepository`
- `GenreRepository`

### Service Layer Pattern
Separate services for operations:
- `SongAdder`, `SongRetriever`, `SongUpdater`, `SongDeleter`
- `ArtistAdder`, `ArtistRetriever`, `ArtistUpdater`, `ArtistDeleter`, `ArtistAssigner`
- `AlbumAdder`, `AlbumRetriever`, `AlbumDeleter`
- `GenreAdder`, `GenreDeleter`

### DTO Pattern
Separation of domain model from API through DTO objects

## Security

### Optimistic Locking
All entities extend `BaseEntity`, which contains:
- `@Version` - versioning for optimistic locking
- `uuid` - unique identifier
- `createdOn` - creation timestamp

### Validation
Using `@Valid` for request validation in controllers

### Error Handling
- `SongErrorHandler` - exception handling for songs
- `ApiValidationErrorHandler` - validation error handling
- Custom exceptions: `SongNotFoundException`, `ArtistNotFoundException`, `AlbumNotFoundException`


## Project Structure

## Project Structure

```
songify/
├── src/
│   ├── main/
│   │   ├── java/com/songify/
│   │   │   ├── SongifyApplication.java
│   │   │   ├── domain/
│   │   │   │   └── crud/
│   │   │   │       ├── Song.java
│   │   │   │       ├── Artist.java
│   │   │   │       ├── Album.java
│   │   │   │       ├── Genre.java
│   │   │   │       ├── SongLanguage.java
│   │   │   │       ├── SongRepository.java
│   │   │   │       ├── ArtistRepository.java
│   │   │   │       ├── AlbumRepository.java
│   │   │   │       ├── GenreRepository.java
│   │   │   │       ├── SongifyCrudFacade.java
│   │   │   │       ├── SongAdder.java
│   │   │   │       ├── SongRetriever.java
│   │   │   │       ├── SongUpdater.java
│   │   │   │       ├── SongDeleter.java
│   │   │   │       ├── ArtistAdder.java
│   │   │   │       ├── ArtistRetriever.java
│   │   │   │       ├── ArtistUpdater.java
│   │   │   │       ├── ArtistDeleter.java
│   │   │   │       ├── ArtistAssigner.java
│   │   │   │       ├── AlbumAdder.java
│   │   │   │       ├── AlbumRetriever.java
│   │   │   │       ├── AlbumDeleter.java
│   │   │   │       ├── GenreAdder.java
│   │   │   │       ├── GenreDeleter.java
│   │   │   │       ├── SongNotFoundException.java
│   │   │   │       ├── ArtistNotFoundException.java
│   │   │   │       ├── AlbumNotFoundException.java
│   │   │   │       ├── GenreWasNotDeletedException.java
│   │   │   │       ├── dto/
│   │   │   │       │   ├── SongDto.java
│   │   │   │       │   ├── SongRequestDto.java
│   │   │   │       │   ├── SongLanguageDto.java
│   │   │   │       │   ├── ArtistDto.java
│   │   │   │       │   ├── ArtistRequestDto.java
│   │   │   │       │   ├── AlbumDto.java
│   │   │   │       │   ├── AlbumRequestDto.java
│   │   │   │       │   ├── AlbumInfo.java
│   │   │   │       │   ├── AlbumDtoWithArtistsAndSongs.java
│   │   │   │       │   ├── GenreDto.java
│   │   │   │       │   └── GenreRequestDto.java
│   │   │   │       └── util/
│   │   │   │           └── BaseEntity.java
│   │   │   └── infrastructure/
│   │   │       ├── apiValidation/
│   │   │       │   └── apiValidation/
│   │   │       │       ├── ApiValidationErrorHandler.java
│   │   │       │       └── ApiValidationErrorResponseDto.java
│   │   │       └── crud/
│   │   │           ├── song/
│   │   │           │   └── controller/
│   │   │           │       ├── SongRestController.java
│   │   │           │       ├── SongViewController.java
│   │   │           │       ├── SongControllerMapper.java
│   │   │           │       ├── dto/
│   │   │           │       │   ├── SongDtoForJson.java
│   │   │           │       │   ├── request/
│   │   │           │       │   │   ├── CreateSongRequestDto.java
│   │   │           │       │   │   ├── UpdateSongRequestDto.java
│   │   │           │       │   │   └── PartiallyUpdateSongRequestDto.java
│   │   │           │       │   └── response/
│   │   │           │       │       ├── CreateSongResponseDto.java
│   │   │           │       │       ├── GetSongResponseDto.java
│   │   │           │       │       ├── GetAllSongsResponseDto.java
│   │   │           │       │       ├── UpdateSongResponseDto.java
│   │   │           │       │       ├── PartiallyUpdateSongResponseDto.java
│   │   │           │       │       ├── DeleteSongResponseDto.java
│   │   │           │       │       └── SongControllerResponseDto.java
│   │   │           │       └── error/
│   │   │           │           ├── SongErrorHandler.java
│   │   │           │           └── ErrorSongResponseDTO.java
│   │   │           ├── artist/
│   │   │           │   ├── ArtistController.java
│   │   │           │   ├── ArtistUpdateRequestDto.java
│   │   │           │   └── AllArtistDto.java
│   │   │            album/
│   │   │           │   └── AlbumController.java
│   │   │           └── genre/
│   │   │               └── GenreController.java
│   │   └── resources/
│   │       ├── db.migration/
│   │       │   ├── V1__crreate_song_table.sql
│   │       │   ├── V2__added_uuid.sql
│   │       │   ├── V3__added_timeStamp_for_song.sql
│   │       │   ├── V4__create_table_genre.sql
│   │       │   ├── V5__create_table_album.sql
│   │       │   ├── V6__create_table_artist.sql
│   │       │   ├── V7__added_uuid_and_created_to_album_artist_genre.sql
│   │       │   ├── V8__relation_one_to_one_between_song_and_genre.sql
│   │       │   ├── V9__adding_foreign_key_album_id_to_song.sql
│   │       │   ├── V10__create_join_table_for_many_to_many_artist_albums.sql
│   │       │   ├── V11__insert_albums.sql
│   │       │   ├── V12__insert_artists.sql
│   │       │   ├── V13__insert_artists_albums.sql
│   │       │   ├── V14__insert_genres.sql
│   │       │   ├── V15__insert_songs.sql
│   │       │   ├── V16__added_optimistinc_locking_using_version_column.sql
│   │       │   └── V17__create_index_for_song_name.sql
│   │       ├── templates/
│   │       │   └── songs.html
│   │       ├── static/
│   │       │   └── home.html
│   │       └── application.properties
│   └── test/
│       └── java/com/songify/
│           └── SongifyApplicationTests.java
├── db/
│   └── init.sql
├── docker-compose.yml
├── pom.xml
├── requirments.md
└── README.md
```


## Usage Examples

### Add artist with default album and song
```bash
POST /artists/album/song
{
  "name": "Eminem"
}
```

### Add album with song
```bash
POST /albums
{
  "title": "The Eminem Show",
  "releaseDate": "2002-05-26T00:00:00Z",
  "songId": 1
}
```

### Assign artist to album
```bash
PUT /artists/1/2
```

### Get album with artists and songs
```bash
GET /albums/1
```

## License

Educational project
