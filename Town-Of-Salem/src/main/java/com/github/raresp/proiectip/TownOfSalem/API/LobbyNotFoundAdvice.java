package com.github.raresp.proiectip.TownOfSalem.API;

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
    String employeeNotFoundHandler(LobbyNotFoundException ex) {
        return ex.getMessage();
    }
}
