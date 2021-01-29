package com.mygdx.game.item;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.graph.AtlasUnpacker;

public class Shield extends Equipment {

    protected TextureRegion textureRegion;

    public Shield() {
        super(null, null, null);
    }


    public Shield(String name, String id, String description) {
        super(name, id, description);
        this.textureRegion = AtlasUnpacker.I.atlas.findRegion(String.format("items/shields/%s", id));
    }

    @Override
    public Item cloneItem() {
        return new Shield(this.name, this.id, this.description);
    }

    @Override
    public void draw(SpriteBatch batch, AbstractActor actor, float xOffset, float yOffset, boolean flip) {
        switch (actor.state) {

            case Idle:
            case Walking:
            case Attacking:
                batch.setColor(Color.WHITE);
                float calculatedXOffset = (actor.getCharacterAnimation().meta.width / 2f);
                float calculatedYOffset = (actor.getCharacterAnimation().meta.height / 5f);

                batch.draw(textureRegion.getTexture(), actor.x + calculatedXOffset - (this.textureRegion.getRegionWidth() / 1.75f) + 3, actor.y + calculatedYOffset, 0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 1, 1, 0, textureRegion.getRegionX(), textureRegion.getRegionY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), flip, false);
                break;
            case Dead:
                break;
            default:

        }
    }
}
