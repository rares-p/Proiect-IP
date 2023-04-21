package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class SerialKiller extends NeutralCharacter {
    /*if the SerialKiller is roleblocked, he kills the rollblocker in addition to his target;
    Roleblockers killed by the SerialKiller have their will destroyed;
    if he chooses to be cautios, he does not attack roleblockers too
     */
    private boolean isCautious = false;
    public SerialKiller(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Basic;
        this.defense = DefenseTypes.None;//it says nothing about the defense so I guess if the mafia
        //visits the SK, the SK dies?
        this.innocent = true;//?
        this.immunity = ImmunityTypes.Roleblock;
    }

    protected SerialKiller() {
        super();
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }

    public boolean isCautious() {
        return isCautious;
    }

    public void setCautious(boolean cautious) {
        isCautious = cautious;
    }


    @Override
    public void act(List<Character> listOfTargets) {

    }
}