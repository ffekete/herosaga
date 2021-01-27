package com.mygdx.game.actor;

import java.util.function.Consumer;

public enum Kit {

    Warrior(a -> {
        a.skills.put(Skills.Swords, 0);
        a.skills.put(Skills.Maces, 0);
        a.skills.put(Skills.Spears, 0);
        a.skills.put(Skills.WarHammers, 0);
        a.skills.put(Skills.PoleArms, 0);
        a.skills.put(Skills.TwoHandedSwords, 0);
        a.skills.put(Skills.Knives, 0);
        a.skills.put(Skills.SingleWeaponStyle, 0);
        a.skills.put(Skills.TwoHandedStyle, 0);
        a.skills.put(Skills.TwoWeaponStyle, 0);
        a.skills.put(Skills.ShieldWearingStyle, 0);
        a.skills.put(Skills.Bows, 0);
        a.skills.put(Skills.CrossBows, 0);
        a.skills.put(Skills.Throwing, 0);
        a.skills.put(Skills.Swimming, 0);
        a.skills.put(Skills.Climbing, 0);
        a.skills.put(Skills.Jumping, 0);
        a.skills.put(Skills.Intimidation, 0);

        a.attributes.put(Attributes.Strength, 7);
        a.attributes.put(Attributes.Endurance, 7);
        a.attributes.put(Attributes.Perception, 5);
        a.attributes.put(Attributes.Agility, 5);
        a.attributes.put(Attributes.Reflexes, 6);
        a.attributes.put(Attributes.Charisma, 4);
        a.attributes.put(Attributes.Willpower, 3);
        a.attributes.put(Attributes.Intelligence, 3);
    }, null);


    Kit(Consumer<AbstractActor> actorModifier, Gender excludedGender) {
        this.actorModifier = actorModifier;
        this.excludedGender = excludedGender;
    }

    private Consumer<AbstractActor> actorModifier;
    private Gender excludedGender;

    public Consumer<AbstractActor> getActorModifier() {
        return actorModifier;
    }
}
