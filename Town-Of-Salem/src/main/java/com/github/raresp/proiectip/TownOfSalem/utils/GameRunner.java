package com.github.raresp.proiectip.TownOfSalem.utils;

import com.github.raresp.proiectip.TownOfSalem.exceptions.CharacterNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.GodFather;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.Mafioso;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Executioner;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Jester;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
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
            case End:
                gameRepository.delete(game);
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
        for(Character c : game.getCharacters()) {
            c.checkIfCanAct();
            c.setPossibleTargets(game.getCharacters());
        }
        game.setGameState(GameState.Night);
        game.computeNightBeginningAnnouncements();
    }

    private void runGameIfNightEndingTime(Game game) {
        for (Character c : game.getCharacters()) {
            c.resetStats();
        }

        Executioner executioner = (Executioner) game.getCharacters().stream().filter(c -> c instanceof Executioner && c.isAlive()).findFirst().orElse(null);
        System.out.println("EXECUTIOJNERUL " + executioner);
        if(executioner != null)
            if(!executioner.target.isAlive() && !game.winners.contains(executioner)) {

                String username = executioner.getPlayerUsername();
                game.getCharacters().remove(executioner);
                game.getCharacters().add(new Jester(username));
            }

        //for(int i = 0; i < 2; i ++) {
        if (game.getAlivePlayers().stream().noneMatch(gf -> gf instanceof GodFather)) {
            Character mafioso = game.getAlivePlayers().stream().filter(c -> c instanceof Mafioso).findFirst().orElse(null);
            if (mafioso != null) {
                try {
                    String username = mafioso.getPlayerUsername();
                    game.getCharacters().remove(game.getCharacterByName(username));
                    game.getCharacters().add(new GodFather(username));
                } catch (CharacterNotFoundException e) {
                    //ignored
                }
            }
        }

        if (game.getAlivePlayers().stream().noneMatch(maf -> maf instanceof Mafioso)) {
            try {
                List<Character> supportMafiaCharacters = new ArrayList<>(game.getAlivePlayers().stream().filter(c -> c instanceof MafiaCharacter && !(c instanceof Mafioso) && !(c instanceof GodFather)).toList());
                if (supportMafiaCharacters.size() > 0) {
                    String username = supportMafiaCharacters.get(new Random().nextInt(supportMafiaCharacters.size())).getPlayerUsername();
                    game.getCharacters().remove(game.getCharacterByName(username));
                    game.getCharacters().add(new Mafioso(username));
                }
            } catch (CharacterNotFoundException e) {
                //ignored
            }
        }
        //}

        if (game.gameEnded()) {
            game.setGameState(GameState.End);
            gameRepository.save(game);
            return;
        }

        if (game.aliveLastNight > game.getAlivePlayers().size())
            game.daysSinceLastDeath = 0;
        else
            game.daysSinceLastDeath++;

        if (game.daysSinceLastDeath == 3) {
            for (Character c : game.getCharacters()) {
                if (c instanceof Jester || c instanceof Executioner)
                    if (!game.winners.contains(c))
                        game.winners.add(c);
            }
            game.setGameState(GameState.End);
            gameRepository.save(game);
            return;
        }

        game.aliveLastNight = game.getAlivePlayers().size();
        game.setGameState(GameState.Discussion);
        //gameRepository.save(game);
    }

    private void runGameIfDiscussionTime(Game game) {
        game.nightCounter++;
        game.setGameState(GameState.Selection);
    }

    private void runGameIfVotingTime(Game game) {
        boolean voted = false;
        VotingSession votingSession = new VotingSession(game.selectedCharacter, game.getCharacters());
        if(votingSession.calculateOutcome()) {
            voted = true;
            game.selectedCharacter.setIsAlive(false);
            if (game.selectedCharacter instanceof Jester)
                game.winners.add(game.selectedCharacter);
            for (Character c : game.getCharacters())
                if (c instanceof Executioner && ((Executioner) c).target.getPlayerUsername().equals(game.selectedCharacter.getPlayerUsername()))
                    game.winners.add(c);
            game.daysSinceLastDeath = 0;
            if (game.gameEnded()) {
                game.setGameState(GameState.End);
                gameRepository.save(game);
                return;
            }
        }
        game.votingLog = votingSession.getVotes();
        game.sendVotingResults(votingSession.getVotes());
        if(voted)
            game.setGameState(GameState.DayEnding);
        else
            game.setGameState(GameState.Selection);
        System.out.println(game.votingLog);
        gameRepository.save(game);
    }

    private void runGameIfNightTime(Game game) {
        game.selectedCharacter = null;
        TurnInteractions turnInteractions = new TurnInteractions(game.getCharacters(), game.isFullMoonNight());
        turnInteractions.computeInteractionsOutcome();
        game.setGameState(GameState.NightEnding);
        //gameRepository.save(game);
    }


    private void runGameIfSelectionTime(Game game) {
        System.out.println(game.getCharacters());

        SelectionSession selectionSession = new SelectionSession(game.getCharacters());
        game.selectedCharacter = selectionSession.calculateOutcome();

        System.out.println(game.getCharacters());

        if(game.selectedCharacter == null) {
            System.out.println("Nobody has been selected");
            game.setGameState(GameState.DayEnding);
        }
        else {
            System.out.println(game.selectedCharacter + " has been selected");
            game.setGameState(GameState.Voting);
        }
        System.out.println(game.getCharacters());
    }
}
