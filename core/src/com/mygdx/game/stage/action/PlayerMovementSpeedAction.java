package com.mygdx.game.stage.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.store.CharacterStore;

public class PlayerMovementSpeedAction extends Action {

    private static final float RUNNING_SPEED_INCREASE_STEP = 0.1f;
    float duration = 0.05f;

    @Override
    public boolean act(float delta) {

        duration += delta;

        if (duration >= 0.10f) {

            if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if(CharacterStore.I.player.runningSpeed > 0) {
                    CharacterStore.I.player.runningSpeed -= RUNNING_SPEED_INCREASE_STEP;

                    if(CharacterStore.I.player.runningSpeed < 0) {
                        CharacterStore.I.player.runningSpeed = 0f;
                    }
                }

                if(CharacterStore.I.player.runningSpeed < 0) {
                    CharacterStore.I.player.runningSpeed += RUNNING_SPEED_INCREASE_STEP;

                    if(CharacterStore.I.player.runningSpeed > 0) {
                        CharacterStore.I.player.runningSpeed = 0f;
                    }
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                // reset from other direction
                if(CharacterStore.I.player.runningSpeed > 0) {
                    CharacterStore.I.player.runningSpeed = 0;
                }

                CharacterStore.I.player.runningSpeed -= RUNNING_SPEED_INCREASE_STEP;
                if (CharacterStore.I.player.runningSpeed < -1 * CharacterStore.I.player.getMaxRunningSpeed()) {
                    CharacterStore.I.player.runningSpeed = -1 * CharacterStore.I.player.getMaxRunningSpeed();
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

                // reset from other direction
                if(CharacterStore.I.player.runningSpeed < 0) {
                    CharacterStore.I.player.runningSpeed = 0;
                }

                CharacterStore.I.player.runningSpeed += RUNNING_SPEED_INCREASE_STEP;
                if (CharacterStore.I.player.runningSpeed > CharacterStore.I.player.getMaxRunningSpeed()) {
                    CharacterStore.I.player.runningSpeed = CharacterStore.I.player.getMaxRunningSpeed();
                }
            }

            duration = 0f;
        }

        return false;
    }
}
