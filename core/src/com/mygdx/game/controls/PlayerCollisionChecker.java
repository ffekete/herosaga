package com.mygdx.game.controls;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.character.Character;
import com.mygdx.game.store.CharacterStore;
import com.mygdx.game.store.MapStore;

import java.util.ArrayList;
import java.util.List;

public class PlayerCollisionChecker {

    public static void checkCollisionOfPlatforms(float delta) {
        Rectangle playerRect = new Rectangle(CharacterStore.player.bounds);
        playerRect.x += (CharacterStore.player.velocity.x * delta);
        playerRect.y += (CharacterStore.player.velocity.y * delta);

        int px = (int) ((playerRect.x + CharacterStore.player.bounds.width / 2) / 16);
        int py = (int) ((playerRect.y) / 16);

        List<Rectangle> boundsDown = new ArrayList<>();

        if (py < MapStore.I.dungeon.getHeight() && py >= 0) {
            boundsDown.add(MapStore.I.dungeon.bounds[px][py]);
        }

        if (CharacterStore.player.velocity.y == 0 && playerRect.y - ((int) playerRect.y) / 16 * 16 > 0.1f) {
            PlayerController.fallingThroughY = ((((int) CharacterStore.player.position.y) / 16) * 16) / 16;
        }
    }

    public static void checkCollisionBelow(float delta) {
        Rectangle playerRect = new Rectangle(CharacterStore.player.bounds);
        playerRect.x += (CharacterStore.player.velocity.x * delta);
        playerRect.y += (CharacterStore.player.velocity.y * delta);

        int px = (int) ((playerRect.x + CharacterStore.player.bounds.width / 2) / 16);
        int lpx = (int) ((playerRect.x + 1) / 16);
        int rpx = (int) ((playerRect.x + CharacterStore.player.bounds.width - 1) / 16);
        int bpy = (int) ((playerRect.y - 1) / 16);

        List<Rectangle> boundsDown = new ArrayList<>();

        if (bpy < MapStore.I.dungeon.getHeight() && bpy >= 0) {
            boundsDown.add(MapStore.I.dungeon.bounds[px][bpy]);

            if (lpx >= 0) {
                boundsDown.add(MapStore.I.dungeon.bounds[lpx][bpy]);
            }

            if (rpx < MapStore.I.dungeon.getWidth()) {
                boundsDown.add(MapStore.I.dungeon.bounds[rpx][bpy]);
            }
        }

        if (CharacterStore.player.velocity.y < 0) {

            if (PlayerController.fallingThroughY != null) {
                if (PlayerController.fallingThroughY == ((int) playerRect.y / 16)) {
                    return;
                } else {
                    PlayerController.fallingThroughY = null;
                }
            }

            if (boundsDown.stream().anyMatch(r -> r != null
                    && r.overlaps(playerRect)
                    && MapStore.I.dungeon.getTile((int) r.x / 16, (int) r.y / 16).obstacleFromUp
                    && r.y < playerRect.y)) {
                CharacterStore.player.velocity.y = 0;
                CharacterStore.player.state = CharacterStore.player.velocity.x != 0 ? Character.State.Running : Character.State.Idle;
            }
        }
    }

    public static void checkCollisionAbove(float delta) {
        Rectangle playerRect = new Rectangle(CharacterStore.player.bounds);
        playerRect.x += (CharacterStore.player.velocity.x * delta);
        playerRect.y += (CharacterStore.player.velocity.y * delta);

        int px = (int) ((playerRect.x + CharacterStore.player.bounds.width / 2) / 16);
        int lpx = (int) ((playerRect.x + 1) / 16);
        int rpx = (int) ((playerRect.x + CharacterStore.player.bounds.width - 1) / 16);
        int bpy = (int) ((playerRect.y + playerRect.height + 1) / 16);

        List<Rectangle> boundsDown = new ArrayList<>();

        if (bpy < MapStore.I.dungeon.getHeight() && bpy >= 0) {
            boundsDown.add(MapStore.I.dungeon.bounds[px][bpy]);

            if (lpx >= 0) {
                boundsDown.add(MapStore.I.dungeon.bounds[lpx][bpy]);
            }

            if (rpx < MapStore.I.dungeon.getWidth()) {
                boundsDown.add(MapStore.I.dungeon.bounds[rpx][bpy]);
            }
        }

        if (CharacterStore.player.velocity.y > 0) {
            if (boundsDown.stream().anyMatch(r -> {
                return r != null
                        && r.overlaps(playerRect) &&
                        MapStore.I.dungeon.getTile((int) r.x / 16, (int) r.y / 16).obstacleFromDown;
            })) {
                CharacterStore.player.velocity.y = 0;
                CharacterStore.player.state = CharacterStore.player.velocity.x != 0 ? Character.State.Running : Character.State.Idle;
            }
        }
    }

