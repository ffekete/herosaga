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

    Map<Character, Float> stateTimes;
    Map<Character, Animation<TextureRegion>> idleAnimation;
    Map<Character, Animation<TextureRegion>> runningAnimation;
    Map<Character, Animation<TextureRegion>> fallingAnimation;
    Map<Character, Animation<TextureRegion>> squattingAnimation;
    Map<Character, Animation<TextureRegion>> landingAnimation;
    Map<Character, Animation<TextureRegion>> jumpingAnimation;
    Map<Character, Animation<TextureRegion>> jumpingAnticipationAnimation;

    public CharacterAnimationRenderer() {

        this.stateTimes = new HashMap<>();

        this.idleAnimation = new HashMap<>();
        this.runningAnimation = new HashMap<>();
        this.fallingAnimation = new HashMap<>();
        this.squattingAnimation = new HashMap<>();
        this.landingAnimation = new HashMap<>();
        this.jumpingAnimation = new HashMap<>();
        this.jumpingAnticipationAnimation = new HashMap<>();

        // others
    }

    public void addAnimations(Character character) {

        String fileName = "Rogue";

        this.stateTimes.put(character, 0f);

        TextureRegion[][] regions = TextureRegion.split(new Texture(Gdx.files.internal(fileName + "-Idle.png")), 16, 16);
        this.idleAnimation.put(character, new Animation<>(1f, regions[0]));

        regions = TextureRegion.split(new Texture(Gdx.files.internal(fileName + "-Running.png")), 16, 16);
        this.runningAnimation.put(character, new Animation<>(0.1f, regions[0]));

        regions = TextureRegion.split(new Texture(Gdx.files.internal(fileName + "-Falling.png")), 16, 16);
        this.fallingAnimation.put(character, new Animation<>(0.3f, regions[0]));

        regions = TextureRegion.split(new Texture(Gdx.files.internal(fileName + "-Squatting.png")), 16, 16);
        this.squattingAnimation.put(character, new Animation<>(0.1f, regions[0]));

        regions = TextureRegion.split(new Texture(Gdx.files.internal(fileName + "-Landing.png")), 16, 16);
        this.landingAnimation.put(character, new Animation<>(0.1f, regions[0]));

        regions = TextureRegion.split(new Texture(Gdx.files.internal(fileName + "-Jumping.png")), 16, 16);
        this.jumpingAnimation.put(character, new Animation<>(0.1f, regions[0]));

        regions = TextureRegion.split(new Texture(Gdx.files.internal(fileName + "-JumpingAnticipation.png")), 16, 16);
        this.jumpingAnticipationAnimation.put(character, new Animation<>(0.05f, regions[0]));
    }

    public void render(SpriteBatch batch) {
        Character player = CharacterStore.I.player;

        this.stateTimes.put(player, this.stateTimes.get(player) + Gdx.graphics.getDeltaTime());

        Character.State state = player.overrideState != null ? player.overrideState : player.state;

        switch (state) {
            case Idle:
                TextureRegion idleTextureRegion = idleAnimation.get(player).getKeyFrame(stateTimes.get(player), true);

                if (player.direction == Character.Direction.Left) {

                    if (!idleTextureRegion.isFlipX()) {
                        idleTextureRegion.flip(true, false);
                    } else {
                        if (idleTextureRegion.isFlipX()) {
                            idleTextureRegion.flip(true, false);
                        }
                    }
                }
                batch.draw(idleTextureRegion, player.x, player.y);
                break;
            case Running:
                TextureRegion runningTextureRegion = runningAnimation.get(player).getKeyFrame(stateTimes.get(player), true);
                if (player.direction == Character.Direction.Left) {
                    if (!runningTextureRegion.isFlipX()) {
                        runningTextureRegion.flip(true, false);
                    }
                } else {
                    if (runningTextureRegion.isFlipX()) {
                        runningTextureRegion.flip(true, false);
                    }
                }
                batch.draw(runningTextureRegion, player.x, player.y);
                break;

            case Falling:
                TextureRegion fallingTextureRegion = fallingAnimation.get(player).getKeyFrame(stateTimes.get(player), false);
                batch.draw(fallingTextureRegion, player.x, player.y);
                break;

            case Squatting:
                TextureRegion sqattingTextureRegion = squattingAnimation.get(player).getKeyFrame(stateTimes.get(player), false);
                batch.draw(sqattingTextureRegion, player.x, player.y);
                break;

            case Landing:
                TextureRegion landingTextureRegion = landingAnimation.get(player).getKeyFrame(stateTimes.get(player), false);

                if (player.direction == Character.Direction.Left) {
                    if (!landingTextureRegion.isFlipX()) {
                        landingTextureRegion.flip(true, false);
                    }
                } else {
                    if (landingTextureRegion.isFlipX()) {
                        landingTextureRegion.flip(true, false);
                    }
                }

                batch.draw(landingTextureRegion, player.x, player.y);
                break;

            case JumpingAnticipation:
                TextureRegion jumpingAnticipationTextureRegion = jumpingAnticipationAnimation.get(player).getKeyFrame(stateTimes.get(player), false);

                if (player.direction == Character.Direction.Left) {
                    if (!jumpingAnticipationTextureRegion.isFlipX()) {
                        jumpingAnticipationTextureRegion.flip(true, false);
                    }
                } else {
                    if (jumpingAnticipationTextureRegion.isFlipX()) {
                        jumpingAnticipationTextureRegion.flip(true, false);
                    }
                }

                batch.draw(jumpingAnticipationTextureRegion, player.x, player.y);
                break;

            case Jumping:
                TextureRegion jumpingTextureRegion = jumpingAnimation.get(player).getKeyFrame(stateTimes.get(player), true);

                if (player.direction == Character.Direction.Left) {
                    if (!jumpingTextureRegion.isFlipX()) {
                        jumpingTextureRegion.flip(true, false);
                    }
                } else {
                    if (jumpingTextureRegion.isFlipX()) {
                        jumpingTextureRegion.flip(true, false);
                    }
                }

                batch.draw(jumpingTextureRegion, player.x, player.y);
                break;
        }
    }

    public void resetAnimation(Character player) {
        stateTimes.put(player, 0f);
    }
}
