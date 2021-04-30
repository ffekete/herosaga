package com.mygdx.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.store.CharacterStore;

import java.util.HashMap;
import java.util.Map;

public class CharacterAnimationRenderer {

    public static final CharacterAnimationRenderer I = new CharacterAnimationRenderer();

    float stateTime = 0f;
    Map<Character.Class, Animation<TextureRegion>> idleAnimation;
    Map<Character.Class, Animation<TextureRegion>> runningAnimation;

    public CharacterAnimationRenderer() {

        this.idleAnimation = new HashMap<>();
        this.runningAnimation = new HashMap<>();

        // rogue
        TextureRegion[][] regions = TextureRegion.split(new Texture(Gdx.files.internal("Rogue-Idle.png")), 16 ,16);
        this.idleAnimation.put(Character.Class.Rogue, new Animation<>(1f, regions[0]));

        regions = TextureRegion.split(new Texture(Gdx.files.internal("Rogue-Running.png")), 16 ,16);
        this.runningAnimation.put(Character.Class.Rogue, new Animation<>(0.1f, regions[0]));

        // others
    }

    public void render(SpriteBatch batch) {
        Character player = CharacterStore.I.player;

        stateTime += Gdx.graphics.getDeltaTime();

        switch (player.state) {
            case Idle:
                TextureRegion idleTextureRegion = idleAnimation.get(player.aClass).getKeyFrame(stateTime, true);

                if(player.direction == Character.Direction.Left) {

                    if(!idleTextureRegion.isFlipX()) {
                        idleTextureRegion.flip(true, false);
                    } else {
                        if(idleTextureRegion.isFlipX()) {
                            idleTextureRegion.flip(true, false);
                        }
                    }
                }
                batch.draw(idleTextureRegion, player.x, player.y);
                break;
            case Running:
                TextureRegion runningTextureRegion = runningAnimation.get(player.aClass).getKeyFrame(stateTime, true);
                if(player.direction == Character.Direction.Left) {
                    if(!runningTextureRegion.isFlipX()) {
                        runningTextureRegion.flip(true, false);
                    }
                } else {
                    if(runningTextureRegion.isFlipX()) {
                        runningTextureRegion.flip(true, false);
                    }
                }
                batch.draw(runningTextureRegion, player.x, player.y);
                break;
        }
    }

}
