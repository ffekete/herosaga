package com.mygdx.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public enum TileType {

    None(false, false, false, false, null),
    Grass(true, true, true, true, new Rectangle(0, 0, 16, 16)),
    Rock(true, true, true, true, new Rectangle(0, 0, 16, 16)),
    CaveBackground(false, false, false, false, new Rectangle(0, 0, 16, 16)),
    WoodenPlatform(false, false, true, false, new Rectangle(0, 0, 16, 16));

    boolean tileSet;
    public boolean obstacleFromSide;
    public boolean obstacleFromUp;
    public boolean obstacleFromDown;
    public Rectangle bounds;

    TileType(boolean tileSet,
             boolean obstacleFromSide,
             boolean obstacleFromUp,
             boolean obstacleFromDown,
             Rectangle bounds) {
        this.tileSet = tileSet;
        this.obstacleFromSide = obstacleFromSide;
        this.obstacleFromUp = obstacleFromUp;
        this.obstacleFromDown = obstacleFromDown;
        this.bounds = bounds;
    }

    private TextureRegion woodenPlatformRegion = new TextureRegion(new Texture(Gdx.files.internal("WoodenPlatform.png")));
    private TextureRegion grassRegion = new TextureRegion(new Texture(Gdx.files.internal("GrassTileset.png")));
    private TextureRegion rockRegion = new TextureRegion(new Texture(Gdx.files.internal("CaveTileset.png")));
    private TextureRegion caveBackgroundRegion = new TextureRegion(new Texture(Gdx.files.internal("CaveBackground.png")));

    public TextureRegion mapToRegion(TileType tileType) {
        switch (tileType) {

            case None:
                return null;
            case Grass:
                return grassRegion;
            case Rock:
                return rockRegion;
            case WoodenPlatform:
                return woodenPlatformRegion;
            case CaveBackground:
                return caveBackgroundRegion;
        }
        return null;
    }

}
