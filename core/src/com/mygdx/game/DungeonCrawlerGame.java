package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.character.Character;
import com.mygdx.game.character.CharacterAnimationRenderer;
import com.mygdx.game.stage.action.GravityAction;
import com.mygdx.game.stage.action.MovementAction;
import com.mygdx.game.store.CameraStore;
import com.mygdx.game.store.CharacterStore;
import com.mygdx.game.store.MapStore;

public class DungeonCrawlerGame extends ApplicationAdapter {

    SpriteBatch batch;
    OrthographicCamera camera;
    Viewport viewport;
    Stage stage;

    @Override
    public void create() {

        stage = new Stage();

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        CameraStore.I.orthographicCamera = camera;

        camera.zoom = 1f;
        camera.position.x = 350;
        camera.position.y = 400;
        viewport = new FitViewport(200, 160, camera);
        viewport.apply();

        GameInitializer.I.init();

        CharacterStore.I.player.addAction(new GravityAction(CharacterStore.I.player, 1f));
        stage.addActor(CharacterStore.I.player);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {

                if (keycode == Input.Keys.UP) {
                    CharacterStore.I.player.y += 32;
                }

                if (keycode == Input.Keys.LEFT) {

                    CharacterStore.I.player.addAction(new MovementAction(CharacterStore.I.player, Character.Direction.Left, -1f * CharacterStore.I.player.getSpeed()));
                    CharacterStore.I.player.state = Character.State.Running;
                }

                if (keycode == Input.Keys.RIGHT) {
                    CharacterStore.I.player.addAction(new MovementAction(CharacterStore.I.player, Character.Direction.Right, CharacterStore.I.player.getSpeed()));
                    CharacterStore.I.player.state = Character.State.Running;
                }

                if (keycode == Input.Keys.DOWN) {
                    CharacterAnimationRenderer.I.resetAnimation(CharacterStore.I.player);
                    CharacterStore.I.player.state = Character.State.Squatting;
                }

                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.LEFT && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    CharacterStore.I.player.state = Character.State.Idle;
                }

                if (keycode == Input.Keys.RIGHT && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    CharacterStore.I.player.state = Character.State.Idle;
                }

                if (keycode == Input.Keys.DOWN) {
                    CharacterStore.I.player.state = Character.State.Idle;
                    CharacterAnimationRenderer.I.resetAnimation(CharacterStore.I.player);
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

        stage.act();

        camera.position.x = CharacterStore.I.player.x;
        camera.position.y = CharacterStore.I.player.y;
        camera.update();

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
