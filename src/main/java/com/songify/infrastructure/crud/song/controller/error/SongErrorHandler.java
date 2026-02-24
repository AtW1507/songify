package com.songify.infrastructure.crud.song.controller.error;

import com.songify.infrastructure.crud.song.controller.SongRestController;
import com.songify.domain.crud.SongNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class SongErrorHandler {

    @ExceptionHandler(SongNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorSongResponseDTO handleException(SongNotFoundException exception){
        log.warn("SongNotFoundException error while accessing song");
        return new ErrorSongResponseDTO(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
