package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class CurrentUserResponse {
    public String userId;
    public boolean isAlive;
    public String actionText;
    public String role;
    public Integer nrOfSelection;
    public Boolean canAct;
    public List<String> nightResults;

    public CurrentUserResponse(Character character) {
        this.isAlive = character.isAlive();
        this.userId = character.getPlayerUsername();
        this.actionText = character.getActionText();
        this.role = character.getRole();
        this.nrOfSelection = character.getNumberOfSelection();
        this.canAct = character.canAct;
        this.nightResults = character.nightResults;
    }
}
