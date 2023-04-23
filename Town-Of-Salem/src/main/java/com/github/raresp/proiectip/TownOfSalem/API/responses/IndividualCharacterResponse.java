package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicGame;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.List;

public class IndividualCharacterResponse {
    private Game game;
    private Character character;

    public IndividualCharacterResponse(Game game, Character character) {
        this.game = game;
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public List<MafiaCharacter> getMafiaCharacters() {
        return game.getMafiaCharactersIfCharacterIsMafia(character);
    }
}
