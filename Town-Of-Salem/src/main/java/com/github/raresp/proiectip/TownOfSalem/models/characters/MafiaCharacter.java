package com.github.raresp.proiectip.TownOfSalem.models.characters;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public abstract class MafiaCharacter extends Character {
    public MafiaCharacter(String playerUsername) {
        super(playerUsername);
    }

    protected MafiaCharacter() {
        super();
    }
    public void becomeMafioso(){

    }
    @Override
    public void setPossibleTargets(List<Character> characters) {
        this.possibleTargets.clear();
        for(Character c : characters)
            if(c.isAlive() && !(c instanceof MafiaCharacter))
                this.possibleTargets.add(c.getPlayerUsername());
    }
}