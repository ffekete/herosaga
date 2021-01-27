package com.mygdx.game.actor;

import static com.mygdx.game.actor.Attributes.*;

public enum Skills {

    // inter-person skills
    Persuasion(Charisma, Willpower),
    Charm(Charisma, Willpower),
    Lie(Charisma, Intelligence),
    Intimidation(Strength, Strength),

    // Weapon skills
    Swords(Agility, Reflexes),
    Maces(Strength, Endurance),
    Spears(Agility, Agility),
    WarHammers(Strength, Endurance),
    ExoticSwords(Agility, Reflexes),
    PoleArms(Strength, Agility),
    TwoHandedSwords(Strength, Agility),
    Knives(Agility, Reflexes),
    Staves(Agility, Endurance),

    // Styles
    SingleWeaponStyle(),
    TwoWeaponStyle(),
    ShieldWearingStyle(),
    TwoHandedStyle(),

    // Magic
    DivineMagic(Willpower, Intelligence),
    FireMagic(Intelligence, Reflexes),
    ColdMagic(Intelligence, Endurance),
    AirMagic(Intelligence, Agility),
    DarkMagic(Willpower, Willpower),

    // Thief skills
    Hiding(Agility, Perception),
    LockPicking(Agility, null),
    Pickpocketing(Agility, Perception),
    DetectTraps(Perception, null),
    SetTraps(Agility, null),

    // physical
    Swimming(Endurance, null),
    Climbing(Strength, null),
    Jumping(Agility, null);

    private Attributes primaryAttribute;
    private Attributes defendingAttribute;

    Skills() {
    }

    Skills(Attributes primaryAttribute, Attributes defendingAttribute) {
        this.primaryAttribute = primaryAttribute;
        this.defendingAttribute = defendingAttribute;
    }
}
