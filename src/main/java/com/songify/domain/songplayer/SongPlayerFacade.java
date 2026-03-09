package com.songify.domain.songplayer;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;

class SongPlayerFacade {

    private final SongifyCrudFacade songifyCrudFacade;
    private final YoutubeHttpClient youtubeHttpClient;


    SongPlayerFacade(final SongifyCrudFacade songifyCrudFacade, final YoutubeHttpClient youtubeHttpClient) {
        this.songifyCrudFacade = songifyCrudFacade;
        this.youtubeHttpClient = youtubeHttpClient;
    }

    public String playSongWihId(Long id){
        SongDto songDtoById = songifyCrudFacade.findSongDtoById(id);
        String name = songDtoById.name();

        String result = youtubeHttpClient.playSongByName(name);
        if(result.equals("success")){
            return result;
        }
        throw new RuntimeException("some error - result failed");
    }
}
