package com.mygdx.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.animation.ArmorAnimation;
import com.mygdx.game.actor.animation.CharacterAnimation;
import com.mygdx.game.actor.animation.Direction;
import com.mygdx.game.item.Armor;
import com.mygdx.game.item.Equipment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.actor.BodyPart.*;

public class AbstractActor {

    public float x, y;
    public String id;
    public String name;
    public Gender gender;
    public Race race;
    public Armor armor;
    public Equipment leftHand;
    public Equipment rightHand;
    private HashMap<String, String> bodyPartColours;
    public HashMap<Attributes, Integer> attributes;
    public Kit kit;
    public HashMap<Skills, Integer> skills;
    public ActorState state = ActorState.Walking;
    public Direction direction = Direction.Right;

    private CharacterAnimation characterAnimation;
    private ArmorAnimation armorAnimation;

    public AbstractActor() {
        bodyPartColours = new HashMap<>();
        attributes = new HashMap<>();
        skills = new HashMap<>();
    }

    public void draw(SpriteBatch batch) {
        Map<BodyPart, TextureRegion> animData = characterAnimation.getAnimationFrames(this.state);
        BodyPart[] partsToRender = new BodyPart[]{
                Torso,
                Head,
                Eyes,
                Ears,
                Hair,
                Mouth,
            };

        Equipment hand = this.direction == Direction.Left || this.direction == Direction.Up ? this.rightHand : this.leftHand;
        // draw hand in coverage
        if (hand != null) {
            hand.draw(batch, this, -1f, 2f);
        }

        // draw body
        renderBodyParts(batch, animData, partsToRender);

        // draw armor
        if (this.armor != null) {
            Map<BodyPart, TextureRegion> armorAnimationData = armorAnimation.getAnimationFrames(this.state);
            batch.setColor(Color.WHITE);
            batch.draw(armorAnimationData.get(Torso), this.x, this.y, 0, 0, armorAnimationData.get(Torso).getRegionWidth(), armorAnimationData.get(Torso).getRegionHeight(), 1, 1, 0);
        }

        // draw beard
        renderBodyParts(batch, animData, new BodyPart[]{
                Beard
            });


        // draw other hand
        hand = this.direction == Direction.Left || this.direction == Direction.Up ? this.leftHand : this.rightHand;
        if (hand != null) {
            hand.draw(batch, this, 0f, -2f);
        }
    }

    private void renderBodyParts(SpriteBatch batch, Map<BodyPart, TextureRegion> animData, BodyPart[] partsToRender) {
        Arrays.stream(partsToRender).forEach(bodyPart -> {
            if (bodyPartColours.containsKey(bodyPart.name())) {
                batch.setColor(Color.valueOf(bodyPartColours.get(bodyPart.name())));
            }
            batch.draw(animData.get(bodyPart), this.x, this.y, 0, 0, animData.get(bodyPart).getRegionWidth(), animData.get(bodyPart).getRegionHeight(), 1, 1, 0);
        });
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
