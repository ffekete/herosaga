package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.map.tiles.Adjacency;
import com.mygdx.game.store.CameraStore;

import static com.mygdx.game.map.tiles.Adjacency.*;

public class Dungeon {

    int width;
    int height;
    TileType[][] backgroundMap;
    TileType[][] map;
    public Rectangle[][] bounds;
    TileType baseTileType;
    TileType backgroundTileType;
    TextureRegion mapTextureRegion;
    TextureRegion backgroundTextureRegion;

    public Dungeon(int width,
                   int height,
                   TileType baseTileType,
                   TileType backgroundTileType) {
        this.width = width;
        this.height = height;
        this.map = new TileType[width][height];
        this.backgroundMap = new TileType[width][height];
        this.bounds = new Rectangle[width][height];
        this.baseTileType = baseTileType;
        this.backgroundTileType = backgroundTileType;
        this.mapTextureRegion = baseTileType.mapToRegion(baseTileType);
        this.backgroundTextureRegion = backgroundTileType.mapToRegion(backgroundTileType);
    }

    public void setTile(int x,
                        int y,
                        TileType value) {
        this.map[x][y] = value;

        if(value.bounds != null) {
            this.bounds[x][y] = new Rectangle(value.bounds);
            this.bounds[x][y].x = value.bounds.x + x * 16;
            this.bounds[x][y].y = value.bounds.y  + y * 16;
        } else {
            this.bounds[x][y] = null;
        }
    }

