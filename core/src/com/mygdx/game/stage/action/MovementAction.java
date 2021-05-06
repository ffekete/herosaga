package com.mygdx.game.stage.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.character.Character;
import com.mygdx.game.map.Dungeon;
import com.mygdx.game.store.MapStore;

public class MovementAction extends Action {

    private Character.Direction direction;
    private Character character;
    private static final float INDUCTION = 0.03f;

    public float actualPxOffset = 0;
    private float update = 0f;


    public MovementAction(Character character,
                          Character.Direction direction) {
        this.character = character;
        this.character.direction = direction;
        this.direction = direction;
    }

    @Override
    public boolean act(float v) {

        if (character.overrideState != null) {
            return false; // nothing to do yet, let's wait staggered / jumping / etc... to be over
        }

        if ((character.direction != direction || character.state != Character.State.Running) && actualPxOffset != 0) {
            character.runningSpeed = 0;
        }

        if ((character.direction != direction || character.state != Character.State.Running) && actualPxOffset == 0) {
            return true;
        }

        update += v;

        if (update >= 0.015f) {

            calculateOffsets();

            if (character.direction == Character.Direction.Left) {
                if (MapStore.I.dungeon.getTileToLeft(character.x + 4, character.y, 1).obstacleFromSide) {
                    character.x = (character.x / 16) * 16;
                    actualPxOffset = 0;
                } else {
                    character.x += actualPxOffset;
                }
            } else if (character.direction == Character.Direction.Right) {
                if (MapStore.I.dungeon.getTileToRight(character.x + 12, character.y, 1).obstacleFromSide) {
                    character.x = (character.x / 16) * 16;
                    actualPxOffset = 0;
                } else {
                    character.x += actualPxOffset;
                }
            }


            update = 0f;
        }

        return false;
    }

    void calculateOffsets() {
        Dungeon dungeon = MapStore.I.dungeon;

        float py = character.y;

        // if blocked to the left, stop immediately
        if (character.direction == Character.Direction.Left) {
            if (dungeon.getTileToLeft(character.x + 12, py, Math.abs(actualPxOffset)).obstacleFromSide) {
                character.runningSpeed = 0;
                actualPxOffset = 0;
            }
        }

        // if blocked to the right, stop immediately
        if (character.direction == Character.Direction.Right) {
            if (dungeon.getTileToRight(character.x + 4, py, Math.abs(actualPxOffset)).obstacleFromSide) {
                character.runningSpeed = 0;
                actualPxOffset = 0;
            }
        }

        if (character.direction == Character.Direction.Right) {
            // ramp down speed if needed
            if (character.runningSpeed == 0 && actualPxOffset > 0) {
                actualPxOffset -= INDUCTION;
                if (actualPxOffset < 0) {
                    character.runningSpeed = 0;
                    actualPxOffset = 0;
                }
            }

            if (character.runningSpeed > actualPxOffset) {
                actualPxOffset += INDUCTION;
            }

        } else {
            // ramp down speed if needed
            if (character.runningSpeed == 0 && actualPxOffset < 0) {
                actualPxOffset += INDUCTION;
                if (actualPxOffset > 0) {
                    character.runningSpeed = 0;
                    actualPxOffset = 0;

                }
            }

            if (character.runningSpeed < actualPxOffset) {
                actualPxOffset -= INDUCTION;
            }
        }
    }
}
