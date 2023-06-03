package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.GameState;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

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
            if(c.isAlive())
                publicUserResponses.add(new PublicUserResponse(c));
            else
                publicUserResponses.add(new PublicDeadUserResponse(c));
        return publicUserResponses;
    }

    public GameState getState(){
        return game.gameState;
    }

    public Long getTimeEndState(){
        return game.timeOfCurrentState.toEpochMilli();
    }

    public String getJudgedCharacter(){
        if(game.selectedCharacter != null)
            return game.selectedCharacter.getPlayerUsername();
        return "";
    }
}

