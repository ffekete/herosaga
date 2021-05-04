package com.mygdx.game.stage.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.store.CharacterStore;

public class PlayerMovementSpeedAction extends Action {

    float duration = 0.5f;

    @Override
    public boolean act(float delta) {

        duration += delta;

        if (duration >= 0.005f) {

            if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if(CharacterStore.I.player.runningSpeed > 0) {
                    CharacterStore.I.player.runningSpeed -= 0.5f;

                    if(CharacterStore.I.player.runningSpeed < 0) {
                        CharacterStore.I.player.runningSpeed = 0f;
                    }
                }

                if(CharacterStore.I.player.runningSpeed < 0) {
                    CharacterStore.I.player.runningSpeed += 0.5f;

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

                CharacterStore.I.player.runningSpeed -= 0.5f;
                if (CharacterStore.I.player.runningSpeed < -2f) {
                    CharacterStore.I.player.runningSpeed = -2f;
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

                // reset from other direction
                if(CharacterStore.I.player.runningSpeed < 0) {
                    CharacterStore.I.player.runningSpeed = 0;
                }

                CharacterStore.I.player.runningSpeed += 0.5f;
                if (CharacterStore.I.player.runningSpeed > 2f) {
                    CharacterStore.I.player.runningSpeed = 2f;
                }
            }

            duration = 0f;
        }

        return false;
    }
}
