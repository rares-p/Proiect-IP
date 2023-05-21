package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;
import java.util.stream.Collectors;

public class AddTargetResponse {
    public String userId;
    public String role;
    public List<String> targets;
    public AddTargetResponse(Character character) {
        this.userId = character.getPlayerUsername();
        this.role = character.getRole();
        this.targets = character.targets.stream().map(Character::getPlayerUsername).collect(Collectors.toList());
    }
}
