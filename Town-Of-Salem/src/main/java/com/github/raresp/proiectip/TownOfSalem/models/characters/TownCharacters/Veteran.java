package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.BasicInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;

import java.util.List;

public class Veteran extends TownCharacter implements PassiveActing {
    private int alerts = 3;
    public boolean onAlert = false;

    public Veteran(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Powerful;
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
    public void resetStats(){
        super.resetStats();
        onAlert = false;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }

    @Override
    public Interaction createInteraction() {
        return new BasicInteraction(this, targets, 1);
    }


    @Override
    public void act() {
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
        target.AddNightResult("You were murdered by the Veteran you visited.");
        this.AddNightResult(target.getPlayerUsername() + " visited you last night. You murdered them!");
    }
}
