package com.github.raresp.proiectip.TownOfSalem.utils;

import com.github.raresp.proiectip.TownOfSalem.API.GameService;
import com.github.raresp.proiectip.TownOfSalem.exceptions.GameNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.GameState;
import com.github.raresp.proiectip.TownOfSalem.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class GameRunner {
    @Autowired
    private GameService gameService;
    @Autowired
    private GameRepository gameRepository;
    ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
    private TaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);

    public GameRunner() {}

    public void runGame(Long gameId) {
        Game game = null;
        try {
            game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException());
        } catch (GameNotFoundException e) {
            return;
        }
        if (game.getTimeOfCurrentState().compareTo(LocalDateTime.now()) > 0)
            return;
        switch (game.getGameState()) {
            case Night:
                runGameIfNightTime(game);
                break;
            case Voting:
                runGameIfVotingTime(game);
                break;
            case Discussion:
                runGameIfDiscussionTime(game);
                break;
            case Selection:
                runGameIfSelectionTime(game);
                break;
        }
        game.setTimeOfCurrentState(game.getTimeOfState());
        gameService.updateGame(game);
        scheduler.schedule(() -> runGame(gameId), java.sql.Timestamp.valueOf(game.getTimeOfState()));
    }

    private void runGameIfDiscussionTime(Game game) {
        game.setGameState(GameState.Selection);
    }

    private void runGameIfVotingTime(Game game) {
        game.setGameState(GameState.Night);
    }

    private void runGameIfNightTime(Game game) {
        game.setGameState(GameState.Discussion);
    }

    private void runGameIfSelectionTime(Game game) {
        game.setGameState(GameState.Voting);
    }
}
