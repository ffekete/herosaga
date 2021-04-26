package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.map.CaveDungeonCreator;
import com.mygdx.game.map.Dungeon;
import com.mygdx.game.store.CameraStore;

public class DungeonCrawlerGame extends ApplicationAdapter {

    SpriteBatch batch;
    OrthographicCamera camera;
    Viewport viewport;

    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    CaveDungeonCreator caveDungeonCreator;
    Dungeon dungeon;

    @Override
    public void create() {


        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        CameraStore.I.orthographicCamera = camera;

        camera.zoom = 1f;
        camera.position.x = 350;
        camera.position.y = 400;
        viewport = new FitViewport(1200, 800, camera);
        viewport.apply();

        tiledMap = new TiledMap();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        caveDungeonCreator = new CaveDungeonCreator();
        dungeon = caveDungeonCreator.create(6, 50, 50, new TextureRegion(new Texture(Gdx.files.internal("CaveTileset.png"))));

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.LEFT) {
                    camera.position.x -= 10f;
                    camera.update();
                }

                if (keycode == Input.Keys.RIGHT) {
                    camera.position.x += 10f;
                    camera.update();
                }

                if (keycode == Input.Keys.UP) {
                    camera.position.y += 10f;
                    camera.update();
                }

                if (keycode == Input.Keys.DOWN) {
                    camera.position.y -= 10f;
                    camera.update();
                }

                if(keycode == Input.Keys.SPACE) {
                    dungeon = caveDungeonCreator.create(6, 50, 50, new TextureRegion(new Texture(Gdx.files.internal("CaveTileset.png"))));
                }

                return true;
            }
        });
    }

    @Override
    public void render() {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // draw here

        dungeon.render(batch);

        batch.end();
    }

    @Override
    public void resize(int width,
                       int height) {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
