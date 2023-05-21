package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions.ArsonistDouseInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.ArsonistIgniteInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions.ArsonistCleanSelfInteraction;

import java.util.ArrayList;
import java.util.List;
public class Arsonist extends NeutralCharacter {

    public static List<Character> dousedPlayers = new ArrayList<>();

    public Arsonist(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Unstoppable;
        this.defense = DefenseTypes.Basic;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.actionText = "Douse";
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return new ArsonistCleanSelfInteraction(this, 6);
        if (targets.get(0).getPlayerUsername().equals(this.playerUsername)) return new ArsonistIgniteInteraction(this, targets, 5);
        else return new ArsonistDouseInteraction(this, targets, 3);

    }
}
