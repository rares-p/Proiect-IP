package com.github.raresp.proiectip.TownOfSalem;

import com.github.raresp.proiectip.TownOfSalem.models.TurnInteractions;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.Consort;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.GodFather;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.Mafioso;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Arsonist;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.SerialKiller;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Werewolf;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TurnInteractionsTest {
    Arsonist arsonist = new Arsonist("arsonist");
    Bodyguard bodyguard = new Bodyguard("bodyguard");
    Consort consort = new Consort("consort");
    Doctor doctor = new Doctor("doctor");
    Escort escort = new Escort("escort");
    GodFather godFather = new GodFather("godfather");
    Lookout lookout = new Lookout("lookout");
    Mafioso mafioso = new Mafioso("mafioso");
    SerialKiller serialKiller = new SerialKiller("serial killer");
    Veteran veteran = new Veteran("veteran");
    Vigilante vigilante = new Vigilante("vigilante");
    Werewolf werewolf = new Werewolf("werewolf");
    List<Character> characters ;//  = List.of(arsonist, bodyguard, doctor, escort, godFather, lookout, mafioso, serialKiller, veteran, vigilante, werewolf) ;
    @Test
    void GfMafVetTest() {

        //bodyguard.targets.add(doctor);
        //doctor.targets.add(bodyguard);
        //escort.targets.add(werewolf);
        godFather.targets.add(veteran);
        lookout.targets.add(doctor);
        mafioso.targets.add(doctor);
        veteran.targets.add(vigilante);
        //serialKiller.targets.add(doctor);
        //vigilante.targets.add(doctor);
        //werewolf.targets.add(doctor);

        characters = List.of(doctor, godFather, lookout, mafioso, veteran);

        TurnInteractions turnInteractions = new TurnInteractions(characters, true);
        turnInteractions.computeInteractionsOutcome();

        for (Character character : characters)
            System.out.println(character.getPlayerUsername() + ": " + character.nightResults);

    }

    @Test
    void BgMafVigTest() {

        bodyguard.targets.add(doctor);
        //doctor.targets.add(bodyguard);
        //escort.targets.add(werewolf);
        //godFather.targets.add(doctor);
        lookout.targets.add(doctor);
        mafioso.targets.add(doctor);
        //veteran.targets.add(vigilante);
        //serialKiller.targets.add(doctor);
        vigilante.targets.add(doctor);
        //werewolf.targets.add(doctor);

        characters = List.of(bodyguard, doctor, godFather, lookout, mafioso, vigilante);

        TurnInteractions turnInteractions = new TurnInteractions(characters, true);
        turnInteractions.computeInteractionsOutcome();

        for (Character character : characters)
            System.out.println(character.getPlayerUsername() + ": " + character.nightResults);

    }

    @Test
    void EscGfMafTest() {
        bodyguard.targets.add(serialKiller);
        //doctor.targets.add(bodyguard);
        escort.targets.add(godFather);
        godFather.targets.add(bodyguard);
        lookout.targets.add(doctor);
        mafioso.targets.add(doctor);
        //veteran.targets.add(vigilante);
        //serialKiller.targets.add(doctor);
        //vigilante.targets.add(doctor);
        //werewolf.targets.add(doctor);
        characters = List.of(bodyguard, doctor, escort, godFather, lookout, mafioso, serialKiller);

        TurnInteractions turnInteractions = new TurnInteractions(characters, true);
        turnInteractions.computeInteractionsOutcome();

        for (Character character : characters)
            System.out.println(character.getPlayerUsername() + ": " + character.nightResults);

    }

    @Test
    void EscWwTest() {
        //bodyguard.targets.add(doctor);
        doctor.targets.add(doctor);
        escort.targets.add(werewolf);
        //godFather.targets.add(bodyguard);
        lookout.targets.add(werewolf);
        mafioso.targets.add(doctor);
        //veteran.targets.add(vigilante);
        //serialKiller.targets.add(doctor);
        //vigilante.targets.add(doctor);
        werewolf.targets.add(doctor);
        characters = List.of(doctor, escort, lookout, mafioso, werewolf);

        TurnInteractions turnInteractions = new TurnInteractions(characters, true);
        turnInteractions.computeInteractionsOutcome();

        for (Character character : characters)
            System.out.println(character.getPlayerUsername() + ": " + character.nightResults);

    }

    @Test
    void ConsEscSkTest() {
        //bodyguard.targets.add(doctor);
        //doctor.targets.add(doctor);
        consort.targets.add(serialKiller);
        escort.targets.add(serialKiller);
        consort.targets.add(serialKiller);
        //godFather.targets.add(bodyguard);
        lookout.targets.add(doctor);
       // mafioso.targets.add(doctor);
        //veteran.targets.add(vigilante);
        serialKiller.targets.add(doctor);
        //vigilante.targets.add(doctor);
        //werewolf.targets.add(doctor);
        characters = List.of(consort, doctor, escort, lookout, serialKiller);

        TurnInteractions turnInteractions = new TurnInteractions(characters, true);
        turnInteractions.computeInteractionsOutcome();

        for (Character character : characters)
            System.out.println(character.getPlayerUsername() + ": " + character.nightResults);

    }

    @Test
    void VigSuicideTest() {
        vigilante.targets.add(bodyguard);
        characters = List.of(bodyguard, doctor, escort, lookout, mafioso, vigilante);

        TurnInteractions turnInteractions = new TurnInteractions(characters, true);
        turnInteractions.computeInteractionsOutcome();

        for (Character character : characters)
            System.out.println(character.getPlayerUsername() + ": " + character.nightResults);

        for (Character character : characters)
            character.resetStats();

        vigilante.targets.add(escort);

		turnInteractions = new TurnInteractions(characters, false);
		turnInteractions.computeInteractionsOutcome();

        System.out.println();
        for (Character character : characters)
            System.out.println(character.getPlayerUsername() + ": " + character.nightResults);

    }

    @Test
    void EscLkTest() {
        escort.targets.add(mafioso);
        lookout.targets.add(doctor);
        mafioso.targets.add(doctor);
        characters = List.of(doctor, escort, lookout, mafioso);

        TurnInteractions turnInteractions = new TurnInteractions(characters, true);
        turnInteractions.computeInteractionsOutcome();

        for (Character character : characters)
            System.out.println(character.getPlayerUsername() + ": " + character.nightResults);

    }

}
