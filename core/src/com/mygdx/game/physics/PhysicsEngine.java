package com.mygdx.game.physics;

import com.mygdx.game.character.Character;
import com.mygdx.game.character.CharacterAnimationRenderer;
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

        if(!dungeon.getTileBelow(px, py).obstacleFromUp ||
                (dungeon.getTileExactly(px, py).obstacleFromUp && !dungeon.getTileExactly(px, py).obstacleFromDown)) {
            CharacterMovementHandler.I.actualPyOffset = - 1f;

            if(player.state != Character.State.Falling) {
                CharacterAnimationRenderer.I.resetAnimation(player);
            }

            player.state = Character.State.Falling;
        } else {
            if(player.state == Character.State.Falling) {
                player.state = Character.State.Idle;
            }

            CharacterMovementHandler.I.actualPyOffset = 0f;
            CharacterMovementHandler.I.stopPlayerVerticalMovement();
            if(dungeon.getTileExactly(player.x + 8, player.y).obstacleFromUp) {
                player.y = (((player.y / 16) + 1) * 16);
            }
        }

        if(player.direction == Character.Direction.Left) {
            if (dungeon.getTileToLeft(px, py).obstacleFromSide) {
                CharacterMovementHandler.I.stopPlayerHorizontalMovement();
            }
        }

        if(player.direction == Character.Direction.Right) {
            if (dungeon.getTileToRight(px, py).obstacleFromSide) {
                CharacterMovementHandler.I.stopPlayerHorizontalMovement();
            }
        }
    }

}
