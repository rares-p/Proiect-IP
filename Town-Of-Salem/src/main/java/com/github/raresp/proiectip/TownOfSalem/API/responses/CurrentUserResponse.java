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
    public List<String> possibleTargets;
    public Long idDebug;

    public CurrentUserResponse(Character character) {
        this.isAlive = character.isAlive();
        this.userId = character.getPlayerUsername();
        this.actionText = character.getActionText();
        this.role = character.getRole();
        this.nrOfSelection = character.getNumberOfSelection();
        this.canAct = character.canAct;

        if(!character.nightResults.isEmpty() && character.nightResults.size() > 2 && character.nightResults.get(0).equals(character.nightResults.get(character.nightResults.size() / 2)))
            this.nightResults = character.nightResults.subList(0, character.nightResults.size() / 2);
        else
            this.nightResults = character.nightResults;//.subList(0, character.nightResults.size() / 2);
        this.possibleTargets = character.possibleTargets;
        idDebug = character.getId();
    }
}
