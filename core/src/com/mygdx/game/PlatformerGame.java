package com.mygdx.game;

import com.badlogic.gdx.Game;

public class PlatformerGame extends Game {
    @Override
    public void create() {
        setScreen(new DungeonCrawlerScreen(1));
    }
}
