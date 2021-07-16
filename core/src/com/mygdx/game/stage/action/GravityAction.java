package com.mygdx.game.stage.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.character.Character;
import com.mygdx.game.store.CharacterStore;
import com.mygdx.game.store.MapStore;
import com.mygdx.game.store.PhysicsStore;

import static com.mygdx.game.physics.PhysicsParameters.GRAVITY_FORCE;

public class GravityAction extends Action {

    private float duration = 0f;

    @Override
    public boolean act(float delta) {

        duration += delta;

        if (duration > 0.05f) {

            PhysicsStore.characters.forEach(character -> {

                // y offsets
                float yOffset = 0f;

                if (character.y > character.y / 16 * 16) {
                    yOffset -= GRAVITY_FORCE;
                } else if (!(MapStore.I.dungeon.getTileBelow(character.x, character.y, 1).obstacleFromUp ||
                        MapStore.I.dungeon.getTileBelow(character.x + 15, character.y, 1).obstacleFromUp)
                ) {
                    yOffset -= GRAVITY_FORCE;
                }

                if (character.physics.verticalForce > 0) {
                    character.physics.verticalForce = Math.max(character.physics.verticalForce - 1f, 0f);
                    yOffset += character.physics.verticalForce / 4f;
                }

                if (yOffset > 0 && (
                        MapStore.I.dungeon.getTileAbove(character.x + 4, character.y + 16, 1).obstacleFromDown
                                || MapStore.I.dungeon.getTileAbove(character.x + 12, character.y + 16, 1).obstacleFromDown
                )) {
                    yOffset = 0;
                }

                character.y += yOffset;

                // if character fell through
                if (MapStore.I.dungeon.getTileExactly(character.x, character.y).obstacleFromUp) {
                    character.y = (character.y + 1) / 16 * 16;
                }

                // x offsets
                float xOffset = 0f;

                if (character.physics.horizontalForce > 0) {
                    character.physics.horizontalForce = Math.max(character.physics.horizontalForce - 0.2f, 0f);
                    xOffset += 0.75f;
                }

                if (character.physics.horizontalForce < 0) {
                    character.physics.horizontalForce = Math.min(character.physics.horizontalForce + 0.2f, 0f);
                    xOffset -= 0.75f;
                }

                if (xOffset > 0 && character.x < character.x / 16 * 16 + 16 && MapStore.I.dungeon.getTileToRight(character.x, character.y, 16).obstacleFromSide) {
                    xOffset = 0;
                }

                if (xOffset < 0 && character.x == character.x / 16 * 16 && MapStore.I.dungeon.getTileToLeft(character.x, character.y, 1).obstacleFromSide) {
                    xOffset = 0;
                }

                character.x += xOffset;

                if (yOffset == 0 &&
                        (MapStore.I.dungeon.getTileBelow(character.x, character.y, 1).obstacleFromUp ||
                                MapStore.I.dungeon.getTileBelow(character.x + 15, character.y, 1).obstacleFromUp)) {
                    CharacterStore.player.physics.canJump = true;
                }

                if (yOffset == 0 && character.state == Character.State.Jumping) {
                    if (xOffset != 0) {
                        character.state = Character.State.Running;
                    } else {
                        character.state = Character.State.Idle;
                    }
                }

                if (yOffset == 0 && xOffset == 0) {
                    character.state = Character.State.Idle;
                }

            });

        }

        return false;
    }
}
