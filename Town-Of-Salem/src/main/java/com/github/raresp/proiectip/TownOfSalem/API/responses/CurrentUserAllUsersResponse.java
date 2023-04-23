package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.GameState;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;

import java.time.ZoneOffset;
import java.util.List;

public class CurrentUserAllUsersResponse {
    private Game game;
    private Character character;

    public CurrentUserAllUsersResponse(Game game, Character character) {
        this.game = game;
        this.character = character;
    }

    public Character getCurrentUser() {
        return character;
    }

    public List<Character> getUsers(){
        return game.getCharacters();
    }

    public GameState getState(){
        return game.gameState;
    }

    public Long getTimeEndState(){
        return game.timeOfCurrentState.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
