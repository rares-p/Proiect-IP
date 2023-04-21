package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicGame;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.List;

public class IndividualGameResponse {
    private Game game;
    private Character character;

    public IndividualGameResponse(Game game, Character character) {
        this.game = game;
        this.character = character;
    }

    public PublicGame getGame() {
        ProjectionFactory pf = new SpelAwareProxyProjectionFactory();
        PublicGame publicGame = pf.createProjection(PublicGame.class, this.game);
        return publicGame;
    }

    public Character getCharacter() {
        return character;
    }

    public List<MafiaCharacter> getMafiaCharacters() {
        return game.getMafiaCharactersIfCharacterIsMafia(character);
    }
}
