package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Jester extends NeutralCharacter {
    //putem face ca jesterul sa omoare pe cnv dupa ce e spanzurat, dar momentan
    //lasam doar sa vrea sa fie spanzurat
    //tb sa punem la win conditions
    public Jester(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.actionText = "Jest";//irelevant
        this.canAct = false;
    }
    protected Jester() {
        super();
    }
    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }

    @Override
    public Interaction createInteraction() {
        return null;
    }
}
