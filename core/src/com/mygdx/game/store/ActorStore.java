package com.mygdx.game.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.actor.AbstractActor;

import java.util.ArrayList;
import java.util.List;

public class ActorStore {

    public static final ActorStore I = new ActorStore();

    public List<AbstractActor> actors = new ArrayList<>();

    public AbstractActor get(String id) {
        return actors.stream().filter(actors -> actors.id.equals(id)).findFirst().orElse(null);
    }

    public void load() {
        Json json1 = new Json();
        actors = json1.fromJson(actors.getClass(), Gdx.files.internal("data/actors/actors.json"));
        actors.forEach(AbstractActor::loadAnimation);
        System.out.println(actors.get(0).name);
        System.out.println(actors.get(1).name);
    }

}
