package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class Executioner extends NeutralCharacter {
    //primeste un target
    @OneToOne
    public Character target;
    public Executioner(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.Basic;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.actionText = "Execute";
        this.canAct = false;
    }
    protected Executioner() {
        super();
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

    @Override
    public String getRole() {
        if(target != null)
            return super.getRole() + " (" + target.getPlayerUsername() + ")";
        return "exectioner";
    }

    @Override
    public void setPossibleTargets(List<Character> characters) {
        this.possibleTargets.clear();
    }
}
