package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions.BasicInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions.SurvivorInteraction;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Survivor extends NeutralCharacter {
    public int bulletVestsCount = 4;
    public Survivor(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.innocent = true;
    }

    protected Survivor() {
        super();
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }
    @Override
    public Interaction createInteraction() {
        return new SurvivorInteraction(this);
    }
}