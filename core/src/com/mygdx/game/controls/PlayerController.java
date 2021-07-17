package com.mygdx.game.controls;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.character.Character;
import com.mygdx.game.store.CharacterStore;

public class PlayerController {

    private static final long LONG_JUMP_PRESS = 150l;
    private static final float ACCELERATION = 20f;
    private static final float GRAVITY = -80f;
    private static final float MAX_JUMP_SPEED = 70f;
    private static final float DAMP = 0.90f;
    private static final float MAX_VEL = 4f;

    private long jumpPressedTime;
    private boolean jumpingPressed;

    public void update(float delta) {

        processInput();

        CharacterStore.player.acceleration.y = GRAVITY;

        CharacterStore.player.acceleration.x *= delta;
        CharacterStore.player.acceleration.y *= delta;

        CharacterStore.player.velocity.add(CharacterStore.player.acceleration.x, CharacterStore.player.acceleration.y);

        if (CharacterStore.player.acceleration.x == 0) CharacterStore.player.velocity.x *= DAMP;

        if (CharacterStore.player.velocity.x > MAX_VEL) {

            CharacterStore.player.velocity.x = MAX_VEL;

        }

        if (CharacterStore.player.velocity.x < -MAX_VEL) {

            CharacterStore.player.velocity.x = -MAX_VEL;

        }

        CharacterStore.player.update(delta);

        if (CharacterStore.player.position.y < 0) {

            CharacterStore.player.position.y = 0f;

            //bob.setPosition(CharacterStore.player.position);

            if (CharacterStore.player.state.equals(Character.State.Jumping)) {
                CharacterStore.player.state = Character.State.Idle;
            }
        }

        if (CharacterStore.player.position.x < 0) {
            CharacterStore.player.position.x = 0;
            //CharacterStore.player.position = CharacterStore.player.position;
            if (!CharacterStore.player.state.equals(Character.State.Jumping)) {
                CharacterStore.player.state = Character.State.Idle;
            }
        }

//        if (CharacterStore.player.position.x > WIDTH - bob.getBounds().width) {
//
//            CharacterStore.player.position.x = WIDTH - bob.getBounds().width;
//
//            bob.setPosition(CharacterStore.player.position);
//
//            if (!bob.getState().equals(State.JUMPING)) {
//
//                bob.setState(State.IDLE);
//
//            }
//
//        }

    }


    public void processInput() {
        if (Gdx.input.isKeyPressed(InputMapping.LEFT)) {

            CharacterStore.player.velocity.x = -20;
            CharacterStore.player.direction = Character.Direction.Left;
            CharacterStore.player.state = Character.State.Running;

        } else if (Gdx.input.isKeyPressed(InputMapping.RIGHT)) {
            CharacterStore.player.velocity.x = 20;
            CharacterStore.player.direction = Character.Direction.Right;
            CharacterStore.player.state = Character.State.Running;
        } else if (CharacterStore.player.state != Character.State.Jumping) {
            CharacterStore.player.state = Character.State.Idle;
            CharacterStore.player.velocity.x = 0;
        }

        if (Gdx.input.isKeyPressed(InputMapping.SPACE)) {

            if (CharacterStore.player.state != Character.State.Jumping) {
                jumpingPressed = true;
                jumpPressedTime = System.currentTimeMillis();
                CharacterStore.player.state = Character.State.Jumping;
                CharacterStore.player.velocity.y = MAX_JUMP_SPEED;
            } else {
                if (jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)) {
                    jumpingPressed = false;
                } else {
                    if (jumpingPressed) {
                        CharacterStore.player.velocity.y = MAX_JUMP_SPEED;
                    }
                }
            }
        }
    }

}
