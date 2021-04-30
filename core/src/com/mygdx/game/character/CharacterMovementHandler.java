package com.mygdx.game.character;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.store.CharacterStore;

public class CharacterMovementHandler {

    public static final CharacterMovementHandler I = new CharacterMovementHandler();
    private static final float INDUCTION = 0.03f;

    public float pxOffsetLimit = 0;
    public float pyOffsetLimit = 0;

    public float actualPxOffset = 0;
    public float actualPyOffset = 0;


    private float update = 0f;

    public void calculateCoords() {

        update += Gdx.graphics.getDeltaTime();

        if (update >= 0.015f) {
            Character player = CharacterStore.I.player;

            calculateOffsets(player);

            player.x += actualPxOffset;
            player.y += actualPyOffset;
            update = 0f;
        }
    }

    private void calculateOffsets(Character player) {
        if (player.direction == Character.Direction.Right) {
            if (pxOffsetLimit == 0 && actualPxOffset > 0) {
                actualPxOffset -= INDUCTION;
                if (actualPxOffset < 0) {
                    actualPxOffset = 0;
                }
            }

            if (pyOffsetLimit == 0 && actualPyOffset > 0) {
                actualPyOffset -= INDUCTION;
                if (actualPyOffset < 0) {
                    actualPyOffset = 0;
                }
            }

            if (pxOffsetLimit > actualPxOffset) {
                actualPxOffset += INDUCTION;
            }

            if (pyOffsetLimit > actualPyOffset) {
                actualPyOffset += INDUCTION;
            }
        } else {
            if (pxOffsetLimit == 0 && actualPxOffset < 0) {
                actualPxOffset += INDUCTION;
                if (actualPxOffset > 0) {
                    actualPxOffset = 0;
                }
            }

            if (pyOffsetLimit == 0 && actualPyOffset < 0) {
                actualPyOffset += INDUCTION;
                if (actualPyOffset > 0) {
                    actualPyOffset = 0;
                }
            }

            if (pxOffsetLimit < actualPxOffset) {
                actualPxOffset -= INDUCTION;
            }

            if (pyOffsetLimit < actualPyOffset) {
                actualPyOffset -= INDUCTION;
            }
        }
    }

    public void stopPlayerHorizontalMovement() {
        pxOffsetLimit = 0;
        actualPxOffset = 0;
    }

    public void stopPlayerVerticalMovement() {
        pyOffsetLimit = 0;
        actualPyOffset = 0;
    }

}
