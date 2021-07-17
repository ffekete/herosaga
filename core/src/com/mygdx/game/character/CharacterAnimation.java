package com.mygdx.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CharacterAnimation {

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runningAnimation;
    private Animation<TextureRegion> jumpingAnimation;

    private Animation<TextureRegion> previousAnimation;

    private float stateTime = 0;

    public CharacterAnimation(String fileName) {

        TextureRegion[][] regions = TextureRegion.split(new Texture(Gdx.files.internal(fileName + "-Idle.png")), 16, 16);
        this.idleAnimation = new Animation<>(1f, regions[0]);

        this.previousAnimation = idleAnimation;

        regions = TextureRegion.split(new Texture(Gdx.files.internal(fileName + "-Running.png")), 16, 16);
        this.runningAnimation = new Animation<>(0.1f, regions[0]);

        regions = TextureRegion.split(new Texture(Gdx.files.internal(fileName + "-Jumping.png")), 16, 16);
        this.jumpingAnimation = new Animation<>(0.1f, regions[0]);
    }

    public TextureRegion getFrame(Character.State state,
                                  Character.Direction direction) {

        stateTime += Gdx.graphics.getDeltaTime();

        switch (state) {
            case Idle:
                return getTextureRegion(direction, idleAnimation, true);
            case Running:
                return getTextureRegion(direction, runningAnimation, true);
            case Jumping:
                return getTextureRegion(direction, jumpingAnimation, false);
        }
        return null;
    }

    private TextureRegion getTextureRegion(Character.Direction direction,
                                           Animation<TextureRegion> animation,
                                           boolean looping) {

        if(previousAnimation != animation) {
            stateTime = 0;
        }

        previousAnimation = animation;

        TextureRegion keyFrame = animation.getKeyFrame(stateTime, looping);

        if (direction == Character.Direction.Left) {

            if (!keyFrame.isFlipX()) {
                keyFrame.flip(true, false);
            }
        } else {
            if (keyFrame.isFlipX()) {
                keyFrame.flip(true, false);
            }
        }
        return keyFrame;
    }
}
