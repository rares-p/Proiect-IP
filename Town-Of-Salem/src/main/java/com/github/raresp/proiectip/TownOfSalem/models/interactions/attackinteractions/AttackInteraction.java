package com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Arsonist;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Werewolf;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;

import java.util.List;

public abstract class AttackInteraction extends Interaction {
    public AttackInteraction(Character actioner, List<Character> targets, int priority) {
        this.actioner = actioner;
        this.targets = targets;
        this.priority = priority;
    }

    @Override
    public boolean isValid() {
        if (this.targets.isEmpty()) {
           // actioner.AddNightResult("You decided to stay at home.");
            return false;
        }

        Character target = targets.get(0);
        //target.visitors.add(actioner);
        if (actioner.roleBlocked)
            return false;
        if (target.getDefense().ordinal() >= actioner.getAttack().ordinal()) {
            actioner.AddNightResult("You tried to attack " + target.getPlayerUsername() + " but his defense was too strong!");
            target.AddNightResult("Someone attacked you last night but your defense was too strong!");
            return false;
        }

        return true;
    }
}
