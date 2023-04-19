package com.github.raresp.proiectip.TownOfSalem.models.characters;

public abstract class TownCharacter extends Character{
    //idk what particularities a Town Character has, we'll see
    public TownCharacter(String playerUsername) {
        super(playerUsername);
        this.innocent = true;
    }
}