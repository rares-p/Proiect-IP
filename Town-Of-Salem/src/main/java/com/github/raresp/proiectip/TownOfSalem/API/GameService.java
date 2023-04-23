package com.github.raresp.proiectip.TownOfSalem.API;

import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicGame;
import com.github.raresp.proiectip.TownOfSalem.exceptions.GameNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.GameState;
import com.github.raresp.proiectip.TownOfSalem.repositories.GameRepositoryAll;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    GameRepositoryAll gameRepository;

    public GameService(GameRepositoryAll gameRepository){
        this.gameRepository = gameRepository;
    }

    @Transactional
    public void updateGameState(Long id,GameState gameState) {
        Game game = gameRepository.findPublicGameById(id);
        game.gameState = gameState;
        gameRepository.save(game);
    }
}
