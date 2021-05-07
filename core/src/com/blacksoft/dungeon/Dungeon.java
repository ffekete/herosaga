package com.blacksoft.dungeon;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.blacksoft.dungeon.actions.TileCleaner;

import static com.blacksoft.animation.TileAnimationProvider.getAnimatedTiledMapTile;
import static com.blacksoft.state.Config.MAP_HEIGHT;
import static com.blacksoft.state.Config.MAP_WIDTH;
import static com.blacksoft.state.Config.TEXTURE_SIZE;

public class Dungeon {

    private static final Tile DEFAULT_TILE = Tile.Rock;
    public Node[][] nodes = new Node[MAP_WIDTH][MAP_HEIGHT];

    public TiledMap tiledMap;

    public Dungeon() {

        tiledMap = new TiledMap();

        TiledMapTileLayer layer = new TiledMapTileLayer(MAP_WIDTH, MAP_HEIGHT, TEXTURE_SIZE, TEXTURE_SIZE);
        layer.setName("dungeon");
        tiledMap.getLayers().add(layer);

        for (int i = 0; i < MAP_WIDTH; i++) {
            for (int j = 0; j < MAP_HEIGHT; j++) {
                nodes[i][j] = new Node();

                nodes[i][j].tile = DEFAULT_TILE;
                nodes[i][j].x = i;
                nodes[i][j].y = j;

                AnimatedTiledMapTile tile = getAnimatedTiledMapTile(DEFAULT_TILE);
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(tile);
                layer.setCell(i, j, cell);
            }
        }

        TileCleaner.clean(this, 0, MAP_HEIGHT / 2);
    }
}
