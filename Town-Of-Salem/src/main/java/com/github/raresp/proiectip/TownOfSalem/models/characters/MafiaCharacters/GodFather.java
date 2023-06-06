package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.AttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.GodfatherInteraction;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class GodFather extends MafiaCharacter {
    @OneToOne
    @JoinColumn(name = "mafioso_godfather_id", nullable = true)
    public Character mafioso = null;

    public GodFather(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Basic;
        this.defense = DefenseTypes.Basic;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.innocent = true;
        this.actionText = "Order Kill";
    }

    protected GodFather() {
        super();
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }

    @Override
    public Interaction createInteraction() {
        return new GodfatherInteraction(this, targets);
    }

}