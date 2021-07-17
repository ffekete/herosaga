package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.character.Character;
import com.mygdx.game.character.CharacterAnimationRenderer;
import com.mygdx.game.map.CaveDungeonCreator;
import com.mygdx.game.map.Tile;
import com.mygdx.game.map.util.DungeonSpaceSpotter;
import com.mygdx.game.store.CameraStore;
import com.mygdx.game.store.CharacterStore;
import com.mygdx.game.store.MapStore;
import com.mygdx.game.store.PhysicsStore;

public class GameInitializer {

    public static final GameInitializer I = new GameInitializer();

    public void init(int level) {
        reGenerate(level);
        Vector2 startPoint = DungeonSpaceSpotter.findEmptySpace(3);
        CameraStore.I.orthographicCamera.position.x = startPoint.x;
        CameraStore.I.orthographicCamera.position.y = startPoint.y;

        // place player
        CharacterStore.player = new Character(startPoint.x, startPoint.y);
        PhysicsStore.characters.add(CharacterStore.player);

        CharacterAnimationRenderer.I.addAnimations(CharacterStore.player);
    }

    public void reGenerate(int level) {
        if (level == 1) {
            CaveDungeonCreator caveDungeonCreator = new CaveDungeonCreator(Tile.Rock, Tile.CaveBackground);
            MapStore.I.dungeon = caveDungeonCreator.create(6, 60, 40);
        }
    }
}