    public static void checkCollisionLeft(float delta) {
        Rectangle playerRect = new Rectangle(CharacterStore.player.bounds);
        playerRect.x += (CharacterStore.player.velocity.x * delta);
        playerRect.y += (CharacterStore.player.velocity.y * delta);


        int lpx = (int) (playerRect.x / 16);
        int py = (int) ((playerRect.y + CharacterStore.player.bounds.height / 2f) / 16);
        int bpy = (int) ((playerRect.y - 1) / 16);
        int apy = (int) ((playerRect.y + CharacterStore.player.bounds.height + 1) / 16);

        List<Rectangle> boundsLeft = new ArrayList<>();

        if (lpx >= 0) {
            boundsLeft.add(MapStore.I.dungeon.bounds[lpx][py]);

            if (bpy >= 0) {
                boundsLeft.add(MapStore.I.dungeon.bounds[lpx][bpy]);
            }

            if (apy < MapStore.I.dungeon.getHeight()) {
                boundsLeft.add(MapStore.I.dungeon.bounds[lpx][apy]);
            }
        }

        if (CharacterStore.player.velocity.x < 0) {
            if (boundsLeft.stream().anyMatch(r -> r != null
                    && r.overlaps(playerRect)
                    && MapStore.I.dungeon.getTile((int) r.x / 16, (int) r.y / 16).obstacleFromSide)) {
                CharacterStore.player.velocity.x = 0;
                CharacterStore.player.state = Character.State.Idle;
            }
        }
    }

    public static void checkCollisionRight(float delta) {
        Rectangle playerRect = new Rectangle(CharacterStore.player.bounds);
        playerRect.x += (CharacterStore.player.velocity.x * delta);
        playerRect.y += (CharacterStore.player.velocity.y * delta);


        int rpx = (int) ((playerRect.x + playerRect.width) / 16);
        int py = (int) ((playerRect.y + CharacterStore.player.bounds.height / 2f) / 16);
        int bpy = (int) ((playerRect.y - 1) / 16);
        int apy = (int) ((playerRect.y + CharacterStore.player.bounds.height + 1) / 16);

        List<Rectangle> boundsLeft = new ArrayList<>();

        if (rpx < MapStore.I.dungeon.getWidth()) {
            boundsLeft.add(MapStore.I.dungeon.bounds[rpx][py]);

            if (bpy >= 0) {
                boundsLeft.add(MapStore.I.dungeon.bounds[rpx][bpy]);
            }

            if (apy < MapStore.I.dungeon.getHeight()) {
                boundsLeft.add(MapStore.I.dungeon.bounds[rpx][apy]);
            }
        }

        if (CharacterStore.player.velocity.x > 0) {
            if (boundsLeft.stream().anyMatch(r -> r != null
                    && r.overlaps(playerRect)
                    && MapStore.I.dungeon.getTile((int) r.x / 16, (int) r.y / 16).obstacleFromSide)) {
                CharacterStore.player.velocity.x = 0;
                CharacterStore.player.state = Character.State.Idle;
            }
        }
    }


    public static boolean isStandingOnSolidGround(float delta) {
        Rectangle playerRect = new Rectangle(CharacterStore.player.bounds);
        playerRect.x += (CharacterStore.player.velocity.x * delta);
        playerRect.y += (CharacterStore.player.velocity.y * delta);

        int px = (int) ((CharacterStore.player.bounds.x + CharacterStore.player.bounds.width / 2) / 16);
        int lpx = (int) (CharacterStore.player.bounds.x / 16);
        int rpx = (int) ((CharacterStore.player.bounds.x + CharacterStore.player.bounds.width) / 16);

        int bpy = (int) ((playerRect.y - 1) / 16);

        if (bpy < MapStore.I.dungeon.getHeight() && bpy >= 0) {
            if (MapStore.I.dungeon.getTile(px, bpy).obstacleFromUp) {
                return true;
            }

            if (lpx >= 0 && MapStore.I.dungeon.getTile(lpx, bpy).obstacleFromUp) {
                return true;
            }

            if (rpx < MapStore.I.dungeon.getWidth() && MapStore.I.dungeon.getTile(rpx, bpy).obstacleFromUp) {
                return true;
            }
        }

        return false;
    }

    public static boolean isStandingOnPlatform(float delta) {
        Rectangle playerRect = new Rectangle(CharacterStore.player.bounds);
        playerRect.x += (CharacterStore.player.velocity.x * delta);
        playerRect.y += (CharacterStore.player.velocity.y * delta);

        int px = (int) ((CharacterStore.player.bounds.x + CharacterStore.player.bounds.width / 2) / 16);
        int lpx = (int) (CharacterStore.player.bounds.x / 16);
        int rpx = (int) ((CharacterStore.player.bounds.x + CharacterStore.player.bounds.width) / 16);

        int bpy = (int) ((playerRect.y - 1) / 16);

        if (bpy < MapStore.I.dungeon.getHeight() && bpy >= 0) {
            if (MapStore.I.dungeon.getTile(px, bpy).obstacleFromUp && !MapStore.I.dungeon.getTile(px, bpy).obstacleFromDown) {
                return true;
            }

            if (lpx >= 0 && MapStore.I.dungeon.getTile(lpx, bpy).obstacleFromUp && !MapStore.I.dungeon.getTile(px, bpy).obstacleFromDown) {
                return true;
            }

            if (rpx < MapStore.I.dungeon.getWidth() && MapStore.I.dungeon.getTile(rpx, bpy).obstacleFromUp && !MapStore.I.dungeon.getTile(px, bpy).obstacleFromDown) {
                return true;
            }
        }

        return false;
    }
}
