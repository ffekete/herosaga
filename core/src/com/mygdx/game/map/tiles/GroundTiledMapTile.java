package com.mygdx.game.map.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;

/**
 * @brief Represents a non changing {@link TiledMapTile} (can be cached)
 */
public class GroundTiledMapTile implements TiledMapTile {

    private int[][] map;
    int x;
    int y;

    private int id;

    private BlendMode blendMode = BlendMode.ALPHA;

    private MapProperties properties;

    private MapObjects objects;

    private TextureRegion animatedDrawableTile;

    private float offsetX;

    private float offsetY;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public BlendMode getBlendMode() {
        return blendMode;
    }

    @Override
    public void setBlendMode(BlendMode blendMode) {
        this.blendMode = blendMode;
    }

    @Override
    public MapProperties getProperties() {
        if (properties == null) {
            properties = new MapProperties();
        }
        return properties;
    }

    @Override
    public MapObjects getObjects() {
        if (objects == null) {
            objects = new MapObjects();
        }
        return objects;
    }

    @Override
    public TextureRegion getTextureRegion() {

        int v = 0;
        if(x == 0 || (x > 0 && map[x-1][y] == 1)) {
            v += 1;
        }

        if(y == 0 || (y > 0 && map[x][y-1] == 1)) {
            v += 8;
        }

        if(x == map.length -1 || (x < map.length-1 && map[x+1][y] == 1)) {
            v += 4;
        }

        if(y == map[0].length-1 || (y < map[0].length-1 && map[x][y+1] == 1)) {
            v += 2;
        }

        if(v == 0) animatedDrawableTile.setRegion(48, 48, 16 ,16);
        if(v == 1) animatedDrawableTile.setRegion(32, 48, 16 ,16);
        if(v == 2) animatedDrawableTile.setRegion(48, 32, 16 ,16);
        if(v == 3) animatedDrawableTile.setRegion(32, 32, 16 ,16);
        if(v == 4) animatedDrawableTile.setRegion(0, 48, 16 ,16);
        if(v == 5) animatedDrawableTile.setRegion(16, 48, 16 ,16);
        if(v == 6) animatedDrawableTile.setRegion(0, 32, 16 ,16);
        if(v == 7) animatedDrawableTile.setRegion(16, 32, 16 ,16);
        if(v == 8) animatedDrawableTile.setRegion(48, 0, 16 ,16);
        if(v == 9) animatedDrawableTile.setRegion(32, 0, 16 ,16);
        if(v == 10) animatedDrawableTile.setRegion(48, 16, 16 ,16);
        if(v == 11) animatedDrawableTile.setRegion(32, 16, 16 ,16);
        if(v == 12) animatedDrawableTile.setRegion(0, 0, 16 ,16);
        if(v == 13) animatedDrawableTile.setRegion(16, 0, 16 ,16);
        if(v == 14) animatedDrawableTile.setRegion(0, 16, 16 ,16);
        if(v == 15) animatedDrawableTile.setRegion(16, 16, 16 ,16);


        return animatedDrawableTile;
    }

    @Override
    public void setTextureRegion(TextureRegion textureRegion) {

    }

    @Override
    public float getOffsetX() {
        return offsetX;
    }

    @Override
    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    @Override
    public float getOffsetY() {
        return offsetY;
    }

    @Override
    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public GroundTiledMapTile(TextureRegion animatedDrawableTile, int[][] map, int x, int y) {
        this.animatedDrawableTile = animatedDrawableTile;
        this.map = map;
        this.x = x;
        this.y = y;
    }
}