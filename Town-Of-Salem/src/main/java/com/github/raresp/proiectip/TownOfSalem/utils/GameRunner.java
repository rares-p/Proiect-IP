package com.github.raresp.proiectip.TownOfSalem.utils;

import com.github.raresp.proiectip.TownOfSalem.repositories.GameService;
import com.github.raresp.proiectip.TownOfSalem.exceptions.GameNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.GameState;
import com.github.raresp.proiectip.TownOfSalem.models.TurnInteractions;
import com.github.raresp.proiectip.TownOfSalem.models.VotingSession;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.SelectionSession;
import com.github.raresp.proiectip.TownOfSalem.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class GameRunner{
    @Autowired
    private GameService gameService;
    @Autowired
    private GameRepository gameRepository;
    ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
    private TaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);

    public GameRunner() {}

    public void StartGame(Long gameId) throws GameNotFoundException {
            CronTrigger cronTrigger
                    = new CronTrigger("0/1 * * * * ?");
            Game onlyGame = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException());
            //gameRepository.deleteAll();
            gameRepository.save(onlyGame);
            scheduler.schedule(() -> runGame(gameId), cronTrigger);
    }

    public void runGame(Long gameId) {
        Game game = null;
        try {
            game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException());
        } catch (GameNotFoundException e) {
            return;
        }
        System.out.println("game state in run game: " + game.gameState);
        game.gameState = game.getGameState();

        if(game.getGameState() == GameState.Selection) {
            SelectionSession selectionSession = new SelectionSession(game.getCharacters());
            game.selectedCharacter = selectionSession.calculateOutcome();

            System.out.println(game.getCharacters());

            if(game.selectedCharacter != null) {
                System.out.println(game.selectedCharacter + " has been selected");
                game.setGameState(GameState.Voting);

                for(Character c : game.getCharacters())
                    c.targets.clear();
                game.setTimeOfCurrentState(game.getTimeOfState());

                game.remainingTimeOfSelection = game.getTimeOfCurrentState().plusSeconds(game.votingTime);

                gameRepository.save(game);//gameService.updateGame(game);
                return;
            }
        }

        if (game.getTimeOfCurrentState().compareTo(Instant.now()) > 0)
            return;
        System.out.println("game state trebuie schimbat");

        switch (game.getGameState()) {
            case Night:
                runGameIfNightTime(game);
                break;
            case NightEnding:
                runGameIfNightEndingTime(game);
                break;
            case Voting:
                runGameIfVotingTime(game);
                break;
            case Discussion:
                runGameIfDiscussionTime(game);
                break;
            case DayEnding:
                runGameIfDayEndingTime(game);
                break;
            case Selection:
                runGameIfSelectionTime(game);
                break;
        }
        for(Character c : game.getCharacters())
            c.targets.clear();

        if(game.getGameState() == GameState.Selection && game.remainingTimeOfSelection != null) {
            game.setTimeOfCurrentState(game.remainingTimeOfSelection);
            game.remainingTimeOfSelection = null;
        }
        else
            game.setTimeOfCurrentState(game.getTimeOfState());

        gameRepository.save(game);//gameService.updateGame(game);
    }

    private void runGameIfDayEndingTime(Game game) {
        game.setGameState(GameState.Night);
    }

    private void runGameIfNightEndingTime(Game game) {
        for (Character c : game.getCharacters())
            c.resetStats();
        game.setGameState(GameState.Discussion);
        gameService.updateGame(game);
    }

    private void runGameIfDiscussionTime(Game game) {
        game.setGameState(GameState.Selection);
    }

    private void runGameIfVotingTime(Game game) {
        VotingSession votingSession = new VotingSession(game.selectedCharacter, game.getCharacters());
        if(votingSession.calculateOutcome()) game.selectedCharacter.setIsAlive(false);
        game.votingLog = votingSession.getVotes();
        //game.sendVotingResults(votingSession.getVotes());

        game.setGameState(GameState.Selection);
        System.out.println(game.votingLog);
        gameService.updateGame(game);
    }

    private void runGameIfNightTime(Game game) {
        game.selectedCharacter = null;
        TurnInteractions turnInteractions = new TurnInteractions(game.getCharacters());
        turnInteractions.computeInteractionsOutcome();
        game.setGameState(GameState.NightEnding);
        gameService.updateGame(game);

        //for(Character c : game.getCharacters())
          //  c.resetStats();
    }


    private void runGameIfSelectionTime(Game game) {
        System.out.println(game.getCharacters());

        SelectionSession selectionSession = new SelectionSession(game.getCharacters());
        game.selectedCharacter = selectionSession.calculateOutcome();

        System.out.println(game.getCharacters());

        if(game.selectedCharacter == null) {
            System.out.println("Nobody has been selected");
            game.setGameState(GameState.Night);
        }
        else {
            System.out.println(game.selectedCharacter + " has been selected");
            game.setGameState(GameState.Voting);
        }
        System.out.println(game.getCharacters());
    }
}
