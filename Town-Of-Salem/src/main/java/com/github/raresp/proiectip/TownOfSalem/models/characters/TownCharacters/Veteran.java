package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions.BasicInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions.VeteranSetAlertInteraction;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Veteran extends TownCharacter {
    public int alerts = 3;
    public boolean onAlert = false;

    public Veteran(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Powerful;
        this.defense = DefenseTypes.None;
        this.innocent = true;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.actionText = "Alert";
    }
    protected Veteran() {
        super();
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
        this.onAlert = false;
    }
    @Override
    public void resetStats(){
        super.resetStats();
        onAlert = false;
    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return null;
        return new VeteranSetAlertInteraction(this);
    }

    @Override
    public void checkIfCanAct() {
        if(!canAct)
            return;
        if(alerts == 0)
            canAct = false;
    }
    @Override
    public String nightBeginningMessage() {
        return "You have " + alerts + " alerts left";
    }
    @Override
    public void setPossibleTargets(List<Character> characters) {
        this.possibleTargets.clear();
        if(canAct)
            this.possibleTargets.add(this.getPlayerUsername());
    }
}
