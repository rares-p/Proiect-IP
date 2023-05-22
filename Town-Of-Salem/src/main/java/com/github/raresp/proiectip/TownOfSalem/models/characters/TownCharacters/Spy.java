package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions.SpyInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions.VisitingInteraction;

import java.util.List;

public class Spy extends TownCharacter {
    public Spy (String playerUsername){
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.actionText = "Spy";
    }
    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return null;
        return new SpyInteraction(this, targets);
    }
}
