package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.controls.InputMapping;
import com.mygdx.game.controls.KeyPressedAction;
import com.mygdx.game.physics.PhysicsParameters;
import com.mygdx.game.stage.action.GravityAction;
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

    private int level;

    public DungeonCrawlerScreen(int level) {
        this.level = level;
    }

    @Override
    public void show() {

        stage = new Stage();

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        CameraStore.I.orthographicCamera = camera;

        shapeRenderer = new ShapeRenderer();

        camera.zoom = 1f;
        viewport = new FitViewport(200, 160, camera);
        viewport.apply(true);

        GameInitializer.I.init(level);

        camera.position.x = CharacterStore.player.x;
        camera.position.y = CharacterStore.player.y;
        CharacterStore.player.addAction(new FollowCameraAction());

        stage.addActor(CharacterStore.player);

        stage.addAction(new GravityAction());
        stage.addAction(new KeyPressedAction());

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

        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // draw here

        stage.act(delta);

        MapStore.I.dungeon.render(batch);

        batch.end();

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(100, 10, CharacterStore.player.runningSpeed * 100, 10);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(100, 25, CharacterStore.player.jumpHeight * 100, 10);
        shapeRenderer.end();
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
