package com.mygdx.game.physics;

import com.mygdx.game.character.Character;
import com.mygdx.game.character.CharacterMovementHandler;
import com.mygdx.game.map.Dungeon;
import com.mygdx.game.store.CharacterStore;
import com.mygdx.game.store.MapStore;

public class PhysicsEngine {

    public static void step() {
        Character player = CharacterStore.I.player;
        Dungeon dungeon = MapStore.I.dungeon;

        float px = player.x + 8f;
        float py = player.y;

        if(!dungeon.getTile((((int)px) / 16),((int)py-1) / 16).obstacleFromUp) {
            CharacterMovementHandler.I.actualPyOffset = - 1f;
        } else {
            CharacterMovementHandler.I.actualPyOffset = 0f;
            CharacterMovementHandler.I.stopPlayerVerticalMovement();
        }

        if(player.direction == Character.Direction.Left) {
            if (dungeon.getTile((((int) px -1) / 16), ((int) py) / 16).obstacleFromSide) {
                CharacterMovementHandler.I.stopPlayerHorizontalMovement();
            }
        }

        if(player.direction == Character.Direction.Right) {
            if (dungeon.getTile((((int) px + 1) / 16), ((int) py) / 16).obstacleFromSide) {
                CharacterMovementHandler.I.stopPlayerHorizontalMovement();
            }
        }
    }

}
