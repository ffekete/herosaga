package com.mygdx.game.stage.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.character.Character;
import com.mygdx.game.map.Dungeon;
import com.mygdx.game.store.MapStore;

public class MovementAction extends Action {

    private Character.Direction direction;
    private Character character;
    private static final float INDUCTION = 0.03f;

    public float pxOffsetLimit = 0;
    public float actualPxOffset = 0;
    private float update = 0f;


    public MovementAction(Character character,
                          Character.Direction direction,
                          float pxOffsetLimit) {
        this.character = character;
        this.character.direction = direction;
        this.pxOffsetLimit = pxOffsetLimit;
        this.direction = direction;
    }

    @Override
    public boolean act(float v) {

        if((character.direction != direction || character.state != Character.State.Running) && actualPxOffset != 0 && actualPxOffset != 0) {
            pxOffsetLimit = 0;
        }

        if((character.direction != direction || character.state != Character.State.Running) && actualPxOffset == 0) {
            return true;
        }

        update += v;

        if (update >= 0.015f) {

            calculateOffsets();

            character.x += actualPxOffset;
            update = 0f;
        }

        return false;
    }

    void calculateOffsets() {
        Dungeon dungeon = MapStore.I.dungeon;

        float px = character.x + 8f;
        float py = character.y;

        if (character.direction == Character.Direction.Left) {
            if (dungeon.getTileToLeft(px, py).obstacleFromSide) {
                pxOffsetLimit = 0;
                actualPxOffset = 0;
            }
        }

        if (character.direction == Character.Direction.Right) {
            if (dungeon.getTileToRight(px, py).obstacleFromSide) {
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
