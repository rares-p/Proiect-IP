package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.AttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;

import java.util.List;

public class SerialKiller extends NeutralCharacter implements PassiveActing {
    public SerialKiller(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Basic;
        this.defense = DefenseTypes.Basic;
        this.innocent = false;
        this.immunity = ImmunityTypes.Roleblock;
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return null;
        return new AttackInteraction(this, targets, 5);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;

        Character target = this.targets.get(0);
        target.setAlive(false);
        target.AddNightResult("You were attacked by a Serial Killer!");
    }

    @Override
    public void passiveAction(List<Character> targets) {
        if(targets.isEmpty())
            return;

        Character target = targets.get(0);
        target.setAlive(false);
        target.AddNightResult("You were murdered by the Serial Killer you visited.");
        this.AddNightResult("Someone tried to role block you. You murdered them!");
    }
}