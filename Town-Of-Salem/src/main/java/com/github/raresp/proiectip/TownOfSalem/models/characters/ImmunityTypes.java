package com.github.raresp.proiectip.TownOfSalem.models.characters;

public enum ImmunityTypes {
    None,
    Roleblock,

    DetectionImmunity,
    Night
}
/*
- night immunity: all roles with night immunity have basic defense:
    - godfather, serial killer, arsonist, werewolf, survivor(with vest), doctor(with self heal)
- role-block immunity: escort, serial killer(can't be roleblocked by escorts)
- detection immunity : "seem innocent to the sheriff"; all townies by default are innocent
(I guess we could take this out and only leave the innocent variable)
 */