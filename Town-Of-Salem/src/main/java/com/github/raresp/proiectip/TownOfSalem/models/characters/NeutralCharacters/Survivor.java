package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class Survivor extends NeutralCharacter {
    private int bulletVestsCount = 4;

    private boolean isAlert = false;
    public Survivor(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.innocent = true;
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public void act(List<Character> listOfTargets) {
        /*by acting, the Survivor simply puts on a bulletproof vest and protects himself*/
        isAlert = true;//idk if we need isAlert if we already changed the defense type;
        bulletVestsCount--;
        this.defense = DefenseTypes.Powerful;
    }

    @Override
    public void act() {

    }
}