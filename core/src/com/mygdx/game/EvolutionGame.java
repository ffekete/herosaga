package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EvolutionGame extends ApplicationAdapter {

	SpriteBatch batch;
	Camera camera;
	Viewport viewport;

	@Override
	public void create () {

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
		// draw here
		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