    public TileType getTile(int x,
                            int y) {
        return map[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void render(SpriteBatch spriteBatch) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                renderBackground(i, j, spriteBatch);
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                render(i, j, spriteBatch);
            }
        }
    }

    private void renderBackground(int x,
                                  int y,
                                  SpriteBatch spriteBatch) {
        if (!(CameraStore.I.orthographicCamera.frustum.pointInFrustum(x * 16 + 16, y * 16 + 16, 0) ||
                CameraStore.I.orthographicCamera.frustum.pointInFrustum(x * 16, y * 16, 0))) {
            return;
        }

        if (backgroundMap[x][y] != TileType.None) {
            spriteBatch.draw(getBackgroundTileTextureRegion(x, y), x * 16, y * 16);
        }
    }

    private void render(int x,
                        int y,
                        SpriteBatch spriteBatch) {
        if (!(CameraStore.I.orthographicCamera.frustum.pointInFrustum(x * 16 + 16, y * 16 + 16, 0) ||
                CameraStore.I.orthographicCamera.frustum.pointInFrustum(x * 16, y * 16, 0))) {
            return;
        }

        if (map[x][y] != TileType.None) {
            spriteBatch.draw(getBaseTileTextureRegion(x, y), x * 16, y * 16);
            renderDecoration(x, y, spriteBatch);
        }
    }

    private TextureRegion getBackgroundTileTextureRegion(int x,
                                                         int y) {

        // return part of the texture if it is not a tileset
        if (!backgroundMap[x][y].tileSet) {
            TextureRegion textureRegion = backgroundMap[x][y].mapToRegion(backgroundMap[x][y]);

            int xOffset = x % (textureRegion.getTexture().getWidth() / 16);
            int yOffset = y % (textureRegion.getTexture().getHeight() / 16);

            textureRegion.setRegion(xOffset * 16, yOffset * 16, 16, 16);
            return textureRegion;
        }

        TileType actualTileType = backgroundMap[x][y];

        int v = 0;
        if (x == 0 || (x > 0 && backgroundMap[x - 1][y] == actualTileType)) {
            v += 1;
        }

        if (y == 0 || (y > 0 && backgroundMap[x][y - 1] == actualTileType)) {
            v += 8;
        }

        if (x == backgroundMap.length - 1 || (x < backgroundMap.length - 1 && backgroundMap[x + 1][y] == actualTileType)) {
            v += 4;
        }

        if (y == backgroundMap[0].length - 1 || (y < backgroundMap[0].length - 1 && backgroundMap[x][y + 1] == actualTileType)) {
            v += 2;
        }

        Adjacency adjacency = Adjacency.fromNumber(v);

        if (adjacency == NONE) backgroundTextureRegion.setRegion(48, 48, 16, 16);
        if (adjacency == LEFT) backgroundTextureRegion.setRegion(32, 48, 16, 16);
        if (adjacency == UP) backgroundTextureRegion.setRegion(48, 32, 16, 16);
        if (adjacency == LEFT_UP) backgroundTextureRegion.setRegion(32, 32, 16, 16);
        if (adjacency == RIGHT) backgroundTextureRegion.setRegion(0, 48, 16, 16);
        if (adjacency == LEFT_RIGHT) backgroundTextureRegion.setRegion(16, 48, 16, 16);
        if (adjacency == UP_RIGHT) backgroundTextureRegion.setRegion(0, 32, 16, 16);
        if (adjacency == LEFT_UP_RIGHT) backgroundTextureRegion.setRegion(16, 32, 16, 16);
        if (adjacency == DOWN) backgroundTextureRegion.setRegion(48, 0, 16, 16);
        if (adjacency == LEFT_DOWN) backgroundTextureRegion.setRegion(32, 0, 16, 16);
        if (adjacency == UP_DOWN) backgroundTextureRegion.setRegion(48, 16, 16, 16);
        if (adjacency == LEFT_UP_DOWN) backgroundTextureRegion.setRegion(32, 16, 16, 16);
        if (adjacency == RIGHT_DOWN) backgroundTextureRegion.setRegion(0, 0, 16, 16);
        if (adjacency == LEFT_RIGHT_DOWN) backgroundTextureRegion.setRegion(16, 0, 16, 16);
        if (adjacency == UP_RIGHT_DOWN) backgroundTextureRegion.setRegion(0, 16, 16, 16);
        if (adjacency == ALL) backgroundTextureRegion.setRegion(16, 16, 16, 16);

        return backgroundTextureRegion;
    }

    private TextureRegion getBaseTileTextureRegion(int x,
                                                   int y) {

        // return full texture if it is not a tileset
        if (!map[x][y].tileSet) {
            return map[x][y].mapToRegion(map[x][y]);
        }

        TileType actualTileType = map[x][y];

        int v = 0;
        if (x == 0 || (x > 0 && map[x - 1][y] == actualTileType)) {
            v += 1;
        }

        if (y == 0 || (y > 0 && map[x][y - 1] == actualTileType)) {
            v += 8;
        }

        if (x == map.length - 1 || (x < map.length - 1 && map[x + 1][y] == actualTileType)) {
            v += 4;
        }

        if (y == map[0].length - 1 || (y < map[0].length - 1 && map[x][y + 1] == actualTileType)) {
            v += 2;
        }

        Adjacency adjacency = Adjacency.fromNumber(v);
        if (adjacency == NONE) mapTextureRegion.setRegion(48, 48, 16, 16);
        if (adjacency == LEFT) mapTextureRegion.setRegion(32, 48, 16, 16);
        if (adjacency == UP) mapTextureRegion.setRegion(48, 32, 16, 16);
        if (adjacency == LEFT_UP) mapTextureRegion.setRegion(32, 32, 16, 16);
        if (adjacency == RIGHT) mapTextureRegion.setRegion(0, 48, 16, 16);
        if (adjacency == LEFT_RIGHT) mapTextureRegion.setRegion(16, 48, 16, 16);
        if (adjacency == UP_RIGHT) mapTextureRegion.setRegion(0, 32, 16, 16);
        if (adjacency == LEFT_UP_RIGHT) mapTextureRegion.setRegion(16, 32, 16, 16);
        if (adjacency == DOWN) mapTextureRegion.setRegion(48, 0, 16, 16);
        if (adjacency == LEFT_DOWN) mapTextureRegion.setRegion(32, 0, 16, 16);
        if (adjacency == UP_DOWN) mapTextureRegion.setRegion(48, 16, 16, 16);
        if (adjacency == LEFT_UP_DOWN) mapTextureRegion.setRegion(32, 16, 16, 16);
        if (adjacency == RIGHT_DOWN) mapTextureRegion.setRegion(0, 0, 16, 16);
        if (adjacency == LEFT_RIGHT_DOWN) mapTextureRegion.setRegion(16, 0, 16, 16);
        if (adjacency == UP_RIGHT_DOWN) mapTextureRegion.setRegion(0, 16, 16, 16);
        if (adjacency == ALL) mapTextureRegion.setRegion(16, 16, 16, 16);

        return mapTextureRegion;
    }

    private void renderDecoration(int x,
                                  int y,
                                  SpriteBatch spriteBatch) {
        // return if it is not a tileset
        if (!map[x][y].tileSet) {
            return;
        }

        TileType actualTileType = map[x][y];

        int v = 0;
        if (x > 0 && map[x - 1][y] == actualTileType) {
            v += 1;
        }

        if (y > 0 && map[x][y - 1] == actualTileType) {
            v += 8;
        }

        if (x < map.length - 1 && map[x + 1][y] == actualTileType) {
            v += 4;
        }

        if (y < map[0].length - 1 && map[x][y + 1] == actualTileType) {
            v += 2;
        }

        Adjacency adjacency = Adjacency.fromNumber(v);

        // upper grass
        if (adjacency == NONE || adjacency == LEFT || adjacency == RIGHT || adjacency == LEFT_RIGHT || adjacency == DOWN
                || adjacency == LEFT_DOWN || adjacency == RIGHT_DOWN || adjacency == LEFT_RIGHT_DOWN) {
            mapTextureRegion.setRegion(64, 32, 16, 16);
            spriteBatch.draw(mapTextureRegion, x * 16, y * 16 + 16);
        }

        // lower grass
        if (adjacency == NONE || adjacency == UP_RIGHT || adjacency == LEFT_UP_RIGHT || adjacency == LEFT_UP ||
                adjacency == UP || adjacency == RIGHT || adjacency == LEFT_RIGHT || adjacency == LEFT) {
            mapTextureRegion.setRegion(80, 48, 16, 16);
            spriteBatch.draw(mapTextureRegion, x * 16, y * 16 - 16);
        }

        // right grass
        if (adjacency == NONE || adjacency == LEFT_DOWN || adjacency == LEFT_UP_DOWN || adjacency == LEFT_UP ||
                adjacency == LEFT || adjacency == DOWN || adjacency == UP_DOWN || adjacency == UP) {
            mapTextureRegion.setRegion(80, 32, 16, 16);
            spriteBatch.draw(mapTextureRegion, x * 16 + 16, y * 16);
        }

        // left grass
        if (adjacency == NONE || adjacency == RIGHT_DOWN || adjacency == UP_RIGHT_DOWN || adjacency == UP_RIGHT ||
                adjacency == RIGHT || adjacency == DOWN || adjacency == UP_DOWN || adjacency == UP) {
            mapTextureRegion.setRegion(64, 48, 16, 16);
            spriteBatch.draw(mapTextureRegion, x * 16 - 16, y * 16);
        }
    }

    public void debug() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(getTile(i, j) + " ");
            }
            System.out.println();
        }
    }

    public TileType getTileExactly(float px,
                                   float py) {
        return getTile(((int) px) / 16, ((int) py) / 16);
    }

    public TileType getTileBelow(float px,
                                 float py,
                                 float amount) {
        return getTile(((int) px) / 16, ((int) (py - amount)) / 16);
    }

    public TileType getTileToLeft(float px,
                                  float py,
                                  float amount) {
        return getTile(((int) (px - amount)) / 16, ((int) py) / 16);
    }

    public TileType getTileToRight(float px,
                                   float py,
                                   float amount) {
        return getTile(((int) (px + amount)) / 16, ((int) py) / 16);
    }

    public TileType getTileAbove(float px,
                                 float py,
                                 float amount) {
        return getTile(((int) px) / 16, ((int) (py + amount)) / 16);
    }
}
