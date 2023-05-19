package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.SerialKiller;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.DistractInteraction;
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
        return new DistractInteraction(this, targets, 2);
    }

    @Override
    public void act() {
        Character target = this.targets.get(0);
        target.setRoleBlocked(true);
        target.AddNightResult("Someone occupied your night. You were role blocked!");
        this.AddNightResult("You kept " + target.getPlayerUsername() + " occupied last night. They did not use their ability!");
    }
}