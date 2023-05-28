package com.github.raresp.proiectip.TownOfSalem.models.interactions.distractinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.TurnInteractions;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.ImmunityTypes;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.SerialKiller;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Werewolf;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.miscellaneousinteractions.WerewolfSetTargetInteraction;
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
        //target.visitors.add(actioner);
//        if(target instanceof SerialKiller) {
//            turnInteractions.addInteraction(new SerialKillerPassiveAttackInteraction(target, Arrays.asList(actioner))); //nush exact care-i prioritatea, dupa heal
//            return false;
//        }
//        if(target instanceof Werewolf werewolf && werewolf.isFullMoon()){
//            //scot si toate interactiunile in care werewolf ataca pe altii
//            turnInteractions.getInteractions().removeIf(i -> i.actioner instanceof Werewolf);
//            //werewolf sta acasa si ataca roleblockerul
//            turnInteractions.addInteraction(new WerewolfSetTargetInteraction(target, new ArrayList<>())); //lista goala => o sa stea acasa
//        }

        if(target instanceof Werewolf werewolf && werewolf.isFullMoon()) {
            werewolf.AddNightResult("Someone tried to roleblock you.");
            return false;
        }

        if(target.getImmunity() == ImmunityTypes.Roleblock) {
            actioner.AddNightResult("You tried to distract " + target.getPlayerUsername() + " but were unsuccessful!");
            target.AddNightResult("Someone tried to role block you, but you are immune!");
            return false;
        }
        return true;
    }
}
