package com.mygdx.game.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StageStore {

    public static final StageStore I = new StageStore();

    public List<Stage> stages = new ArrayList<>();

    public Stage currentStage = null;

    public void loadAll() {
        Json json1 = new Json();
        stages = json1.fromJson(stages.getClass(), Gdx.files.internal("data/stages/stages.json"));
    }

    public void load(String id) {
        Stage stage = stages.stream().filter(stage1 -> stage1.id.equals(id)).findFirst().orElse(null);
        stage.load();
        this.currentStage = stage;
    }

}
