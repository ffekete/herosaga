package com.mygdx.game.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.actor.AbstractActor;

public class Equipment extends Item {

    public Equipment(String name, String id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public void draw(SpriteBatch batch, AbstractActor actor) {

    }
}
