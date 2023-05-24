package com.github.raresp.proiectip.TownOfSalem.utils;

import com.github.raresp.proiectip.TownOfSalem.models.Lobby;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.Consigliere;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.GodFather;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.Mafioso;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Arsonist;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.SerialKiller;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.*;

import java.util.ArrayList;
import java.util.List;

public class GameUtils {
    public static List<Character> generateCharacters(List<String> playerNames) {
        List<Character> characters = new ArrayList<>();
        if(playerNames.size() < Lobby.MINIMUM_PLAYERS)
            throw new RuntimeException();
        for(int i = 0; i < playerNames.size(); i ++) {
            switch (i) {
                case 0:
                    characters.add(new Sheriff(playerNames.get(i)));
                    break;
                case 1:
                    characters.add(new Mafioso(playerNames.get(i)));
                    break;
                case 2:
                    characters.add(new Mafioso(playerNames.get(i)));
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
    }
}
