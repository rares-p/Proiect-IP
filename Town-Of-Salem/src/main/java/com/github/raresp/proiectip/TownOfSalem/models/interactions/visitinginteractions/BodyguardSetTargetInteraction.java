package com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class BodyguardSetTargetInteraction extends VisitingInteraction{
    public BodyguardSetTargetInteraction(Character actioner, List<Character> targets, int priority) {
        super(actioner, targets, priority);
    }

    @Override
    public void act() {
        ///asta nu face nimica, e doar ca sa avem ce cauta in metoda computeBodyguardInteraction in TurnInteractions
    }
}
