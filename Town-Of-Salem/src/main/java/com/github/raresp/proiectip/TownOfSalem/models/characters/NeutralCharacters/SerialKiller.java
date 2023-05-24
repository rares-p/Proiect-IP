package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.AttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.SerialKillerTargetInteraction;
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
    protected SerialKiller() {
        super();
    }
    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return null;
        return new SerialKillerTargetInteraction(this, targets);
    }
}