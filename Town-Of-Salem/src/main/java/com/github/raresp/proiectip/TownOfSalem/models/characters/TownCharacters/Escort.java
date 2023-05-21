package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.distractinteractions.DistractInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Escort extends TownCharacter {
    public Escort(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.Roleblock;
        this.actionText = "Distract";
    }

    protected Escort() {
        super();
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return null;
        return new DistractInteraction(this, targets, 2);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        Character target = this.targets.get(0);
        target.setRoleBlocked(true);
        target.AddNightResult("Someone occupied your night. You were role blocked!");
        this.AddNightResult("You kept " + target.getPlayerUsername() + " occupied last night. They did not use their ability!");
    }
}