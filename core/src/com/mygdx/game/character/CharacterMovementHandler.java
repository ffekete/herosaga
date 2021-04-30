package com.mygdx.game.character;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.store.CameraStore;
import com.mygdx.game.store.CharacterStore;

public class CharacterMovementHandler {

    public static final CharacterMovementHandler I = new CharacterMovementHandler();

    public float pxOffset = 0;
    public float pyOffset = 0;

    private float update = 0f;

    public void calculateCoords() {

        update += Gdx.graphics.getDeltaTime();

        if(update >= 0.015f) {
            Character player = CharacterStore.I.player;
            player.x += pxOffset;
            player.y += pyOffset;
            update = 0f;
        }
    }

}
