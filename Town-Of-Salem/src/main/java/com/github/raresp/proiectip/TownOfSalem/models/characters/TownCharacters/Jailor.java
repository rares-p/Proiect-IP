package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class Jailor extends TownCharacter {
    public Jailor(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Unstoppable ;//it says that it can forcefully execute the prisoner
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;

    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }
}