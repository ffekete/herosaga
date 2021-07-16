package com.mygdx.game.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.character.Character;
import com.mygdx.game.physics.PhysicsParameters;
import com.mygdx.game.store.CharacterStore;

public class KeyPressedAction extends Action {

    private float duration = 0f;

    @Override
    public boolean act(float delta) {

        duration += delta;

        if (duration > 0.025f) {

            if (Gdx.input.isKeyPressed(InputMapping.LEFT)) {

                CharacterStore.player.direction = Character.Direction.Left;

                if (CharacterStore.player.state == Character.State.Idle || CharacterStore.player.state == Character.State.Running) {
                    CharacterStore.player.state = Character.State.Running;
                    CharacterStore.player.physics.horizontalForce = -2;
                } else if (CharacterStore.player.state == Character.State.Jumping || CharacterStore.player.state == Character.State.Falling) {
                    CharacterStore.player.physics.horizontalForce = -0.5f;
                }

            } else if (Gdx.input.isKeyPressed(InputMapping.RIGHT)) {

                CharacterStore.player.direction = Character.Direction.Right;

                if (CharacterStore.player.state == Character.State.Idle || CharacterStore.player.state == Character.State.Running) {
                    CharacterStore.player.state = Character.State.Running;
                    CharacterStore.player.physics.horizontalForce = 2;
                } else {
                    if (CharacterStore.player.state == Character.State.Jumping || CharacterStore.player.state == Character.State.Falling) {
                        CharacterStore.player.physics.horizontalForce = 0.5f;
                    }
                }
            }

            if (Gdx.input.isKeyPressed(InputMapping.SPACE) && CharacterStore.player.physics.canJump) {
                if (CharacterStore.player.physics.verticalForce == 0) {
                    CharacterStore.player.physics.verticalForce = 10;
                } else {
                    CharacterStore.player.physics.verticalForce += 5;
                }
                if (CharacterStore.player.physics.verticalForce > PhysicsParameters.JUMP_FORCE) {
                    CharacterStore.player.physics.canJump = false;
                    CharacterStore.player.physics.verticalForce = PhysicsParameters.JUMP_FORCE;
                }
            }

            duration = 0;
        }
        return false;
    }
}
