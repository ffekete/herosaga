package com.mygdx.game.stage.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.character.Character;
import com.mygdx.game.map.Dungeon;
import com.mygdx.game.store.MapStore;

public class HorizontalGravityAction extends Action {

    private Character character;
    private static final float INDUCTION = 0.1f;

    public float pxOffsetLimit = 0;
    public float actualPxOffset = 0;
    private float update = 0f;


    public HorizontalGravityAction(Character character) {
        this.character = character;
    }

    @Override
    public boolean act(float v) {

        update += v;

        if (update >= 0.005f) {

            if (character.overrideState == Character.State.Jumping) {
                pxOffsetLimit = character.getMaxRunningSpeed();
                calculateHorizontalOffsets();

                float px = character.x + 8f;
                float py = character.y;

                if (MapStore.I.dungeon.getTileToRight(px, py, actualPxOffset).obstacleFromSide) {
                    character.x = ((px) / 16) * 16;
                    character.runningSpeed = 0f;
                } else {
                    character.x += actualPxOffset;
                }
                update = 0;
                return false; // jumping, nothing to do here
            }
        }

        return false;
    }

    void calculateHorizontalOffsets() {
        Dungeon dungeon = MapStore.I.dungeon;

        float px = character.x + 8f;
        float py = character.y;

        if (character.direction == Character.Direction.Left) {
            if (dungeon.getTileToLeft(px, py, actualPxOffset).obstacleFromSide) {
                pxOffsetLimit = 0;
                actualPxOffset = 0;
            }
        }

        if (character.direction == Character.Direction.Right) {
            if (dungeon.getTileToRight(px, py, actualPxOffset).obstacleFromSide) {
                pxOffsetLimit = 0;
                actualPxOffset = 0;
            }
        }

        if (character.direction == Character.Direction.Right) {
            if (pxOffsetLimit == 0 && actualPxOffset > 0) {
                actualPxOffset -= INDUCTION;
                if (actualPxOffset < 0) {
                    pxOffsetLimit = 0;
                    actualPxOffset = 0;
                }
            }

            if (pxOffsetLimit > actualPxOffset) {
                actualPxOffset += INDUCTION;
            }

        } else {
            if (pxOffsetLimit == 0 && actualPxOffset < 0) {
                actualPxOffset += INDUCTION;
                if (actualPxOffset > 0) {
                    pxOffsetLimit = 0;
                    actualPxOffset = 0;

                }
            }

            if (pxOffsetLimit < actualPxOffset) {
                actualPxOffset -= INDUCTION;
            }
        }
    }
}
