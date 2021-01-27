package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.graph.AtlasUnpacker;
import com.mygdx.game.state.State;
import com.mygdx.game.store.ActorStore;
import com.mygdx.game.store.ItemStore;
import com.mygdx.game.store.StageStore;
import com.mygdx.game.store.StateStore;

public class Crawler extends ApplicationAdapter {

	SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		AtlasUnpacker.I.unpack();
		ItemStore.I.load();
		ActorStore.I.load();
		StageStore.I.loadAll();
		StateStore.I.loadAll();
		StageStore.I.load(StateStore.I.state.stageId);
		StateStore.I.applyOverrides();
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		StageStore.I.currentStage.actors.forEach(abstractActor -> abstractActor.draw(batch));
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
