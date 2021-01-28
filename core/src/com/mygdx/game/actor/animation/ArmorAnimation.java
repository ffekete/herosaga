package com.mygdx.game.actor.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.actor.ActorState;
import com.mygdx.game.actor.BodyPart;
import com.mygdx.game.graph.AtlasUnpacker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ArmorAnimation {

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> deadAnimation;

    private float stateTime = 0;

    public void loadAnimation(String race, String gender, String armorName) {

        Json json = new Json();
        Meta meta = json.fromJson(Meta.class, Gdx.files.internal(String.format("races/%s/%s/meta.json", race.toLowerCase(), gender.toLowerCase())));

        // load armor animation
        TextureRegion[][] tmp = AtlasUnpacker.I.atlas.findRegions(String.format("races/%s/%s/%s", race.toLowerCase(), gender.toLowerCase(), armorName)).first().split(meta.width, meta.height);
        TextureRegion[] idleFrames = new TextureRegion[5];
        TextureRegion[] walkFrames = new TextureRegion[5];
        TextureRegion[] deadFrames = new TextureRegion[1];

        for (int i = 0; i < 5; i++) {
            idleFrames[i] = tmp[0][i];
            walkFrames[i] = tmp[1][i];
        }
        deadFrames[0] = tmp[0][0];

        idleAnimation = new Animation<>(0.50f, Array.with(idleFrames), Animation.PlayMode.LOOP);
        walkAnimation = new Animation<>(0.15f, Array.with(walkFrames), Animation.PlayMode.LOOP);
        deadAnimation = new Animation<>(0.15f, Array.with(deadFrames), Animation.PlayMode.LOOP);

    }

    public Map<BodyPart, TextureRegion> getAnimationFrames(ActorState state) {
        this.stateTime += Gdx.graphics.getDeltaTime();

        Map<BodyPart, TextureRegion> animationFrames = new LinkedHashMap<>();

        switch (state) {
            case Idle:
            case Attacking:
                animationFrames.put(BodyPart.Torso, idleAnimation.getKeyFrame(this.stateTime));
                break;

            case Walking:
                animationFrames.put(BodyPart.Torso, walkAnimation.getKeyFrame(this.stateTime));
                break;

            case Dead:
                animationFrames.put(BodyPart.Torso, deadAnimation.getKeyFrame(this.stateTime));
                break;

            default:
        }

        return animationFrames;
    }
}
