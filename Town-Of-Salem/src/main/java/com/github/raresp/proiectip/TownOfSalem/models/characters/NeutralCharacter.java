package com.github.raresp.proiectip.TownOfSalem.models.characters;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public abstract class NeutralCharacter extends Character {
    public NeutralCharacter(String playerUsername) {
        super(playerUsername);
    }

    protected NeutralCharacter() {
        super();
    }
}