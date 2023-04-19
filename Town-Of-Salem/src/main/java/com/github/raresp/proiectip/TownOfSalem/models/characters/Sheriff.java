package com.github.raresp.proiectip.TownOfSalem.models.characters;

import java.util.List;

public class Sheriff extends Character {
    public Sheriff(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.innocent = true;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }
}
