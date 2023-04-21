package com.github.raresp.proiectip.TownOfSalem.API.advices;

import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidCharacterException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidLobbyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InvalidCharacterAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidCharacterException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String invalidLobbyHandler(InvalidCharacterException ex) {
        return "{\"error\": " + ex.getMessage() + "\"}";
    }
}
