package com.mygdx.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.item.Armor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AbstractActor {

    public float x, y;
    public String id;
    public String name;
    public Gender gender;
    public Race race;
    public Armor armor;
    private HashMap<String, String> bodyPartColours;
    public HashMap<Attributes, Integer> attributes;
    public Kit kit;
    public HashMap<Skills, Integer> skills;

    private CharacterAnimation characterAnimation;
    private ArmorAnimation armorAnimation;

    public AbstractActor() {
        bodyPartColours = new HashMap<>();
        attributes = new HashMap<>();
        skills = new HashMap<>();
    }

    public void draw(SpriteBatch batch) {
        Map<BodyPart, TextureRegion> animData = characterAnimation.getAnimationFrames();

        // draw body
        Arrays.stream(BodyPart.values()).forEach(bodyPart -> {
            if (bodyPartColours.containsKey(bodyPart.name())) {
                batch.setColor(Color.valueOf(bodyPartColours.get(bodyPart.name())));
            }
            batch.draw(animData.get(bodyPart), this.x, this.y, 0, 0, 64, 64, 3, 3, 0);
        });

        // draw armor
        if(this.armor != null) {
            Map<BodyPart, TextureRegion> armorAnimationData = armorAnimation.getAnimationFrames();

            if (bodyPartColours.containsKey(BodyPart.Torso.name())) {
                batch.setColor(Color.valueOf(bodyPartColours.get(BodyPart.Torso.name())));
            }
            batch.draw(armorAnimationData.get(BodyPart.Torso), this.x, this.y, 0, 0, 64, 64, 3, 3, 0);
        }
    }

    public void loadAnimation() {
        this.characterAnimation = new CharacterAnimation();
        this.characterAnimation.loadAnimation(race.name(), gender.name());

        if (this.armor != null) {
            armorAnimation = new ArmorAnimation();
            armorAnimation.loadAnimation(race.name(), gender.name(), armor.id);
        }
    }

    public void setBodyPartColour(String bodyPart, String color) {
        this.bodyPartColours.put(bodyPart, color);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Map<String, String> getBodyPartColours() {
        return bodyPartColours;
    }

    public void setBodyPartColours(HashMap<String, String> bodyPartColours) {
        this.bodyPartColours = bodyPartColours;
    }

    public CharacterAnimation getCharacterAnimation() {
        return characterAnimation;
    }

    public void setCharacterAnimation(CharacterAnimation characterAnimation) {
        this.characterAnimation = characterAnimation;
    }

    public HashMap<Attributes, Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<Attributes, Integer> attributes) {
        this.attributes = attributes;
    }
}
