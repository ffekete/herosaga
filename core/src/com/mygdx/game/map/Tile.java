package com.mygdx.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum Tile {

    None(false),
    Grass(true),
    Rock(true),
    WoodenPlatform(false);

    boolean tileSet;

    Tile(boolean tileSet) {
        this.tileSet = tileSet;
    }

    private TextureRegion woodenPlatformRegion = new TextureRegion(new Texture(Gdx.files.internal("WoodenPlatform.png")));
    private TextureRegion grassRegion = new TextureRegion(new Texture(Gdx.files.internal("GrassTileset.png")));
    private TextureRegion rockRegion = new TextureRegion(new Texture(Gdx.files.internal("CaveTileset.png")));

    public TextureRegion mapToRegion(Tile tile) {
        switch (tile) {

            case None:
                return null;
            case Grass:
                return grassRegion;
            case Rock:
                return rockRegion;
            case WoodenPlatform:
                return woodenPlatformRegion;

        }
        return null;
    }

}
