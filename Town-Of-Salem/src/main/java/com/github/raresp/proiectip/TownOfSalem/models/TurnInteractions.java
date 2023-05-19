package com.github.raresp.proiectip.TownOfSalem.models;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.GodFather;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.Mafioso;
import com.github.raresp.proiectip.TownOfSalem.models.characters.PassiveActing;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Doctor;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.AttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.PassiveInteraction;

import java.util.*;
import java.util.stream.Collectors;

public class TurnInteractions {
    PriorityQueue<Interaction> interactions;
    List<Interaction> validInteractions = new ArrayList<>();

    public TurnInteractions(List<Character> characters){
        interactions = characters.stream().map(Character::createInteraction).collect(Collectors.toCollection(PriorityQueue::new));
    }

    public void addInteraction(Interaction interaction){
        interactions.add(interaction);
    }

    public void computeInteractionsOutcome() {
        for(int p = 1 ; p <=6; p++){
            if(p==5)
                computeMafiosoInteraction();

            int priority = p;

            validInteractions = interactions.stream()
                    .filter(i -> i.getPriority() == priority)
                    .filter(i -> i.actioner.isAlive())
                    .filter(Interaction::isValid)
                    .collect(Collectors.toList());

            for(Interaction interaction : validInteractions)
                if(interaction instanceof PassiveInteraction && interaction.actioner instanceof PassiveActing actioner)
                    actioner.passiveAction(interaction.targets);
                else
                    interaction.actioner.act();
        }
        computeDoctorMessage();
    }

    private void computeMafiosoInteraction(){
        Interaction mafiosoInteraction = interactions.stream().filter(i -> i.actioner instanceof Mafioso).filter(i-> i.actioner.isAlive()).findFirst().orElse(null);
        Interaction godfatherInteraction = interactions.stream().filter(i -> i.actioner instanceof GodFather).filter(i-> i.actioner.isAlive()).findFirst().orElse(null);

        if(mafiosoInteraction == null || godfatherInteraction == null) return;

        interactions.remove(mafiosoInteraction);
        interactions.remove(godfatherInteraction);

        Mafioso mafioso = (Mafioso) mafiosoInteraction.actioner;
        GodFather godFather = (GodFather) godfatherInteraction.actioner;

        if(godFather.roleBlocked) {
            if (!mafioso.roleBlocked)
                interactions.add(mafiosoInteraction);
        }
        else if(mafioso.roleBlocked)
            interactions.add(godfatherInteraction);
        else if(godfatherInteraction.targets.isEmpty())  ///godfather nu a selectat pe nimeni
            interactions.add(mafiosoInteraction);
        else {
                interactions.add(new AttackInteraction(mafioso, godfatherInteraction.targets, 5));
                mafioso.AddNightResult("The Godfather ordered you to kill " + godfatherInteraction.targets.get(0).getPlayerUsername() + "!");
                godFather.AddNightResult("The mafioso attacked the target you selected.");
            }
    }

    private void computeDoctorMessage(){
        List<Character> healedCharacters = interactions.stream().map(i -> i.actioner)
                .filter(c-> c.healed).toList();
        for(Character character : healedCharacters){
            if(interactions.stream().anyMatch(i -> i instanceof AttackInteraction || i instanceof PassiveInteraction)){
                List<Interaction> healingInteractions = interactions.stream()
                        .filter(i -> i.targets.get(0).getPlayerUsername().equals(character.getPlayerUsername()))
                        .toList();
                for(Interaction interaction : healingInteractions)
                    interaction.actioner.AddNightResult( "Your target was attacked last Night!");
            }
        }
    }

}