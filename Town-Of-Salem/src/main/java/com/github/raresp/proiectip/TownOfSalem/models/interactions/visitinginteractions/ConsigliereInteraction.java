package com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import jakarta.persistence.Entity;

import java.util.List;

public class ConsigliereInteraction extends VisitingInteraction{
    public ConsigliereInteraction(Character actioner, List<Character> targets, int priority) {
        super(actioner, targets, priority);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        Character target = this.targets.get(0);

        switch (target.getClass().getSimpleName()) {
            case "Doctor" ->
                    actioner.AddNightResult("Your target is a professional surgeon. They must be a Doctor.");
            case "Escort" ->
                    actioner.AddNightResult("Your target is a beautiful person working for the town. They must be an Escort.");
            case "Sheriff" ->
                    actioner.AddNightResult("Your target is a protector of the town. They must be a Sheriff.");
            case "Vigilante" ->
                    actioner.AddNightResult("Your target will bend the law to enact justice. They must be a Vigilante.");
            case "Consigliere" ->
                    actioner.AddNightResult("Your target gathers information for the Mafia. They must be a Consigliere.");
            case "Consort" ->
                    actioner.AddNightResult("Your target is a beautiful person working for the Mafia. They must be a Consort.");
            case "Forger" ->
                    actioner.AddNightResult("Your target is good at forging documents. They must be a Forger.");
            case "GodFather" ->
                    actioner.AddNightResult("Your target is the leader of the Mafia. They must be the Godfather.");
            case "Mafioso" ->
                    actioner.AddNightResult("Your target does the Godfather's dirty work. They must be a Mafioso.");
            case "Framer" -> actioner.AddNightResult("Your target has a desire to deceive. They must be a Framer!");
            case "Arsonist" ->
                    actioner.AddNightResult("Your target likes to watch things burn. They must be an Arsonist.");
            case "SerialKiller" ->
                    actioner.AddNightResult("Your target wants to kill everyone. They must be a Serial Killer.");
            case "Survivor" ->
                    actioner.AddNightResult("Your target simply wants to live. They must be a Survivor.");
            case "Jailor" ->
                    actioner.AddNightResult("Your target detains people at night. They must be a Jailor.");
            case "Veteran" ->
                    actioner.AddNightResult("Your target is a paranoid war hero. They must be a Veteran.");
        }
        target.setFramed(false);
        //devine mafiot
    }
}
