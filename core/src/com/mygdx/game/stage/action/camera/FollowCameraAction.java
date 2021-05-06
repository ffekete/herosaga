package com.mygdx.game.stage.action.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.character.Character;
import com.mygdx.game.store.CameraStore;
import com.mygdx.game.store.CharacterStore;

public class FollowCameraAction extends Action {

    private static final float STEP = 0.5f;
    private float duration = 0;

    @Override
    public boolean act(float v) {

        duration += v;

        if (duration > 0.0001f) {

            OrthographicCamera camera = CameraStore.I.orthographicCamera;
            Character player = CharacterStore.I.player;

            if (camera.position.x < player.x + 8) {
                camera.position.x += STEP;
                camera.update();
            }

            if (camera.position.x > player.x + 8) {
                camera.position.x -= STEP;
                camera.update();
            }

            if (camera.position.y < player.y + 8) {
                camera.position.y += STEP;
                camera.update();
            }

            if (camera.position.y > player.y + 8) {
                camera.position.y -= STEP;
                camera.update();
            }

            duration = 0;
        }

        return false;
    }
}
