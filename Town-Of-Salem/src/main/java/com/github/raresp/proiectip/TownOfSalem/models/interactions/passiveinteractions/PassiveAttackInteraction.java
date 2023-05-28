package com.github.raresp.proiectip.TownOfSalem.models.interactions.passiveinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Bodyguard;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.AttackInteraction;

import java.util.List;

public abstract class PassiveAttackInteraction extends Interaction {
    //pt veteran si serial killer
//la veteran act-ul e sa se puna on alert, si asta ca omoara pe cine il viziteaza e ceva pasiv
    public PassiveAttackInteraction(Character actioner, List<Character> targets, int priority) {
        this.actioner = actioner;
        this.targets = targets;
        this.priority = priority;
    }

    @Override
    public boolean isValid() {
        if(targets.isEmpty())
            return false;

        Character target = targets.get(0);
        if(target.getDefense().ordinal() >= actioner.getAttack().ordinal()) {
            actioner.AddNightResult("You tried to attack " + target.getPlayerUsername() + " but his defense was too strong!");
            if(target.healed) target.AddNightResult("You were attacked by a " + actioner.getClass().getSimpleName() + " but someone nursed you back to health");
            else target.AddNightResult("You were attacked by a " + actioner.getClass().getSimpleName() + " but your defense was too strong");

            return false;
        }

        return true;
    }
}
