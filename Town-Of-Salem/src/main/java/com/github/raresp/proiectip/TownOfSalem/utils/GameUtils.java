package com.github.raresp.proiectip.TownOfSalem.utils;

import com.github.raresp.proiectip.TownOfSalem.models.Lobby;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.Mafioso;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Doctor;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Escort;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Sheriff;

import java.util.ArrayList;
import java.util.List;

public class GameUtils {
    public static List<Character> generateCharacters(List<String> playerNames) {
        List<Character> characters = new ArrayList<>();
        if(playerNames.size() < Lobby.MINIMUM_PLAYERS)
            throw new RuntimeException();
        characters.add(new Sheriff(playerNames.get(0)));
        characters.add(new Escort(playerNames.get(1)));
        characters.add(new Mafioso(playerNames.get(2)));
        characters.add(new Doctor(playerNames.get(3)));
        return characters;
    }
}
