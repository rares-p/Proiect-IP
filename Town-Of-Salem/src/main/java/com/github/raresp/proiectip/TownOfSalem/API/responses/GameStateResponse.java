package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicGame;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.GameState;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.List;

public class GameStateResponse {
    private Game game;
    public GameStateResponse(com.github.raresp.proiectip.TownOfSalem.models.Game game) {
        this.game = game;
    }

    public PublicGame getGame() {
        ProjectionFactory pf = new SpelAwareProxyProjectionFactory();
        PublicGame publicGame = pf.createProjection(PublicGame.class, this.game);
        System.out.println("STATE: " + game.getGameState());
        return publicGame;
    }
}
