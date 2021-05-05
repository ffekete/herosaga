package com.mygdx.game.stage.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.character.Character;
import com.mygdx.game.map.Dungeon;
import com.mygdx.game.store.MapStore;

public class GravityAction extends Action {

    private Character character;
    private static final float INDUCTION = 0.1f;

    public float pyOffsetLimit = 0;
    public float actualPyOffset = 0;
    private float update = 0f;


    public GravityAction(Character character,
                         float pyOffsetLimit) {
        this.character = character;
        this.pyOffsetLimit = pyOffsetLimit;
    }

    @Override
    public boolean act(float v) {

        update += v;

        if (update >= 0.005f) {

            if (character.overrideState == Character.State.Jumping) {
                update = 0;
                return false; // jumping, nothing to do here
            }

            float px = character.x + 8f;
            float py = character.y;

            calculateOffsets();

            if (MapStore.I.dungeon.getTileBelow(px, py, actualPyOffset).obstacleFromUp) {
                character.y = ((py) / 16) * 16;

                if (actualPyOffset > 0) {
                    character.addAction(new LandingAction(character));
                }

                actualPyOffset = 0; // stop falling
            } else {
                character.y -= actualPyOffset;
            }

            update = 0f;
        }

        return false;
    }

    void calculateOffsets() {
        Dungeon dungeon = MapStore.I.dungeon;

        float px = character.x + 8f;
        float py = character.y;

        if (dungeon.getTileBelow(px, py, 1).obstacleFromUp) {
            if (actualPyOffset > 0) {
                character.addAction(new LandingAction(character));
            }
            actualPyOffset = 0;

        } else {
            actualPyOffset += INDUCTION;

            if (pyOffsetLimit > actualPyOffset) {
                actualPyOffset = pyOffsetLimit;
            }
        }
    }
}
