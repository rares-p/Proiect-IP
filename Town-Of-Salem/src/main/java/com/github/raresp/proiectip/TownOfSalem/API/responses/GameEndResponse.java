package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.ArrayList;
import java.util.List;

public class GameEndResponse {
    public List<String> results = new ArrayList<>();

    public GameEndResponse(Game game) {
        for (Character c : game.getCharacters()) {
            if (game.winners.contains(c))
                results.add(c.getPlayerUsername() + " (" + c.getRole() + ") has won!");
            else
                results.add(c.getPlayerUsername() + " (" + c.getRole() + ") has lost!");
        }
    }
}
