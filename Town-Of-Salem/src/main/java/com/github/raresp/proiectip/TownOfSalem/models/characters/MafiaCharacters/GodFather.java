package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class GodFather extends MafiaCharacter {

    private Mafioso mafioso; //read comment from Mafioso class
    public GodFather(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Basic;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.innocent = true;//because of detection immunity
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }
}