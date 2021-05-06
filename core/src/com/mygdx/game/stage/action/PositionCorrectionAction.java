package com.mygdx.game.stage.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.character.Character;
import com.mygdx.game.map.Dungeon;
import com.mygdx.game.store.MapStore;

public class PositionCorrectionAction extends Action {

    private Character character;

    public PositionCorrectionAction(Character character) {
        this.character = character;
    }


    @Override
    public boolean act(float v) {
        Dungeon dungeon = MapStore.I.dungeon;

        if (dungeon.getTileExactly(character.x + 6, character.y).obstacleFromSide) {
            character.x += 0.2f;
        } else if (dungeon.getTileExactly(character.x + 10, character.y).obstacleFromSide) {
            character.x -= 0.2f;
        }

        if (dungeon.getTileExactly(character.x + 6, character.y).obstacleFromDown || dungeon.getTileExactly(character.x + 6, character.y).obstacleFromUp) {
            character.y += 0.2f;
        } else if (dungeon.getTileExactly(character.x + 6, character.y + 13).obstacleFromDown || dungeon.getTileExactly(character.x + 6, character.y + 13).obstacleFromUp) {
            character.y -= 0.2f;
        }

        if(character.overrideState == Character.State.Jumping) {
            if((dungeon.getTileExactly(character.x + 6, character.y).obstacleFromUp ||
                    dungeon.getTileExactly(character.x + 10, character.y).obstacleFromUp)) {
                character.y = (character.y / 16) * 16 + 16;
            }
        }

        return false;
    }
}
