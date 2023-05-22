package com.github.raresp.proiectip.TownOfSalem.models.interactions.distractinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.ImmunityTypes;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.SerialKiller;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Werewolf;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.passiveinteractions.PassiveAttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.AttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.passiveinteractions.SerialKillerPassiveAttackInteraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DistractInteraction extends Interaction {
    public DistractInteraction(Character actioner, List<Character> targets, int priority) {
        this.actioner = actioner;
        this.targets = targets;
        this.priority = priority;
    }

    @Override
    public boolean isValid() {
        if (this.targets.isEmpty()) {
            actioner.AddNightResult("You decided to stay at home.");
            return false;
        }
        Character target = targets.get(0);
        target.visitors.add(actioner);
        if(target instanceof SerialKiller) {
            getTurnInteractions().addInteraction(new SerialKillerPassiveAttackInteraction(target, Arrays.asList(actioner), 5)); //nush exact care-i prioritatea, dupa heal
            return false;
        }
//        if(target instanceof Werewolf){
//           //scot si toate interactiunile in care werewolf ataca pe altii
//            getTurnInteractions().getInteractions().removeIf(i -> i.actioner instanceof Werewolf);
//            //werewolf sta acasa si ataca roleblockerul
//            getTurnInteractions().addInteraction(new AttackInteraction(target, new ArrayList<>(List.of(actioner)), 5)); //nush exact care-i prioritatea, dupa heal
//
//        }

        if(target.getImmunity() == ImmunityTypes.Roleblock) {
            actioner.AddNightResult("You tried to distract " + target.getPlayerUsername() + " but were unsuccessful!");
            target.AddNightResult("Someone tried to role block you, but you are immune!");
            return false;
        }
        return true;
    }
}
