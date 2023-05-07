package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

public class CurrentUserResponse {
    public String username;
    public boolean isAlive;
    public String actionText;
    public String role;
    public Integer nrOfSelection;
    public Boolean canAct;

    public CurrentUserResponse(Character character) {
        this.isAlive = character.isAlive();
        this.username = character.getPlayerUsername();
        this.actionText = character.getActionText();
        this.role = character.getRole();
        this.nrOfSelection = character.getNumberOfSelection();
        this.canAct = character.canAct;
    }
}
