package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.graph.AtlasUnpacker;
import com.mygdx.game.state.State;
import com.mygdx.game.store.ActorStore;
import com.mygdx.game.store.ItemStore;
import com.mygdx.game.store.StageStore;
import com.mygdx.game.store.StateStore;

public class Crawler extends ApplicationAdapter {

	SpriteBatch batch;
	Camera camera;
	Viewport viewport;

	@Override
	public void create () {

		AtlasUnpacker.I.unpack();
		ItemStore.I.load();
		ActorStore.I.load();
		StageStore.I.loadAll();
		StateStore.I.loadAll();
		StageStore.I.load(StateStore.I.state.stageId);
		StateStore.I.applyOverrides();

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		((OrthographicCamera)camera).zoom = 0.3f;
		viewport = new FitViewport(1200, 800, camera);
		viewport.apply();

	}

	@Override
	public void render () {
		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		StageStore.I.currentStage.actors.forEach(abstractActor -> abstractActor.draw(batch));
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		float aspectRatio = (float) width / (float) height;


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
