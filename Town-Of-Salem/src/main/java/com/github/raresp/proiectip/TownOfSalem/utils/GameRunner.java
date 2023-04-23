package com.github.raresp.proiectip.TownOfSalem.utils;

import com.github.raresp.proiectip.TownOfSalem.API.GameService;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.GameState;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameRunner {
    @Autowired
    //private ApplicationContext applicationContext;
    private GameService gameService;

    public Game game;

    public GameRunner(Game game){
        this.game = game;
        //gameService = applicationContext.getBean(GameService.class);
    }
    public void setGameState(GameState gameState){
        //gameService = new GameService();
        gameService.updateGameState(game.getId(), gameState);
    }
}
