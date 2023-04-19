package com.github.raresp.proiectip.TownOfSalem.models.characters;

public class Sheriff extends Character {
    public Sheriff(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.NONE;
        this.defense = DefenseTypes.NONE;
        this.innocent = true;
    }
}
