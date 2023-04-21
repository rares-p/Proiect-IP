package com.github.raresp.proiectip.TownOfSalem.API.projections;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public interface PublicGame {
    List<PublicCharacter> getCharacters();
    public Long getId();
}
