package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.store.CameraStore;

public class Dungeon {

    int width;
    int height;
    int[][] map;
    TextureRegion textureRegion;

    public Dungeon(int width,
                   int height,
                   TextureRegion textureRegion) {
        this.width = width;
        this.height = height;
        this.map = new int[width][height];
        this.textureRegion = textureRegion;
    }

    public void setTile(int x,
                        int y,
                        int value) {
        this.map[x][y] = value;
    }

    public int getTile(int x,
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
                render(i, j, spriteBatch);
            }
        }
    }

    private void render(int x,
                        int y,
                        SpriteBatch spriteBatch) {
        if(!(CameraStore.I.orthographicCamera.frustum.pointInFrustum(x * 16 + 16, y * 16 + 16, 0) ||
                CameraStore.I.orthographicCamera.frustum.pointInFrustum(x * 16, y * 16, 0))) {
            return;
        }

        if (map[x][y] != 0) {
            spriteBatch.draw(getBaseTileTextureRegion(x, y), x * 16, y * 16);
            renderDecoration(x, y, spriteBatch);
        }
    }

    private TextureRegion getBaseTileTextureRegion(int x,
                                                   int y) {

        int v = 0;
        if (x == 0 || (x > 0 && map[x - 1][y] == 1)) {
            v += 1;
        }

        if (y == 0 || (y > 0 && map[x][y - 1] == 1)) {
            v += 8;
        }

        if (x == map.length - 1 || (x < map.length - 1 && map[x + 1][y] == 1)) {
            v += 4;
        }

        if (y == map[0].length - 1 || (y < map[0].length - 1 && map[x][y + 1] == 1)) {
            v += 2;
        }

        if (v == 0) textureRegion.setRegion(48, 48, 16, 16);
        if (v == 1) textureRegion.setRegion(32, 48, 16, 16);
        if (v == 2) textureRegion.setRegion(48, 32, 16, 16);
        if (v == 3) textureRegion.setRegion(32, 32, 16, 16);
        if (v == 4) textureRegion.setRegion(0, 48, 16, 16);
        if (v == 5) textureRegion.setRegion(16, 48, 16, 16);
        if (v == 6) textureRegion.setRegion(0, 32, 16, 16);
        if (v == 7) textureRegion.setRegion(16, 32, 16, 16);
        if (v == 8) textureRegion.setRegion(48, 0, 16, 16);
        if (v == 9) textureRegion.setRegion(32, 0, 16, 16);
        if (v == 10) textureRegion.setRegion(48, 16, 16, 16);
        if (v == 11) textureRegion.setRegion(32, 16, 16, 16);
        if (v == 12) textureRegion.setRegion(0, 0, 16, 16);
        if (v == 13) textureRegion.setRegion(16, 0, 16, 16);
        if (v == 14) textureRegion.setRegion(0, 16, 16, 16);
        if (v == 15) textureRegion.setRegion(16, 16, 16, 16);

        return textureRegion;
    }

    private void renderDecoration(int x,
                                  int y,
                                  SpriteBatch spriteBatch) {
        int v = 0;
        if (x == 0 || (x > 0 && map[x - 1][y] == 1)) {
            v += 1;
        }

        if (y == 0 || (y > 0 && map[x][y - 1] == 1)) {
            v += 8;
        }

        if (x == map.length - 1 || (x < map.length - 1 && map[x + 1][y] == 1)) {
            v += 4;
        }

        if (y == map[0].length - 1 || (y < map[0].length - 1 && map[x][y + 1] == 1)) {
            v += 2;
        }

        // upper grass
        if (v == 0 || v == 1 || v == 4 || v == 5 || v == 8 || v == 9 || v == 12 || v == 13) {
            textureRegion.setRegion(64, 32, 16, 16);
            spriteBatch.draw(textureRegion, x * 16, y * 16 + 16);
        }

        // lower grass
        if (v == 0 || v == 6 || v == 7 || v == 3 || v == 2 || v == 4 || v == 5 || v == 1) {
            textureRegion.setRegion(80, 48, 16, 16);
            spriteBatch.draw(textureRegion, x * 16, y * 16 - 16);
        }

        // right grass
        if (v == 0 || v == 9 || v == 11 || v == 3 || v == 1 || v == 8 || v == 10 || v == 2) {
            textureRegion.setRegion(80, 32, 16, 16);
            spriteBatch.draw(textureRegion, x * 16 + 16, y * 16);
        }

        // left grass
        if (v == 0 || v == 12 || v == 14 || v == 6 || v == 4 || v == 8 || v == 10 || v == 2) {
            textureRegion.setRegion(64, 48, 16, 16);
            spriteBatch.draw(textureRegion, x * 16 - 16, y * 16);
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
}
