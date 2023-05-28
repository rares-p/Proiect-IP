package com.github.raresp.proiectip.TownOfSalem;

import com.github.raresp.proiectip.TownOfSalem.models.TurnInteractions;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.GodFather;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.Mafioso;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Arsonist;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.SerialKiller;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Werewolf;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TownOfSalemApplicationTests {
	@Test
	void contextLoads() {
		//Bodyguard bodyguard = new Bodyguard("bodyguard");
		Doctor doctor = new Doctor("doctor");
		Escort escort = new Escort("escort");
		/*GodFather godFather = new GodFather("godfather");
		Lookout lookout = new Lookout("lookout");
		Mafioso mafioso = new Mafioso("mafioso");
		SerialKiller serialKiller = new SerialKiller("serial killer");
		Veteran veteran = new Veteran("veteran");
		Vigilante vigilante = new Vigilante("vigilante");
		Werewolf werewolf = new Werewolf("werewolf");*/
		Arsonist arsonist = new Arsonist("arsonist");


		/*bodyguard.targets.add(doctor);
		doctor.targets.add(bodyguard);
		//escort.targets.add(werewolf);
		//godFather.targets.add(veteran);
		lookout.targets.add(doctor);
		mafioso.targets.add(doctor);
		veteran.targets.add(vigilante);
		//serialKiller.targets.add(doctor);
		//vigilante.targets.add(doctor);
		//werewolf.targets.add(doctor);*/

		arsonist.targets.add(doctor);
		//List<Character> characters = List.of(bodyguard, doctor, escort, godFather, lookout, mafioso, serialKiller, veteran, vigilante, werewolf);
		TurnInteractions turnInteractions = new TurnInteractions(List.of(doctor, escort, arsonist), true);
		turnInteractions.computeInteractionsOutcome();

		//for(Character character : characters)
		//	System.out.println(character.getPlayerUsername() + ": " + character.nightResults);

		arsonist.targets.clear();
		arsonist.targets.add(arsonist);

		turnInteractions = new TurnInteractions(List.of(doctor, escort, arsonist), true);
		turnInteractions.computeInteractionsOutcome();

		System.out.println(doctor.isAlive() + doctor.nightResults.toString());

//		vigilante.resetStats();
//		doctor.resetStats();
//		mafioso.resetStats();
//		escort.resetStats();
//
//		vigilante.targets.add(escort);
//		mafioso.targets.add(doctor);
//		doctor.targets.add(doctor);
//
//		turnInteractions = new TurnInteractions(List.of(vigilante, mafioso, doctor, escort), false);
//		turnInteractions.computeInteractionsOutcome();
//
//		System.out.println("vigilante: " + vigilante.nightResults);
//		System.out.println("mafioso: " + mafioso.nightResults);
//		System.out.println("doctor: " + doctor.nightResults);
//		System.out.println("escort: " + escort.nightResults);
	}

}
