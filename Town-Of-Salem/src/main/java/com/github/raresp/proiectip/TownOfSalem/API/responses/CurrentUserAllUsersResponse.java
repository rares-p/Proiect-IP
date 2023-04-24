package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.GameState;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class CurrentUserAllUsersResponse {
    private Game game;
    private Character character;

    public CurrentUserAllUsersResponse(Game game, Character character) {
        this.game = game;
        this.character = character;
    }

    public CurrentUserResponse getCurrentUser() {
        return new CurrentUserResponse(character);
    }

    public List<PublicUserResponse> getUsers(){
        List<PublicUserResponse> publicUserResponses = new ArrayList<>();
        for(Character c : game.getCharacters())
            publicUserResponses.add(new PublicUserResponse(c));
        return publicUserResponses;
    }

    public GameState getState(){
        return game.gameState;
    }

    public Long getTimeEndState(){
        return game.timeOfCurrentState.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}

