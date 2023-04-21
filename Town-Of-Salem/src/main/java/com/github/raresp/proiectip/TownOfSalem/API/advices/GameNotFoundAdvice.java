package com.github.raresp.proiectip.TownOfSalem.API.advices;

import com.github.raresp.proiectip.TownOfSalem.exceptions.GameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GameNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String gameNotFoundHandler(GameNotFoundException ex) {
        return "{\"error\": " + ex.getMessage() + "\"}";
    }
}
