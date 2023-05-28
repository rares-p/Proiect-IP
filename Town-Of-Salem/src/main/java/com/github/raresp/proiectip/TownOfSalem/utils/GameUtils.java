package com.github.raresp.proiectip.TownOfSalem.utils;

import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidLobbyException;
import com.github.raresp.proiectip.TownOfSalem.models.Lobby;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameUtils {
    public static List<Character> generateCharacters(List<String> playerNames) throws InvalidLobbyException {
        Collections.shuffle(playerNames);
        List<Character> characters = new ArrayList<>();
        if (playerNames.size() < Lobby.MINIMUM_PLAYERS)
            throw new InvalidLobbyException("Not enough players");

        int numberOfNeutralPlayers;
        numberOfNeutralPlayers = (playerNames.size()) / 5;
        int numberOfMafiaPlayers;
        numberOfMafiaPlayers = (playerNames.size()) / 4;
        int numberOfTownPlayers;
        numberOfTownPlayers = ((playerNames.size()) - numberOfNeutralPlayers) - numberOfMafiaPlayers;

        Random random = new Random();
        boolean randomBool1 = random.nextBoolean();
        int randomInt1 = random.nextInt(3);


        for (int i = 0; i < numberOfTownPlayers; i++) {
            switch (i) {
                case 0 -> characters.add(new Bodyguard(playerNames.get(0)));
                case 1 -> characters.add(new Veteran(playerNames.get(0)));
                case 2 -> characters.add(new Doctor(playerNames.get(0)));
                case 3 -> characters.add(new Sheriff(playerNames.get(0)));
                case 4 -> characters.add(new Escort(playerNames.get(0)));
                default -> {
                    int randomRole = random.nextInt(6);
                    switch (randomRole) {
                        case 0 -> characters.add(new Vigilante(playerNames.get(0)));
                        case 1 -> characters.add(new Spy(playerNames.get(0)));
                        case 2 -> characters.add(new Lookout(playerNames.get(0)));
                        case 3 -> characters.add(new Doctor(playerNames.get(0)));
                        case 4 -> characters.add(new Escort(playerNames.get(0)));
                        case 5 -> characters.add(new Sheriff(playerNames.get(0)));
                        default -> characters.add(new Sheriff(playerNames.get(0)));
                    }
                }
            }
            playerNames.remove(playerNames.get(0));
        }

        for (int i = 0; i < numberOfNeutralPlayers; i++) {
            switch (i) {
                case 0 -> {
                    Executioner executioner = new Executioner(playerNames.get(0));
                    int randomTarget = random.nextInt(numberOfNeutralPlayers);
                    executioner.target = characters.get(randomTarget);
                    characters.add(executioner);
                }
                case 1 -> {
                    int randomNeutral = random.nextInt(5);
                    switch (randomNeutral) {
                        case 0 -> characters.add(new Arsonist(playerNames.get(0)));
                        case 1 -> characters.add(new Jester(playerNames.get(0)));
                        case 2 -> characters.add(new SerialKiller(playerNames.get(0)));
                        case 3 -> characters.add(new Werewolf(playerNames.get(0)));
                        case 4 -> characters.add(new Survivor(playerNames.get(0)));
                    }
                }
            }
            playerNames.remove(playerNames.get(0));
        }

        for (int i = 0; i < numberOfMafiaPlayers; i++) {
            switch (i) {
                case 0 -> characters.add(new GodFather(playerNames.get(0)));
                case 1 -> characters.add(new Mafioso(playerNames.get(0)));
                default -> {
                    int randomMafia = random.nextInt(numberOfMafiaPlayers);
                    switch (randomMafia) {
                        case 0 -> characters.add(new Framer(playerNames.get(0)));
                        case 1 -> characters.add(new Consigliere(playerNames.get(0)));
                        case 2 -> characters.add(new Consort(playerNames.get(0)));
                    }
                }
            }
            playerNames.remove(playerNames.get(0));
        }
        return characters;

    /*public static List<Character> generateCharacters(List<String> playerNames) {
        List<Character> characters = new ArrayList<>();
        if(playerNames.size() < Lobby.MINIMUM_PLAYERS)
            throw new InvalidLobbyException();
        for(int i = 0; i < playerNames.size(); i ++) {
            switch (i) {
                case 0:
                    characters.add(new Doctor(playerNames.get(i)));
                    break;
                case 1:
                    characters.add(new Mafioso(playerNames.get(i)));
                    break;
                case 2:
                    characters.add(new Escort(playerNames.get(i)));
                    break;
                case 3:
                    characters.add(new Escort(playerNames.get(i)));
                    break;
                case 4:
                    characters.add(new Veteran(playerNames.get(i)));
                    break;
                case 5:
                    characters.add(new GodFather(playerNames.get(i)));
                    break;
                case 6:
                    characters.add(new Vigilante(playerNames.get(i)));
                    break;
                case 7:
                    characters.add(new Arsonist(playerNames.get(i)));
                    break;
                case 8:
                    characters.add(new Consigliere(playerNames.get(i)));
                    break;
                case 9:
                    characters.add(new SerialKiller(playerNames.get(i)));
                    break;
            }
        }
        return characters;
    }*/
    }
}
