package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Survivor extends NeutralCharacter {
    private int bulletVestsCount = 4;

    private boolean isAlert = false;
    public Survivor(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.innocent = true;
    }

    protected Survivor() {
        super();
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public void act() {
        /*by acting, the Survivor simply puts on a bulletproof vest and protects himself*/
        isAlert = true;//idk if we need isAlert if we already changed the defense type;
        bulletVestsCount--;
        this.defense = DefenseTypes.Basic;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }
}