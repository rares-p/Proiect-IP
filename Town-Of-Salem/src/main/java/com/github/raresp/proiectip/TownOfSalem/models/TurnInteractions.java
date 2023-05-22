package com.github.raresp.proiectip.TownOfSalem.models;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.PassiveActing;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.*;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.AttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.MafiosoInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.passiveinteractions.PassiveAttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.passiveinteractions.VeteranPassiveAttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions.VisitingInteraction;

import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class TurnInteractions {
    private PriorityQueue<Interaction> interactions;

    public TurnInteractions(List<Character> characters, boolean isFullMoon) {
        characters.stream().filter(character -> character instanceof Werewolf)
                .map(character -> (Werewolf) character)
                .forEach(werewolf -> werewolf.setFullMoon(isFullMoon));

        interactions = characters.stream()
                .filter(Character::isAlive)
                .map(Character::createInteraction)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(PriorityQueue::new));

        characters.stream()
                .filter(character -> character.targets.isEmpty())
                .forEach(character -> character.AddNightResult("You decided to stay at home"));
    }

    public void addInteraction(Interaction interaction) {
        interactions.add(interaction);
    }

    public void deleteInteraction(Interaction interaction){
        interactions.remove(interaction);
    }
    public PriorityQueue<Interaction> getInteractions(){
        return interactions;
    }
    public void computeInteractionsOutcome() {
        for (int p = 1; p <= 7; p++) {
            switch (p) {
                case 4 -> computeLookoutMessage();
                case 5 -> {
                    computeMafiosoInteraction();
              //      computeWerewolfInteraction(); //poate ar tb 6? sau 7? ca sa fi terminat de vizitat toata lumea
                }
                case 6 -> computeSpyMessage();
                case 7 -> computeVeteranInteraction();
            }
            int priority = p;

            List<Interaction> validInteractions = interactions.stream()
                    .filter(i -> i.getPriority() == priority)
                    .filter(i -> i.actioner.isAlive())
                    .filter(Interaction::isValid)
                    .toList();

            for (Interaction interaction : validInteractions)
                if(interaction.actioner.isAlive())
                    interaction.act();
        }
        computeDoctorMessage();
    }

    private void computeMafiosoInteraction() {
        Interaction mafiosoInteraction = interactions.stream().filter(i -> i.actioner instanceof Mafioso).filter(i -> i.actioner.isAlive()).findFirst().orElse(null);
        Interaction godfatherInteraction = interactions.stream().filter(i -> i.actioner instanceof GodFather).filter(i -> i.actioner.isAlive()).findFirst().orElse(null);

        if (mafiosoInteraction == null || godfatherInteraction == null) return;

        interactions.remove(mafiosoInteraction);
        interactions.remove(godfatherInteraction);

        Mafioso mafioso = (Mafioso) mafiosoInteraction.actioner;
        GodFather godFather = (GodFather) godfatherInteraction.actioner;

        if (godFather.roleBlocked) {
            if (!mafioso.roleBlocked)
                interactions.add(mafiosoInteraction);
        } else if (mafioso.roleBlocked)
            interactions.add(godfatherInteraction);
        else if (godfatherInteraction.targets.isEmpty())  ///godfather nu a selectat pe nimeni
            interactions.add(mafiosoInteraction);
        else {
            interactions.add(new MafiosoInteraction(mafioso, godfatherInteraction.targets));
            mafioso.AddNightResult("The Godfather ordered you to kill " + godfatherInteraction.targets.get(0).getPlayerUsername() + "!");
            godFather.AddNightResult("The mafioso attacked the target you selected.");
        }
    }

    private void computeDoctorMessage() {
        List<Character> healedCharacters = interactions.stream().map(i -> i.actioner)
                .filter(c -> c.healed).toList();

        if(healedCharacters.isEmpty())
            return;

        for (Character character : healedCharacters) {
            if (interactions.stream().anyMatch(i -> i instanceof AttackInteraction || i instanceof PassiveAttackInteraction)) {
                List<Interaction> healingInteractions = interactions.stream()
                        .filter(i -> i.targets.get(0).getPlayerUsername().equals(character.getPlayerUsername()))
                        .toList();
                for (Interaction interaction : healingInteractions)
                    interaction.actioner.AddNightResult("Your target was attacked last Night!");
            }
        }
    }

///am comentat asta fiindca am pus in clasa BodyguardAttackInteraction

