package com.mygdx.game.map.service;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.map.Dungeon;
import com.mygdx.game.map.Tile;
import com.mygdx.game.store.MapStore;

import java.util.Vector;

public class DungeonSpaceSpotter {

    public static Vector2 findEmptySpace(int emptyAreaRequiredAbove) {
        Dungeon dungeon = MapStore.I.dungeon;

        for (int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                if(dungeon.getTile(i,j) != Tile.None) {
                    for(int y = 1; y < emptyAreaRequiredAbove; y++) {
                        if(j + y >= dungeon.getHeight() || dungeon.getTile(i, j + y) != Tile.None) {
                            continue;
                        }
                        return new Vector2(i * 16 + 16, j * 16 + 16); // translate dungeon tile to real world coordinate
                    }
                }
            }
        }
        return null;
    }

}
