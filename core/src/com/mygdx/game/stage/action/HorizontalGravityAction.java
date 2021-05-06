package com.mygdx.game.stage.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.character.Character;
import com.mygdx.game.map.Dungeon;
import com.mygdx.game.store.MapStore;

public class HorizontalGravityAction extends Action {

    private Character character;

    private float update = 0f;

    private float offset = 0f;


    public HorizontalGravityAction(Character character) {
        this.character = character;
    }

    @Override
    public boolean act(float v) {

        update += v;

        if (update >= 0.005f) {

            if(character.overrideState == Character.State.Jumping) {

                if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

                    // reset if it was to the other direction
                    if(offset > 0) {
                        offset = 0;
                    }

                    offset -= 0.25f;
                    if(offset < -3) {
                        offset = -3;
                    }
                    character.x += offset;
                }

                else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

                    // reset if it was to the other direction
                    if(offset < 0) {
                        offset = 0;
                    }

                    offset += 0.25f;
                    if(offset > 3) {
                        offset = 3;
                    }
                    character.x += offset;
                }
            } else {
                offset = 0;
            }

            update = 0;
        }

        return false;
    }
}
