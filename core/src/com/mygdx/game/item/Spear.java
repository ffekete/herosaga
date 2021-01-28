package com.mygdx.game.item;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.graph.AtlasUnpacker;

public class Spear extends Equipment {

    private TextureRegion textureRegion;

    public Spear() {
        super(null, null, null);
    }

    public Spear(String name, String id, String description) {
        super(name, id, description);
        this.textureRegion = AtlasUnpacker.I.atlas.findRegion(String.format("items/weapons/%s", id));
    }

    @Override
    public Item cloneItem() {
        return new Spear(this.name, this.id, this.description);
    }

    @Override
    public void draw(SpriteBatch batch, AbstractActor actor, float xOffset, float yOffset, boolean flip) {
        switch (actor.state) {

            case Idle:
            case Walking:
                batch.setColor(Color.WHITE);
                float calculatedXOffset = (actor.getCharacterAnimation().meta.width / 3f);
                float calculatedYOffset = (actor.getCharacterAnimation().meta.height / 4f);
                batch.draw(textureRegion.getTexture(), actor.x - calculatedXOffset - xOffset, actor.y + calculatedYOffset + yOffset, 0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 1, 1, 0, textureRegion.getRegionX(), textureRegion.getRegionY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), flip, false);
                break;
            case Attacking:

                break;
            case Dead:

                break;
            default:

        }
    }
}
