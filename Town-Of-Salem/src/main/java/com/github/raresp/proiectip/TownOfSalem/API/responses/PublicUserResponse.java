package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

public class PublicUserResponse {
    public String username;
    public boolean isAlive;

    public PublicUserResponse(Character character) {
        this.isAlive = character.isAlive();
        this.username = character.getPlayerUsername();
    }
}
