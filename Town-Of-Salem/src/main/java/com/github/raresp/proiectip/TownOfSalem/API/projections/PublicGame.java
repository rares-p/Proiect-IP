package com.github.raresp.proiectip.TownOfSalem.API.projections;

import com.github.raresp.proiectip.TownOfSalem.models.GameState;

import java.util.List;

public interface PublicGame {
    //Character<PublicCharacter> asd();
    List<PublicCharacter> getCharacters();
    Long getId();
    GameState getGameState();
}
