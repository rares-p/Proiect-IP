package com.github.raresp.proiectip.TownOfSalem.utils;

import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidLobbyException;
import com.github.raresp.proiectip.TownOfSalem.models.Lobby;
import com.github.raresp.proiectip.TownOfSalem.repositories.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDatabase {
    @Autowired
    LobbyRepository lobbyRepository;

    @Bean
    public void init() throws InvalidLobbyException {
        Lobby lobby = lobbyRepository.findLobbyByJoinCode("000000");
        if(lobby == null) {
            lobby = new Lobby();
            lobby.setJoinCode("000000");
            lobbyRepository.save(lobby);
        }
        Lobby lobby1 = lobbyRepository.findLobbyByJoinCode("000001");
        if(lobby1 == null) {
            lobby1 = new Lobby();
            lobby1.setJoinCode("000001");
            lobby1.addPlayerInLobby("test1");
            lobby1.addPlayerInLobby("test2");
            lobby1.addPlayerInLobby("test3");
            lobby1.addPlayerInLobby("test4");
            lobbyRepository.save(lobby1);
            lobby1.startGame();
            lobbyRepository.save(lobby1);
        }
    }
}
