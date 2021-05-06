package com.mygdx.game.stage.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.character.Character;
import com.mygdx.game.store.CharacterStore;

public class PlayerJumpHeightAction extends Action {

    private static final float JUMP_SPEED_INCREASE_STEP = 0.25f;
    float duration = 0.05f;

    @Override
    public boolean act(float delta) {

        duration += delta;

        if (duration >= 0.025f) {

            if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

                Character.State state = CharacterStore.I.player.overrideState != null ? CharacterStore.I.player.overrideState : CharacterStore.I.player.state;
                if (state != Character.State.Jumping && state != Character.State.JumpingAnticipation) {
                    CharacterStore.I.player.jumpHeight = 0;
                }

                return false;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

                CharacterStore.I.player.jumpHeight += JUMP_SPEED_INCREASE_STEP;
                if (CharacterStore.I.player.jumpHeight > CharacterStore.I.player.getMaxJumpHeight()) {
                    CharacterStore.I.player.jumpHeight = CharacterStore.I.player.getMaxJumpHeight();
                }
            }

            duration = 0f;
        }

        return false;
    }
}
