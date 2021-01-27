package com.mygdx.game.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemStore {

    public static final ItemStore I = new ItemStore();

    public List<Item> items = new ArrayList<>();

    public Item get(String id) {
        return items.stream().filter(item -> item.id.equals(id)).findFirst().orElse(null);
    }

    public void load() {
        Json json1 = new Json();
        items = json1.fromJson(items.getClass(), Gdx.files.internal("data/items/items.json"));
    }

}
