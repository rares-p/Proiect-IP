package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

public class PublicUserResponseRole extends PublicUserResponse{
    public String role;
    public PublicUserResponseRole(Character character) {
        super(character);
        role = character.getRole();
    }
}
