package com.github.raresp.proiectip.TownOfSalem.models.interactions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Arsonist;

import java.util.List;

public class AttackInteraction extends Interaction {
    public AttackInteraction(Character actioner, List<Character> targets, int priority) {
        super(actioner, targets, priority);
    }

    @Override
    public boolean isValid() {
        if (this.targets.isEmpty()) {
            if(actioner instanceof Arsonist arsonist) {
                arsonist.AddNightResult("You cleaned the doused gas from yourself");
                Arsonist.dousedPlayers.remove(arsonist);
                return false;
            }
            actioner.AddNightResult("You decided to stay at home.");
            return false;
        }
        Character target = targets.get(0);
        target.visitors.add(actioner);
        if(actioner.roleBlocked)
            return false;
        if(target.getDefense().ordinal() >= actioner.getAttack().ordinal()) {
            actioner.AddNightResult("You tried to attack " + target.getPlayerUsername() + " but his defense was too strong!");
            target.AddNightResult("Someone attacked you last night but your defense was too strong!");
            return false;
        }

        return true;
    }
}
