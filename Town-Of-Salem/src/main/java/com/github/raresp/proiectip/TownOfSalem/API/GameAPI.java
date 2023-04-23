package com.github.raresp.proiectip.TownOfSalem.API;

import com.github.raresp.proiectip.TownOfSalem.API.projections.IndividualGame;
import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicGame;
import com.github.raresp.proiectip.TownOfSalem.API.requests.AddCharacterTargetRequest;
import com.github.raresp.proiectip.TownOfSalem.API.requests.AddUserToLobbyRequest;
import com.github.raresp.proiectip.TownOfSalem.API.responses.GameStateResponse;
import com.github.raresp.proiectip.TownOfSalem.API.responses.IndividualCharacterResponse;
import com.github.raresp.proiectip.TownOfSalem.API.responses.IndividualGameResponse;
import com.github.raresp.proiectip.TownOfSalem.exceptions.CharacterNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.GameNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidLobbyException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.LobbyNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.Lobby;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.repositories.GameRepository;
import com.github.raresp.proiectip.TownOfSalem.utils.GameRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
public class GameAPI {
    private final GameRepository gameRepository;
    @Autowired
    public GameService gameService;
    @Autowired
    private GameRunner gameRunner;

    GameAPI(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/game/{id}")
    PublicGame lobbyByID(@PathVariable Long id) {
        return gameRepository.findPublicGameById(id);
    }

    @GetMapping("/game/{id}/admin")
    Game lobbyByIDAdmin(@PathVariable Long id) throws GameNotFoundException {
        Game game = gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
        gameRunner.runGame(game.getId());
        return game;
    }

    @GetMapping("/game/{id}/user/{username}")
    IndividualGameResponse gameByIDUsername(@PathVariable Long id, @PathVariable String username) throws GameNotFoundException, CharacterNotFoundException {
        Game game = gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
        Character character = game.getCharacterByName(username);
        return new IndividualGameResponse(game, character);
    }

    @GetMapping("/statez/{id}")
    GameStateResponse gameState(@PathVariable Long id) throws GameNotFoundException, CharacterNotFoundException {
        Game game = gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
        gameRepository.save(game);
        System.out.println("GAMEAPI STATE: " + game.gameState);
        return new GameStateResponse(game);
    }

    @GetMapping("/state/{id}")
    IndividualCharacterResponse characterStateByUsername(@PathVariable Long id, @RequestParam String username) throws GameNotFoundException, CharacterNotFoundException {
        Game game = gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
        Character character = game.getCharacterByName(username);
        return new IndividualCharacterResponse(game, character);
    }

    /*@PostMapping(path = "/lobbies/{id}/add_user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Lobby> addUserToLobby(@PathVariable UUID id, @RequestBody AddUserToLobbyRequest request) throws LobbyNotFoundException, InvalidLobbyException {
        Lobby lobby = lobbyRepository.findById(id)
                .orElseThrow(() -> new LobbyNotFoundException(id.toString()));
        lobby.addPlayerInLobby(request.username);
        lobbyRepository.save(lobby);
        return new ResponseEntity<>(lobby, HttpStatus.OK);
    }*/
    @PostMapping(path = "/state/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Character> test(@PathVariable Long id, @RequestBody AddCharacterTargetRequest request) throws GameNotFoundException, CharacterNotFoundException {
        Game game = gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
        Character character = game.getCharacterByName(request.username);
        character.targets.clear();
        for(String username : request.targets)
            game.getCharacterByName(request.username).targets.add(game.getCharacterByName(username));
        for(Character c : game.getCharacters())
            System.out.println(c.targets);
        gameRepository.save(game);
        return new ResponseEntity<>(character, HttpStatus.OK);
    }
}
