package com.mygdx.game.item;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.graph.AtlasUnpacker;

public class OneHandedSword extends Equipment {

    private TextureRegion textureRegion;
    private float rotation = -250f;
    private int attackDelay = 0;

    public OneHandedSword() {
        super(null, null, null);
    }

    public OneHandedSword(String name, String id, String description) {
        super(name, id, description);
        this.textureRegion = AtlasUnpacker.I.atlas.findRegion(String.format("items/weapons/%s", id));
    }

    @Override
    public Item cloneItem() {
        return new OneHandedSword(this.name, this.id, this.description);
    }

    @Override
    public void draw(SpriteBatch batch, AbstractActor actor, float xOffset, float yOffset, boolean flip) {
        switch (actor.state) {

            case Idle:
            case Walking:
                batch.setColor(Color.WHITE);
                float calculatedXOffset = (flip ? -1 : 1) * (textureRegion.getRegionWidth() / 4f);
                float calculatedYOffset = (actor.getCharacterAnimation().meta.height / 5f);
                batch.draw(textureRegion.getTexture(), actor.x + calculatedXOffset + xOffset, actor.y + calculatedYOffset + yOffset, 0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 1, 1, 0, textureRegion.getRegionX(), textureRegion.getRegionY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), flip, false);
                break;
            case Attacking:

                calculateRotation(actor, flip);

                batch.setColor(Color.WHITE);
                if(flip) {
                    calculatedXOffset = 5;
                } else {
                    calculatedXOffset = (textureRegion.getRegionWidth()) - 5;
                }

                if(flip) {
                    calculatedYOffset = (actor.getCharacterAnimation().meta.height / 3f);
                } else {
                    calculatedYOffset = (actor.getCharacterAnimation().meta.height / 5f);
                }

                batch.draw(textureRegion.getTexture(), actor.x + calculatedXOffset + xOffset, actor.y + calculatedYOffset + yOffset, 0, textureRegion.getRegionHeight() / 2f, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 1, 1, rotation, textureRegion.getRegionX(), textureRegion.getRegionY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), false, flip);

                break;
            case Dead:

                break;
            default:

        }
    }

    private void calculateRotation(AbstractActor actor, boolean flip) {
        if(attackDelay > 0) {
            attackDelay--;
        }
        else {
            if(!flip) {
                rotation -= 7f;
                if (rotation <= -380) {
                    rotation = -250;
                    attackDelay = actor.getAttackSpeed();
                    // todo attack calc trigger here
                }
            } else {
                rotation += 7f;
                if (rotation >= -160) {
                    rotation = -290;
                    attackDelay = actor.getAttackSpeed();
                    // todo attack calc trigger here
                }
            }
        }
    }
}
