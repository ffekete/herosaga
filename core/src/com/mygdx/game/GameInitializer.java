package com.mygdx.game;

import com.mygdx.game.map.CaveDungeonCreator;
import com.mygdx.game.map.Tile;
import com.mygdx.game.store.MapStore;

public class GameInitializer {

    public static final GameInitializer I = new GameInitializer();

    public void init() {
        CaveDungeonCreator caveDungeonCreator = new CaveDungeonCreator(Tile.Rock, Tile.CaveBackground);
        MapStore.I.dungeon = caveDungeonCreator.create(6, 60, 40);
    }

    public void reGenerate() {
        CaveDungeonCreator caveDungeonCreator = new CaveDungeonCreator(Tile.Rock, Tile.CaveBackground);
        MapStore.I.dungeon = caveDungeonCreator.create(6, 60, 40);
    }


}
