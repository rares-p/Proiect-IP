package com.github.raresp.proiectip.TownOfSalem.models.characters;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public abstract class TownCharacter extends Character{
    //idk what particularities a Town Character has, we'll see
    public TownCharacter(String playerUsername) {
        super(playerUsername);
        this.innocent = true;
    }

    public TownCharacter() {
        super();
    }
}