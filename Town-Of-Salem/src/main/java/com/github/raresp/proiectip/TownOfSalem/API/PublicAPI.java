package com.github.raresp.proiectip.TownOfSalem.API;

import com.github.raresp.proiectip.TownOfSalem.exceptions.LobbyNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.models.Lobby;
import com.github.raresp.proiectip.TownOfSalem.repositories.LobbyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class PublicAPI {
    private final LobbyRepository lobbyRepository;

    PublicAPI(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    @GetMapping("/lobbies")
    List<Lobby> all() {
        return lobbyRepository.findAll();
    }

    @GetMapping("/lobbies/{id}")
    Lobby one(@PathVariable UUID id) throws LobbyNotFoundException {
        return lobbyRepository.findById(id)
                .orElseThrow(() -> new LobbyNotFoundException(id.toString()));
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
}
