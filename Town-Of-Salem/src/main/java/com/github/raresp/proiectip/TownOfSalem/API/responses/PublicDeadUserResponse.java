package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

public class PublicDeadUserResponse extends PublicUserResponse{
    public String role;
    public PublicDeadUserResponse(Character character) {
        super(character);
        role = character.getRole();
    }
}
