package com.github.raresp.proiectip.TownOfSalem.models.interactions.miscellaneousinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;

import java.util.List;

public class WerewolfSetTargetInteraction extends Interaction {
    public WerewolfSetTargetInteraction(Character actioner, List<Character> targets){
        this.actioner = actioner;
        this.targets = targets;
        this.priority = 5;
    }
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void act() {
        //nu face nimic aici, functionalitatea e in clasele WerewolfRampageTargetInteraction si WerewolfRampageHomeInteraction
        //dar trebuie sa am ce cauta in metoda computeWerewolfInteraction in TurnInteractions
    }
}
