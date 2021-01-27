package com.mygdx.game.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.state.State;

public class StateStore {

    public static final StateStore I = new StateStore();

    public State state;

    public void loadAll() {
        Json json1 = new Json();
        state = json1.fromJson(State.class, Gdx.files.internal("data/state/state.json"));
    }

    public void applyOverrides() {
        state.applyOverrides();
    }
}
