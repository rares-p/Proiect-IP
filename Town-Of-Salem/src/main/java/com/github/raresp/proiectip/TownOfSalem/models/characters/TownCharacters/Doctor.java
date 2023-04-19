package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class Doctor extends TownCharacter {
    //will be set true when he heals himself
    private boolean hasHealedHimself = false;

    public Doctor(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;

    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Powerful;
    }

    @Override
    public void act(List<Character> listOfTargets) {
        /* when acting, he gives temporary powerful defense*/
    }
}