//    private void computeBodyguardInteraction() {
//        var bodyguardInteractions = interactions.stream()
//                .filter(i -> i.actioner instanceof Bodyguard && i instanceof BodyguardSetTargetInteraction).toList();
//
//        for (var bodyguardInteraction : bodyguardInteractions) {
//            //caut targeturile
//            //pt fiecare target, vad daca are vizitatori care l-au atacat
//            Character target = bodyguardInteraction.targets.get(0);
//
//            var attackers = interactions.stream()
//                    .filter(interaction -> interaction instanceof AttackInteraction && interaction.targets.get(0) == target)
//                    .map(Interaction::getActioner)
//                    .toList();
//            //omor primul atacator din lista (doar daca nu a fost healed),
//            //deci fac un attack interaction care sa fie verificat dupa ce a dat medicul heal
//
//            interactions.add(new BodyguardAttackInteraction(bodyguardInteraction.actioner,
//                    List.of(attackers.get(0)), 3));
//        }
//
//    }

    private void computeSpyMessage() {
        //vad daca exista vreun spy si care e targetul lui
        var spyInteractions = interactions.stream().filter(i -> i.actioner instanceof Spy).toList();
        for (var spyInteraction : spyInteractions) {
            //get spy
            var spy = spyInteraction.actioner;

            if(spyInteraction.targets.isEmpty())
                continue;

            //get target
            var target = spyInteraction.targets.get(0);
            //see if they were roleblocked
            if (target.roleBlocked)
                spy.AddNightResult("Someone occupied your target's night. They were role blocked!");
            //see if they were attacked
            var attackers = interactions.stream().filter(i -> i.targets.get(0) == target && i instanceof AttackInteraction).map(Interaction::getActioner).toList();
            //mafia
            if (attackers.stream().filter(a -> a instanceof MafiaCharacter).toList().size() > 0)
                spy.AddNightResult("Your target was attacked by a member of the Mafia!");
            //sk
            if (attackers.stream().filter(a -> a instanceof SerialKiller).toList().size() > 0)
                spy.AddNightResult("Your target was attacked by a Serial Killer!");

            //bodyguard
            if (attackers.stream().filter(a -> a instanceof SerialKiller).toList().size() > 0)
                spy.AddNightResult("Your target was attacked by a Bodyguard!");

            //burned
            if (attackers.stream().filter(a -> a instanceof Arsonist).toList().size() > 0)
                spy.AddNightResult("Your target was set on fire by an arsonist!");

            //visiting actions

            //blackmailed
            var visitors = interactions.stream().filter(i -> i.targets.get(0) == target && i instanceof VisitingInteraction).map(Interaction::getActioner).toList();

            //doctor
            if (visitors.stream().filter(a -> a instanceof Doctor).toList().size() > 0)
                spy.AddNightResult("Your target was healed by a doctor");

            //framer
            if (visitors.stream().filter(a -> a instanceof Framer).toList().size() > 0)
                spy.AddNightResult("Your target was framed!");

            //blackmailer
            if (visitors.stream().filter(a -> a instanceof Blackmailer).toList().size() > 0)
                spy.AddNightResult("Your target was blackmailed!");

        }
    }

    void computeLookoutMessage() {
        //fac sa vada pur si simplu toti oamenii care viziteaza pe cnv seara
        //mi-e neclar daca vede usernameul sau rolul, o sa pun usernameul
        var lookoutInteractions = interactions.stream().filter(i -> i.actioner instanceof Lookout).toList();

        //iau pe rand
        for (var lookoutInteraction : lookoutInteractions) {
            var lookout = lookoutInteraction.actioner;

            if(lookoutInteraction.targets.isEmpty())
                continue;

            //iau targetul
            var target = lookoutInteraction.targets.get(0);
            //vad daca mai mult de 3 oameni viziteaza
            var visitors = target.visitors;

            if (visitors.size() == 0) {
                lookout.AddNightResult("Your target was not visited by anyone.");
                continue;
            }
            if (visitors.size() > 3) {
                lookout.AddNightResult("Your target was visited by more than 3 people last night! You could only identify 3");
            }
            for (var visitor : visitors) {
                lookout.AddNightResult("Your target was visited by " + visitor.getPlayerUsername() + " last night");
            }
        }
    }
    private void computeVeteranInteraction(){
        Interaction veteranInteraction = interactions.stream().filter(i -> i.actioner instanceof Veteran).filter(i-> i.actioner.isAlive()).findFirst().orElse(null);
        if (veteranInteraction == null) return;
        interactions.remove(veteranInteraction);
        Veteran veteran = (Veteran) veteranInteraction.actioner;
        if(veteran.onAlert) interactions.addAll(
                veteran.visitors.stream().map(visitor -> new VeteranPassiveAttackInteraction(veteran, List.of(visitor))).toList()
        );
    }
    //o sa pun la loc dar vreau sa remodelez cumva si vr sa vad cum

//    private void computeWerewolfInteraction() {
//        //iau interactiunea
//        var werewolfInteractionList = interactions.stream()
//                .filter(i -> i.actioner instanceof Werewolf).toList();
//
//        if(werewolfInteractionList.isEmpty())
//            return;
//        var werewolfInteraction = werewolfInteractionList.get(0);
//
//        //iau targetul
//        var target = werewolfInteraction.targets.get(0);
//        //iau werewolful
//        var werewolf = werewolfInteraction.actioner;
//
//        //vad cine a mai vizitat si adaug attack interaction: mai bine le adaug separat
//        //  interactions.add(new AttackInteraction(werewolf, target.visitors, 5));
//        target.visitors.forEach(visitor -> interactions.add(new AttackInteraction(werewolf, List.of(visitor), 5)));
//
//        //adaug in lista de targets
//        werewolf.targets.addAll(target.visitors);
//    }
}