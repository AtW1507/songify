package com.songify.song.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Builder
@Entity
@Getter
@Setter
@Table(name = "song")
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String artist;

    public SongEntity() {}

    public SongEntity(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public SongEntity(Long id, String name, String artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }
}
