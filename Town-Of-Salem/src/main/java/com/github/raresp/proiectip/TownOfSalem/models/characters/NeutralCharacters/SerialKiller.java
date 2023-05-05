package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class SerialKiller extends NeutralCharacter {
    public SerialKiller(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Basic;
        this.defense = DefenseTypes.Basic;
        this.innocent = false;
        this.immunity = ImmunityTypes.Roleblock;
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
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
        target.setAlive(false);
        target.AddNightResult("You were attacked by a Serial Killer!");
    }
}