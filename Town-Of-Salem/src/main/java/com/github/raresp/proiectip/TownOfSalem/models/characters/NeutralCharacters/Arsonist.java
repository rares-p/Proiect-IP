package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions.ArsonistDouseInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.ArsonistIgniteInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions.ArsonistCleanSelfInteraction;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Arsonist extends NeutralCharacter {

    public static List<Character> dousedPlayers = new ArrayList<>();

    public Arsonist(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Unstoppable;
        this.defense = DefenseTypes.Basic;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.actionText = "Douse";
    }
    protected Arsonist() {
        super();
    }
    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return new ArsonistCleanSelfInteraction(this);
        if (targets.get(0).getPlayerUsername().equals(this.playerUsername)) return new ArsonistIgniteInteraction(this, targets);
        else return new ArsonistDouseInteraction(this, targets);
    }
    @Override
    public void setPossibleTargets(List<Character> characters) {
    }
}
