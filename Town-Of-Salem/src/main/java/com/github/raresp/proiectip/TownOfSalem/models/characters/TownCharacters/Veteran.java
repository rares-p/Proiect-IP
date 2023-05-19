package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class Veteran extends TownCharacter implements PassiveActing {
    private int alerts = 3;
    public boolean onAlert = false;

    public Veteran(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Unstoppable;
        this.defense = DefenseTypes.None;
        this.innocent = true;
        this.immunity = ImmunityTypes.DetectionImmunity;
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
        this.onAlert = false;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }

    @Override
    public Interaction createInteraction() {
        return null;
    }


    @Override
    public void act() { ///o sa verific la inceput, dupa ce isi activeaza abilitatea cine il beleste
        if (this.targets.isEmpty()) {
            this.AddNightResult("You decided not to go on alert.");
            return;
        }

        alerts--;
        onAlert = true;
        this.defense = DefenseTypes.Basic;
        this.AddNightResult("You decided to go on alert.");
        this.AddNightResult("You have " + alerts + " vests left.");
    }

    @Override
    public void checkIfCanAct() {
        if(!canAct)
            return;
        if(alerts == 0)
            canAct = false;
    }

    @Override
    public void passiveAction(List<Character> characters) {
        Character target = targets.get(0);
        target.setAlive(false);
        this.AddNightResult("You were murdered by the Veteran you visited.");
        target.AddNightResult("Someone tried to role block you. You murdered them!");
    }
}
