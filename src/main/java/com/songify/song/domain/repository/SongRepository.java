package com.songify.song.domain.repository;

import com.songify.song.domain.model.SongEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class SongRepository {

    Map<Integer, SongEntity> dataBase = new HashMap<>(Map.of(
            1, new SongEntity("ShownMendes songs1", "Shawn Mendes"),
            2, new SongEntity("ShownMendes songs2", "Shawn Mendes"),
            3, new SongEntity("Ariana Grande songs1", "Ariana Grande"),
            4, new SongEntity("T-Love songs1", "T-Love"),
            5, new SongEntity("Lady Pank songs1", "Lady Pank")
    ));

    public SongEntity saveToDatabase(SongEntity song) {

        dataBase.put(dataBase.size() + 1, song);
        return song;
    }

    public Map<Integer, SongEntity> findAll(){
        return dataBase;
    }
}
