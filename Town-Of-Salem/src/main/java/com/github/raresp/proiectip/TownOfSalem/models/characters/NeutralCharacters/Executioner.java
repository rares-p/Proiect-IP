package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;

import java.util.List;

public class Executioner extends NeutralCharacter {
    //primeste un target
    private Character target;
    public Executioner(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.Basic;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.actionText = "Execute";
    }
    //setez la inceputul jocului, idk unde in gamelogic
    public void setTarget(Character target){
        this.target = target;
    }
    public Character getTarget(){
        return target;
    }
    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }

    @Override
    public Interaction createInteraction() {
        return null;
    }
}
