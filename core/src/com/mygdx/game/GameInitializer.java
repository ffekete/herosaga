package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.character.Character;
import com.mygdx.game.map.CaveDungeonCreator;
import com.mygdx.game.map.Tile;
import com.mygdx.game.map.service.DungeonSpaceSpotter;
import com.mygdx.game.store.CameraStore;
import com.mygdx.game.store.CharacterStore;
import com.mygdx.game.store.MapStore;

public class GameInitializer {

    public static final GameInitializer I = new GameInitializer();

    public void init() {
        reGenerate();
        Vector2 startPoint = DungeonSpaceSpotter.findEmptySpace(3);
        CameraStore.I.orthographicCamera.position.x = startPoint.x;
        CameraStore.I.orthographicCamera.position.y = startPoint.y;

        // place player
        CharacterStore.I.player = new Character(startPoint.x, startPoint.y, Character.Class.Rogue);
    }

    public void reGenerate() {
        CaveDungeonCreator caveDungeonCreator = new CaveDungeonCreator(Tile.Rock, Tile.CaveBackground);
        MapStore.I.dungeon = caveDungeonCreator.create(6, 60, 40);
    }
}
