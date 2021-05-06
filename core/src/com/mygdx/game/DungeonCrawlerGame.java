package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.character.Character;
import com.mygdx.game.character.CharacterAnimationRenderer;
import com.mygdx.game.stage.action.*;
import com.mygdx.game.stage.action.camera.FollowCameraAction;
import com.mygdx.game.store.CameraStore;
import com.mygdx.game.store.CharacterStore;
import com.mygdx.game.store.MapStore;

public class DungeonCrawlerGame extends ApplicationAdapter {


    ShapeRenderer shapeRenderer;
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

        shapeRenderer = new ShapeRenderer();

        camera.zoom = 1f;
        camera.position.x = 350;
        camera.position.y = 400;
        viewport = new FitViewport(200, 160, camera);
        viewport.apply();

        GameInitializer.I.init();

        CharacterStore.I.player.addAction(new GravityAction(CharacterStore.I.player, 1f));
        CharacterStore.I.player.addAction(new PlayerMovementSpeedAction());
        CharacterStore.I.player.addAction(new PlayerJumpHeightAction());
        CharacterStore.I.player.addAction(new HorizontalGravityAction(CharacterStore.I.player));
        CharacterStore.I.player.addAction(new PositionCorrectionAction(CharacterStore.I.player));
        CharacterStore.I.player.addAction(new FollowCameraAction());

        stage.addActor(CharacterStore.I.player);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {

                if (keycode == Input.Keys.UP) {
                    CharacterAnimationRenderer.I.resetAnimation(CharacterStore.I.player);
                    CharacterStore.I.player.overrideState = Character.State.Climbing;
                }

                if (keycode == Input.Keys.LEFT) {

                    CharacterStore.I.player.addAction(new MovementAction(CharacterStore.I.player, Character.Direction.Left));
                    CharacterStore.I.player.state = Character.State.Running;
                }

                if (keycode == Input.Keys.RIGHT) {
                    CharacterStore.I.player.addAction(new MovementAction(CharacterStore.I.player, Character.Direction.Right));
                    CharacterStore.I.player.state = Character.State.Running;
                }

                if (keycode == Input.Keys.DOWN) {
                    CharacterAnimationRenderer.I.resetAnimation(CharacterStore.I.player);
                    CharacterStore.I.player.state = Character.State.Squatting;
                }

                if (keycode == Input.Keys.SPACE) {
                    CharacterAnimationRenderer.I.resetAnimation(CharacterStore.I.player);
                    CharacterStore.I.player.overrideState = Character.State.JumpingAnticipation;

                    SequenceAction action = new SequenceAction();
                    action.addAction(new JumpingAnticipationAction(0.2f, CharacterStore.I.player));
                    action.addAction(new JumpingAction(CharacterStore.I.player));

                    CharacterStore.I.player.addAction(action);
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

        MapStore.I.dungeon.render(batch);

        batch.end();

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(100, 10, CharacterStore.I.player.runningSpeed * 100, 10);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(100, 25, CharacterStore.I.player.jumpHeight * 100, 10);
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
