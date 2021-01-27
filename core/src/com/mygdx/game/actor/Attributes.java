package com.mygdx.game.actor;

public enum Attributes {

    Strength("Strength", "Strength"),
    Endurance("Endurance", "Endurance"),
    Perception("Perception", "Perception"),
    Agility("Agility", "Agility"),
    Reflexes("Reflexes", "Reflexes"),
    Charisma("Charisma", "Beauty"),
    Willpower("Willpower", "Willpower"),
    Intelligence("Intelligence", "Intelligence");

    Attributes(String maleAttributeName, String femaleAttributeName) {
        this.maleAttributeName = maleAttributeName;
        this.femaleAttributeName = femaleAttributeName;
    }

    private String maleAttributeName;
    private String femaleAttributeName;

    public String getMaleAttributeName() {
        return maleAttributeName;
    }

    public String getFemaleAttributeName() {
        return femaleAttributeName;
    }
}
