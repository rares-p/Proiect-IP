package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.AttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Mafioso extends MafiaCharacter {
    @OneToOne(mappedBy = "mafioso", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private GodFather godFather;

    public Mafioso(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Basic;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.innocent = false;
        this.actionText = "Attack";
    }

    protected Mafioso() {
        super();
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
        return new AttackInteraction(this, targets, 5);
    }

    @Override
    public void act() {
        Character target = this.targets.get(0);
        this.AddNightResult("You attacked " + target.getPlayerUsername() + "!");
        target.AddNightResult("You were attacked last night. You died");
        target.setIsAlive(false);

        //e promovat la godfather daca moare
    }
}