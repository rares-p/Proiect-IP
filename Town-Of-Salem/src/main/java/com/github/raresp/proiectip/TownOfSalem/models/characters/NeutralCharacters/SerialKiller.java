package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

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
        return null;
    }

    @Override
    public void act() {
        Character target = this.targets.get(0);
        target.setAlive(false);
        target.AddNightResult("You were attacked by a Serial Killer!");
    }

    @Override
    public void passiveAction(List<Character> targets) {
        Character target = targets.get(0);
        target.setAlive(false);
        this.AddNightResult("You were murdered by the Serial Killer you visited.");
        target.AddNightResult("Someone tried to role block you. You murdered them!");

    }
}