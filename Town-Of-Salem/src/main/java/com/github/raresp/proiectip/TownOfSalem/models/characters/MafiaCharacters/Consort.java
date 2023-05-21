package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.distractinteractions.ConsortInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.distractinteractions.DistractInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Consort extends MafiaCharacter {
    public Consort(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.Roleblock;
        this.innocent = false;
        this.actionText = "Distract";
    }

    public Consort() {
    }
    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return null;
        return new ConsortInteraction(this, targets, 2);
    }
}
