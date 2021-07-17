package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.controls.PlayerController;
import com.mygdx.game.stage.action.camera.FollowCameraAction;
import com.mygdx.game.store.CameraStore;
import com.mygdx.game.store.CharacterStore;
import com.mygdx.game.store.MapStore;

public class DungeonCrawlerScreen extends ScreenAdapter {

    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    OrthographicCamera camera;
    Viewport viewport;
    Stage stage;
    PlayerController playerController;

    private int level;

    public DungeonCrawlerScreen(int level) {
        this.level = level;
    }

    @Override
    public void show() {

        stage = new Stage();

        playerController = new PlayerController();

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        CameraStore.I.orthographicCamera = camera;

        shapeRenderer = new ShapeRenderer();

        camera.zoom = 1f;
        viewport = new FitViewport(200, 160, camera);
        viewport.apply(true);

        GameInitializer.I.init(level);

        camera.position.x = CharacterStore.player.position.x;
        camera.position.y = CharacterStore.player.position.y;
        CharacterStore.player.addAction(new FollowCameraAction());

        stage.addActor(CharacterStore.player);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {

        playerController.update(delta);

        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // draw here

        stage.act(delta);

        MapStore.I.dungeon.render(batch);

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
