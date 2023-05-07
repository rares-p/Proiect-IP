package com.github.raresp.proiectip.TownOfSalem.API;

import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicLobby;
import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicLobbyListProjection;
import com.github.raresp.proiectip.TownOfSalem.API.requests.AddCharacterTargetRequest;
import com.github.raresp.proiectip.TownOfSalem.API.requests.AddUserToLobbyRequest;
import com.github.raresp.proiectip.TownOfSalem.API.responses.CurrentUserAllUsersResponse;
import com.github.raresp.proiectip.TownOfSalem.API.responses.PeersResponse;
import com.github.raresp.proiectip.TownOfSalem.exceptions.*;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.GameState;
import com.github.raresp.proiectip.TownOfSalem.models.Lobby;
import com.github.raresp.proiectip.TownOfSalem.models.LobbyState;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.repositories.GameRepository;
import com.github.raresp.proiectip.TownOfSalem.repositories.LobbyRepository;
import com.github.raresp.proiectip.TownOfSalem.utils.GameRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class LobbyAPI {
    private final LobbyRepository lobbyRepository;
    private final GameRepository gameRepository;
    @Autowired
    private GameRunner gameRunner;

    LobbyAPI(LobbyRepository lobbyRepository, GameRepository gameRepository) {
        this.lobbyRepository = lobbyRepository;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/lobbies")
    List<PublicLobbyListProjection> allLobbies() {
        return lobbyRepository.findBy();
    }

    @GetMapping("/lobbies/{joinCode}")
    PublicLobby lobbyByID(@PathVariable String joinCode) throws LobbyNotFoundException {
        PublicLobby publicLobby = lobbyRepository.findPublicLobbyByJoinCode(joinCode);
        if (publicLobby == null)
            throw new LobbyNotFoundException(joinCode);
        return publicLobby;
    }

    @PostMapping(path = "/lobbies",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Lobby> createLobby() {
        Lobby lobby = new Lobby();
        lobbyRepository.save(lobby);
        return new ResponseEntity<>(lobby, HttpStatus.CREATED);
    }

    @PostMapping(path = "/lobbies/{joinCode}/add_user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Lobby> addUserToLobby(@PathVariable String joinCode, @RequestBody AddUserToLobbyRequest request) throws LobbyNotFoundException, InvalidLobbyException {
        Lobby lobby = lobbyRepository.findLobbyByJoinCode(joinCode);
        if(lobby == null)
            throw new LobbyNotFoundException(joinCode);
        lobby.addPlayerInLobby(request.userId);
        lobbyRepository.save(lobby);
        return new ResponseEntity<>(lobby, HttpStatus.OK);
    }

    @PostMapping(path = "/lobbies/{joinCode}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Lobby> addUserToLobbySprint1(@PathVariable String joinCode, @RequestBody AddUserToLobbyRequest request) throws LobbyNotFoundException, InvalidLobbyException {
        Lobby lobby = lobbyRepository.findLobbyByJoinCode(joinCode);
        if(lobby == null)
            throw new LobbyNotFoundException(joinCode);
        lobby.addPlayerInLobby(request.userId);
        lobbyRepository.save(lobby);
        return new ResponseEntity<>(lobby, HttpStatus.OK);
    }

    @PostMapping(path = "/lobbies/{joinCode}/remove_user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Lobby> removeUserFromLobby(@PathVariable String joinCode, @RequestBody AddUserToLobbyRequest request) throws LobbyNotFoundException, InvalidLobbyException, InvalidCharacterException, CharacterNotFoundException {
        Lobby lobby = lobbyRepository.findLobbyByJoinCode(joinCode);
        if(lobby == null)
            throw new LobbyNotFoundException(joinCode);
        lobby.removePlayerFromLobby(request.userId);
        lobbyRepository.save(lobby);
        return new ResponseEntity<>(lobby, HttpStatus.OK);
    }

    @DeleteMapping(path = "/lobbies/{joinCode}")
    void deleteLobby(@PathVariable String joinCode) throws LobbyNotFoundException {
        Lobby lobby = lobbyRepository.findLobbyByJoinCode(joinCode);
        if(lobby == null)
            throw new LobbyNotFoundException(joinCode);
        lobbyRepository.delete(lobby);
    }

    @PostMapping(path = "/lobbies/{joinCode}/start_game")
    Lobby startGame(@PathVariable String joinCode) throws LobbyNotFoundException, InvalidLobbyException, GameNotFoundException {
        Lobby lobby = lobbyRepository.findLobbyByJoinCode(joinCode);
        if(lobby == null)
            throw new LobbyNotFoundException(joinCode);
        lobby.startGame();
        lobbyRepository.save(lobby);
        gameRunner.StartGame(lobby.getGame().getId());
        return lobby;
    }

    @GetMapping(path="/lobbies/{joinCode}/peers")
    PeersResponse getPeers(@PathVariable String joinCode, @RequestParam String userId) throws LobbyNotFoundException, CharacterNotFoundException {
        Lobby lobby = lobbyRepository.findLobbyByJoinCode(joinCode);
        if(lobby == null)
            throw new LobbyNotFoundException(joinCode);
        PeersResponse response = new PeersResponse();
        Character character = lobby.getGame().getCharacterByName(userId);
        if(character == null)
            throw new CharacterNotFoundException();
        Game game = lobby.getGame();
        if(game.gameState == GameState.Night) {
            for(Character c : game.getPeers(character))
                response.peers.add(c.getPlayerUsername());
        }
        else
            for(Character c : lobby.getGame().getCharacters())
                response.peers.add(c.getPlayerUsername());
        return response;
    }

    @GetMapping(path = "/gamestate/{joinCode}")
    Lobby gamestate(@PathVariable String joinCode) throws LobbyNotFoundException {
        Lobby lobby = lobbyRepository.findLobbyByJoinCode(joinCode);
        if(lobby == null)
            throw new LobbyNotFoundException(joinCode);
        return lobby;
    }

    @GetMapping("/state/{joinCode}")
    ResponseEntity<?> characterStateByUsernameFromLobby(@PathVariable String joinCode, @RequestParam String userId) throws CharacterNotFoundException, LobbyNotFoundException {
        Lobby lobby = lobbyRepository.findLobbyByJoinCode(joinCode);
        if(lobby == null)
            throw new LobbyNotFoundException(joinCode);
        if(userId == null) {
            return ResponseEntity.ok(lobby.getGame());
        }
        if(lobby.getState() == LobbyState.WAITING_PLAYERS)
            return ResponseEntity.ok("{\"state\":\"Lobby\"}");
        Character character = lobby.getGame().getCharacterByName(userId);
        ResponseEntity<?> resp = ResponseEntity.ok(new CurrentUserAllUsersResponse(lobby.getGame(), character));
        if(lobby.getGame().gameState == GameState.NightEnding)
            lobby.getGame().getCharacterByName(userId).targets.clear();
        gameRepository.save(lobby.getGame());
        return resp;
    }

    @PostMapping(path = "/state/{joinCode}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Character> postGameByLobbyId(@PathVariable String joinCode, @RequestBody AddCharacterTargetRequest request) throws GameNotFoundException, CharacterNotFoundException, LobbyNotFoundException {
        Lobby lobby = lobbyRepository.findLobbyByJoinCode(joinCode);
        if(lobby == null)
            throw new LobbyNotFoundException(joinCode);
        Game game = lobby.getGame();
        Character character = game.getCharacterByName(request.userId);

        character.targets.clear();
        for(String username : request.targets)
            game.getCharacterByName(request.userId).targets.add(game.getCharacterByName(username));
        gameRepository.save(game);
        return new ResponseEntity<>(character, HttpStatus.OK);
    }
}