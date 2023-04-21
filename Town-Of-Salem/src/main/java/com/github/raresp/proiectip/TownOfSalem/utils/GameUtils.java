package com.github.raresp.proiectip.TownOfSalem.utils;

import com.github.raresp.proiectip.TownOfSalem.models.Lobby;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Sheriff;

import java.util.ArrayList;
import java.util.List;

public class GameUtils {
    public static List<Character> generateCharacters(List<String> playerNames) {
        List<Character> characters = new ArrayList<>();
        if(playerNames.size() < Lobby.MINIMUM_PLAYERS)
            throw new RuntimeException();
        characters.add(new Sheriff(playerNames.get(0)));
        characters.add(new Sheriff(playerNames.get(1)));
        characters.add(new Sheriff(playerNames.get(2)));
        return characters;
    }
}
