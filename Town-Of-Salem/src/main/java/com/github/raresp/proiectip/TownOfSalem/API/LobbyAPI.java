package com.github.raresp.proiectip.TownOfSalem.API;

import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicLobby;
import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicLobbyListProjection;
import com.github.raresp.proiectip.TownOfSalem.API.requests.AddUserToLobbyRequest;
import com.github.raresp.proiectip.TownOfSalem.exceptions.CharacterNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidCharacterException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidLobbyException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.LobbyNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.models.Lobby;
import com.github.raresp.proiectip.TownOfSalem.repositories.LobbyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class LobbyAPI {
    private final LobbyRepository lobbyRepository;

    LobbyAPI(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    @GetMapping("/lobbies")
    List<PublicLobbyListProjection> allLobbies() {
        return lobbyRepository.findBy();
    }

    @GetMapping("/lobbies/{id}")
    PublicLobby lobbyByID(@PathVariable UUID id) throws LobbyNotFoundException {
        return lobbyRepository.findPublicLobbyById(id);
    }

    @PostMapping(path = "/lobbies",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Lobby> createLobby() {
        Lobby lobby = new Lobby();
        lobbyRepository.save(lobby);
        return new ResponseEntity<>(lobby, HttpStatus.CREATED);
    }

    @PostMapping(path = "/lobbies/{id}/add_user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Lobby> addUserToLobby(@PathVariable UUID id, @RequestBody AddUserToLobbyRequest request) throws LobbyNotFoundException, InvalidLobbyException {
        Lobby lobby = lobbyRepository.findById(id)
                .orElseThrow(() -> new LobbyNotFoundException(id.toString()));
        lobby.addPlayerInLobby(request.username);
        lobbyRepository.save(lobby);
        return new ResponseEntity<>(lobby, HttpStatus.OK);
    }

    @PostMapping(path = "/lobbies/{id}/remove_user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Lobby> removeUserFromLobby(@PathVariable UUID id, @RequestBody AddUserToLobbyRequest request) throws LobbyNotFoundException, InvalidLobbyException, InvalidCharacterException, CharacterNotFoundException {
        Lobby lobby = lobbyRepository.findById(id)
                .orElseThrow(() -> new LobbyNotFoundException(id.toString()));
        lobby.removePlayerFromLobby(request.username);
        lobbyRepository.save(lobby);
        return new ResponseEntity<>(lobby, HttpStatus.OK);
    }

    @DeleteMapping(path = "/lobbies/{id}")
    void deleteLobby(@PathVariable UUID id) throws LobbyNotFoundException {
        Lobby lobby = lobbyRepository.findById(id)
                .orElseThrow(() -> new LobbyNotFoundException(id.toString()));
        lobbyRepository.delete(lobby);
    }

    @PostMapping(path = "/lobbies/{id}/start_game")
    Lobby startGame(@PathVariable UUID id) throws LobbyNotFoundException, InvalidLobbyException {
        Lobby lobby = lobbyRepository.findById(id)
                .orElseThrow(() -> new LobbyNotFoundException(id.toString()));
        lobby.startGame();
        lobbyRepository.save(lobby);
        return lobby;
    }
}