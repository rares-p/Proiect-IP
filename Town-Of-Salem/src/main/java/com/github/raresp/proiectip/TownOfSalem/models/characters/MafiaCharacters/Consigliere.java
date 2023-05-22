package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.VisitingInteraction;

import java.util.List;

public class Consigliere extends MafiaCharacter {

    public Consigliere(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.innocent = false;
        this.actionText = "Investigate";
    }

    public Consigliere() {
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
        if(targets.isEmpty())
            return null;
        return new VisitingInteraction(this, targets, 3);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;

        Character target = this.targets.get(0);
        switch(target.getClass().getSimpleName()){
            case "Doctor": this.AddNightResult("Your target is a professional surgeon. They must be a Doctor.");
                break;
            case "Escort": this.AddNightResult("Your target is a beautiful person working for the town. They must be an Escort.");
                break;
            case "Sheriff": this.AddNightResult("Your target is a protector of the town. They must be a Sheriff.");
                break;
            case "Vigilante": this.AddNightResult("Your target will bend the law to enact justice. They must be a Vigilante.");
                break;
            case "Consigliere": this.AddNightResult("Your target gathers information for the Mafia. They must be a Consigliere.");
                break;
            case "Consort": this.AddNightResult("Your target is a beautiful person working for the Mafia. They must be a Consort.");
                break;
            case "Forger": this.AddNightResult("Your target is good at forging documents. They must be a Forger.");
                break;
            case "GodFather": this.AddNightResult("Your target is the leader of the Mafia. They must be the Godfather.");
                break;
            case "Mafioso": this.AddNightResult("Your target does the Godfather's dirty work. They must be a Mafioso.");
                break;
            case "Framer": this.AddNightResult("Your target has a desire to deceive. They must be a Framer!");
                break;
            case "Arsonist": this.AddNightResult("Your target likes to watch things burn. They must be an Arsonist.");
                break;
            case "SerialKiller": this.AddNightResult("Your target wants to kill everyone. They must be a Serial Killer.");
                break;
            case "Survivor": this.AddNightResult("Your target simply wants to live. They must be a Survivor.");
                break;
            case "Jailor": this.AddNightResult("Your target detains people at night. They must be a Jailor.");
                break;
            case "Veteran": this.AddNightResult("Your target is a paranoid war hero. They must be a Veteran.");
                break;
        }
        target.setFramed(false);
        //devine mafiot
    }
}
