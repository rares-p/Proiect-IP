package com.github.raresp.proiectip.TownOfSalem.API;

import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicGame;
import com.github.raresp.proiectip.TownOfSalem.API.responses.IndividualGameResponse;
import com.github.raresp.proiectip.TownOfSalem.exceptions.CharacterNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.GameNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.repositories.GameRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameAPI {
    private final GameRepository gameRepository;

    GameAPI(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/game/{id}")
    PublicGame lobbyByID(@PathVariable Long id) {
        return gameRepository.findPublicGameById(id);
    }

    @GetMapping("/game/{id}/admin")
    Game lobbyByIDAdmin(@PathVariable Long id) throws GameNotFoundException {
        return gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
    }

    @GetMapping("/game/{id}/user/{username}")
    IndividualGameResponse gameByIDUsername(@PathVariable Long id, @PathVariable String username) throws GameNotFoundException, CharacterNotFoundException {
        Game game = gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
        Character character = game.getCharacterByName(username);
        return new IndividualGameResponse(game, character);
    }

}
