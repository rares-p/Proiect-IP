package com.github.raresp.proiectip.TownOfSalem.utils;

import com.github.raresp.proiectip.TownOfSalem.API.GameService;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameManager {
    @Autowired
    private GameService gameService;

    public GameManager() {}

    public void setGameState(Game game, GameState gameState) {
        game.setGameState(gameState);
        gameService.updateGameState(game.getId(), gameState);
    }
}
