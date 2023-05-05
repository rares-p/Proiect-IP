package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.SerialKiller;
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
    public void act() {
        if (this.targets.isEmpty()) {
            this.AddNightResult("You decided to stay at home.");
            return;
        }

        Character target = this.targets.get(0);
        if (target instanceof SerialKiller)
            return;
        target.setRoleBlocked(true);
        target.AddNightResult("Someone occupied your night. You were role blocked!");
    }
}