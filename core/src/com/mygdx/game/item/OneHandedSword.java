package com.mygdx.game.item;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.graph.AtlasUnpacker;

public class OneHandedSword extends Equipment {

    private TextureRegion textureRegion;

    public OneHandedSword() {
        super(null, null, null);
    }

    public OneHandedSword(String name, String id, String description) {
        super(name, id, description);
        this.textureRegion = AtlasUnpacker.I.atlas.findRegion("items/weapons/ShortSword");
    }

    @Override
    public Item cloneItem() {
        return new OneHandedSword(this.name, this.id, this.description);
    }

    @Override
    public void draw(SpriteBatch batch, AbstractActor actor) {
        switch (actor.state) {

            case Idle:
            case Walking:
                batch.setColor(Color.WHITE);
                batch.draw(textureRegion, actor.x + 16, actor.y+16, 0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 1, 1, 0);
                break;
            case Attacking:

                break;
            case Dead:

                break;
            default:

        }
    }
}
