package com.mygdx.game.stage.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.character.Character;
import com.mygdx.game.map.Dungeon;
import com.mygdx.game.store.MapStore;

public class JumpingAction extends Action {

    private Character character;
    private static final float INDUCTION = 0.1f;

    public float pyOffsetLimit = 0;
    public float pxOffsetLimit = 0;
    public float actualPyOffset = 0;
    public float actualPxOffset = 0;
    private float update = 0f;
    private Float height = null;


    public JumpingAction(Character character,
                         float pxOffsetLimit) {
        this.character = character;
        this.pxOffsetLimit = pxOffsetLimit;
    }

    @Override
    public boolean act(float v) {

        if (height == null) {
            this.height = 0f;
        }

        if (height >= character.jumpHeight) {
            character.overrideState = null;
            return true;
        }

        update += v;

        if (update >= 0.01f) {

            height += 0.25f;

            this.pyOffsetLimit = character.jumpHeight;

            calculateVerticalOffsets();
            calculateHorizontalOffsets();

            float px = character.x + 8f;
            float py = character.y;

            if (MapStore.I.dungeon.getTileAbove(px, py, actualPyOffset).obstacleFromDown) {
                character.y = ((py) / 16) * 16 + 15;
                actualPyOffset = 0; // stop jump
                character.overrideState = null;
            } else {
                character.y += actualPyOffset;
                character.x += actualPxOffset * 3;
            }

            update = 0f;
        }

        return false;
    }

    void calculateVerticalOffsets() {
        Dungeon dungeon = MapStore.I.dungeon;

        float px = character.x + 8f;
        float py = character.y;

        if (dungeon.getTileAbove(px, py, 1).obstacleFromDown) {
            actualPyOffset = 0;
            character.overrideState = null;
        } else {
            actualPyOffset += INDUCTION;

            if (pyOffsetLimit > actualPyOffset) {
                actualPyOffset = pyOffsetLimit;
            }
        }
    }

    void calculateHorizontalOffsets() {
        Dungeon dungeon = MapStore.I.dungeon;

        float px = character.x + 8f;
        float py = character.y;

        if (character.direction == Character.Direction.Left) {
            if (dungeon.getTileToLeft(px, py, 1).obstacleFromSide) {
                pxOffsetLimit = 0;
                actualPxOffset = 0;
            }
        }

        if (character.direction == Character.Direction.Right) {
            if (dungeon.getTileToRight(px, py, 1).obstacleFromSide) {
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
