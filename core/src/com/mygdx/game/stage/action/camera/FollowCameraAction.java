package com.mygdx.game.stage.action.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.mygdx.game.character.Character;
import com.mygdx.game.store.CameraStore;
import com.mygdx.game.store.CharacterStore;

public class FollowCameraAction extends Action {

    private float duration = 0;

    @Override
    public boolean act(float v) {

        duration += v;

        if(duration > 0.0025f) {

            OrthographicCamera camera = CameraStore.I.orthographicCamera;
            Character player = CharacterStore.I.player;

            if (camera.position.x < player.x + 8) {
                camera.position.x+=0.5f;
                camera.update();
            }

            if (camera.position.x > player.x + 8) {
                camera.position.x-=0.5f;
                camera.update();
            }

            if (camera.position.y < player.y + 8) {
                camera.position.y+= 0.5f;
                camera.update();
            }

            if (camera.position.y > player.y + 8) {
                camera.position.y-=0.5f;
                camera.update();
            }

            duration = 0;
        }

        return false;
    }
}
