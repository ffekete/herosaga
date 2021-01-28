package com.mygdx.game.actor.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.mygdx.game.actor.ActorState;
import com.mygdx.game.actor.BodyPart;
import com.mygdx.game.graph.AtlasUnpacker;
import com.mygdx.game.state.State;
import com.mygdx.game.store.ActorStore;

import java.util.*;

import static com.mygdx.game.actor.ActorState.*;

public class CharacterAnimation {

    private Map<BodyPart, Animation<TextureRegion>> idleAnimation = new HashMap<>();
    private Map<BodyPart, Animation<TextureRegion>> walkAnimation = new HashMap<>();
    private Map<BodyPart, Animation<TextureRegion>> deadAnimation = new HashMap<>();

    private float stateTime = 0;

    public void loadAnimation(String race, String gender) {


        Json json = new Json();
        Meta meta = json.fromJson(Meta.class, Gdx.files.internal(String.format("races/%s/%s/meta.json", race.toLowerCase(), gender.toLowerCase())));

        // load body animation
        Arrays.stream(BodyPart.values()).forEach(bodyPart -> {
            TextureRegion[][] tmp = AtlasUnpacker.I.atlas.findRegions(String.format("races/%s/%s/%s", race.toLowerCase(), gender.toLowerCase(), bodyPart)).first().split(meta.width, meta.height);
            TextureRegion[] idleFrames = new TextureRegion[5];
            TextureRegion[] walkFrames = new TextureRegion[5];
            TextureRegion[] deadFrames = new TextureRegion[1];

            for (int i = 0; i < 5; i++) {
                idleFrames[i] = tmp[0][i];
                walkFrames[i] = tmp[1][i];
            }
            deadFrames[0] = tmp[0][0];

            idleAnimation.put(bodyPart, new Animation<>(0.50f, Array.with(idleFrames), Animation.PlayMode.LOOP));
            walkAnimation.put(bodyPart, new Animation<>(0.15f, Array.with(walkFrames), Animation.PlayMode.LOOP));
            deadAnimation.put(bodyPart, new Animation<>(0.15f, Array.with(deadFrames), Animation.PlayMode.LOOP));
        });
    }

    public Map<BodyPart, TextureRegion> getAnimationFrames(ActorState state) {
        this.stateTime += Gdx.graphics.getDeltaTime();

        Map<BodyPart, TextureRegion> animationFrames = new LinkedHashMap<>();

        switch (state) {
            case Idle:
            case Attacking:
                Arrays.stream(BodyPart.values()).forEach(bodyPart -> {
                    animationFrames.put(bodyPart, idleAnimation.get(bodyPart).getKeyFrame(this.stateTime));
                });
                break;

            case Walking:
                Arrays.stream(BodyPart.values()).forEach(bodyPart -> {
                    animationFrames.put(bodyPart, walkAnimation.get(bodyPart).getKeyFrame(this.stateTime));
                });
                break;

            case Dead:
                Arrays.stream(BodyPart.values()).forEach(bodyPart -> {
                    animationFrames.put(bodyPart, deadAnimation.get(bodyPart).getKeyFrame(this.stateTime));
                });
                break;

            default:
        }

        return animationFrames;
    }
}
