package com.mygdx.game.stage.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.character.Character;
import com.mygdx.game.store.CharacterStore;
import com.mygdx.game.store.MapStore;
import com.mygdx.game.store.PhysicsStore;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.physics.PhysicsParameters.GRAVITY_FORCE;

public class GravityAction extends Action {

    private float duration = 0f;

    Map<Character, Integer> landingCounter = new HashMap<>();
    Map<Character, Integer> fallingCounter = new HashMap<>();

    @Override
    public boolean act(float delta) {

        duration += delta;

        if (duration > 0.05f) {

            PhysicsStore.characters.forEach(character -> {

                landingCounter.computeIfAbsent(character, v -> 0);
                fallingCounter.computeIfAbsent(character, v -> 0);

                // y offsets
                float yOffset = 0f;

                // falling down
                if (character.y > character.y / 16 * 16) {
                    yOffset -= GRAVITY_FORCE;
                } else if (!(MapStore.I.dungeon.getTileBelow(character.x + 4, character.y, 1).obstacleFromUp ||
                        MapStore.I.dungeon.getTileBelow(character.x + 12, character.y, 1).obstacleFromUp)
                ) {
                    yOffset -= GRAVITY_FORCE;
                }

                // jump up
                if (character.physics.verticalForce > 0) {
                    character.physics.verticalForce = Math.max(character.physics.verticalForce - 1f, 0f);
                    yOffset += character.physics.verticalForce / 4f;
                }

                // jump blocked from above?
                if (yOffset > 0 && (
                        MapStore.I.dungeon.getTileAbove(character.x + 4, character.y + 12, 1).obstacleFromDown
                                || MapStore.I.dungeon.getTileAbove(character.x + 12, character.y + 12, 1).obstacleFromDown
                )) {
                    yOffset = 0;
                }

                // falling down blocked?
                if (yOffset < 0 && (
                        MapStore.I.dungeon.getTileBelow(character.x + 8, character.y, Math.abs(yOffset)).obstacleFromUp
                                || MapStore.I.dungeon.getTileBelow(character.x + 12, character.y, Math.abs(yOffset)).obstacleFromUp)) {
                    yOffset = (character.y / 16 * 16) - character.y + 1;
                }


                character.y += yOffset;

                // x offsets
                float xOffset = 0f;

                if (character.physics.horizontalForce > 0) {
                    character.physics.horizontalForce = Math.max(character.physics.horizontalForce - 0.2f, 0f);
                    xOffset += 0.5f;
                }

                if (character.physics.horizontalForce < 0) {
                    character.physics.horizontalForce = Math.min(character.physics.horizontalForce + 0.2f, 0f);
                    xOffset -= 0.5f;
                }

                if (xOffset > 0 && MapStore.I.dungeon.getTileToRight(character.x + 12, character.y, 1).obstacleFromSide) {
                    xOffset = 0;
                }

                if (xOffset < 0 && MapStore.I.dungeon.getTileToLeft(character.x + 4, character.y, 1).obstacleFromSide) {
                    xOffset = 0;
                }

                // if landed
                if (character.state == Character.State.Landing) {
                    landingCounter.put(character, landingCounter.get(character) + 1);
                    if (landingCounter.get(character) >= 30) {
                        if (xOffset != 0) {
                            character.state = Character.State.Running;
                        } else {
                            character.state = Character.State.Idle;
                        }
                    } else {
                        xOffset = 0;
                    }
                }

                character.x += xOffset;

                // check jumping capability
                if (yOffset == 0 &&
                        (MapStore.I.dungeon.getTileBelow(character.x + 4, character.y, 1).obstacleFromUp ||
                                MapStore.I.dungeon.getTileBelow(character.x + 12, character.y, 1).obstacleFromUp)) {
                    CharacterStore.player.physics.canJump = true;
                }

                // check for landing
                if (yOffset == 0) {
                    if (character.state == Character.State.Falling && fallingCounter.get(character) > 20) { // if player was falling
                        character.state = Character.State.Landing;
                        landingCounter.put(character, 0);
                        fallingCounter.put(character, 0);
                    }else if (character.state != Character.State.Landing) {
                        fallingCounter.put(character, 0);
                        if (xOffset != 0) {
                            character.state = Character.State.Running;
                        } else {
                            character.state = Character.State.Idle;
                        }
                    }
                }

                if (yOffset > 0) {
                    character.state = Character.State.Jumping;
                } else if (yOffset < 0) {
                    character.state = Character.State.Falling;
                    fallingCounter.put(character, fallingCounter.get(character) + 1);
                }

            });

        }

        return false;
    }
}
