package com.github.raresp.proiectip.TownOfSalem.API.advices;

import com.github.raresp.proiectip.TownOfSalem.exceptions.LobbyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class LobbyNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(LobbyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String lobbyNotFoundHandler(LobbyNotFoundException ex) {
        return "{\"error\": " + ex.getMessage() + "\"}";
    }
}
