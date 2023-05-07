package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.AttackTypes;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.DefenseTypes;
import com.github.raresp.proiectip.TownOfSalem.models.characters.ImmunityTypes;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacter;

import java.util.List;

public class Veteran extends TownCharacter {
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
    public void act() {
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
}
