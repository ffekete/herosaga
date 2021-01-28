package com.mygdx.game.item;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.graph.AtlasUnpacker;

import java.util.Random;

public class Spear extends Equipment {

    private TextureRegion textureRegion;

    public Spear() {
        super(null, null, null);
    }

    private float rotation = -250f;
    private int attackDelay = 0;
    private int attackAnimation = 0;
    private float attackXOffset = 5;

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
                if (attackAnimation == 0) {
                    calculateRotation(actor, flip);
                } else {
                    calculateOffset(actor, flip);
                }
                batch.setColor(Color.WHITE);
                calculatedXOffset = -actor.getCharacterAnimation().meta.width / 2f;
                calculatedYOffset = (actor.getCharacterAnimation().meta.height / 4f);

                boolean flipX;
                boolean flipY;

                if(flip) {
                    flipX = (attackAnimation == 0 ? !flip : flip);
                    flipY = (attackAnimation == 0 ? flip : !flip);
                } else {
                    flipX = false;
                    flipY = false;
                }

                batch.draw(textureRegion.getTexture(), actor.x - calculatedXOffset - xOffset + attackXOffset, actor.y + calculatedYOffset + yOffset, 0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 1, 1, rotation, textureRegion.getRegionX(), textureRegion.getRegionY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), flipX, flipY);
                break;
            case Dead:

                break;
            default:

        }
    }

    private void calculateOffset(AbstractActor actor, boolean flip) {
        if (attackDelay > 0) {
            attackDelay--;
        } else {

            if (flip) {
                attackXOffset -= 3f;

                if (attackXOffset <= -40) {
                    attackDelay = actor.getAttackSpeed();
                    attackAnimation = new Random().nextInt(2);
                    attackXOffset = 0;

                    if(attackAnimation == 0) {
                        attackXOffset = 0;
                        rotation = -290;
                    } else {
                        attackXOffset = -15;
                        rotation = 0;
                    }
                    // todo attack calc trigger here
                }
            } else {
                attackXOffset += 3f;

                if (attackXOffset >= 15) {
                    attackDelay = actor.getAttackSpeed();
                    attackAnimation = new Random().nextInt(2);

                    if(attackAnimation == 0) {
                        attackXOffset = 0;
                        rotation = -250;
                    } else {
                        attackXOffset = -15;
                        rotation = 0;
                    }
                    // todo attack calc trigger here
                }
            }
        }
    }

    private void calculateRotation(AbstractActor actor, boolean flip) {
        if (attackDelay > 0) {
            attackDelay--;
        } else {
            if (!flip) {
                rotation -= 9f;
                if (rotation <= -380) {

                    attackDelay = actor.getAttackSpeed();
                    attackAnimation = new Random().nextInt(2);
                    if(attackAnimation == 0) {
                        attackXOffset = 0;
                        rotation = -250;
                    } else {
                        attackXOffset = -15;
                        rotation = 0;
                    }
                    // todo attack calc trigger here
                }
            } else {
                rotation += 9f;
                if (rotation >= -160) {

                    attackDelay = actor.getAttackSpeed();
                    attackAnimation = new Random().nextInt(2);
                    if(attackAnimation == 0) {
                        attackXOffset = 0;
                        rotation = -290;
                    } else {
                        attackXOffset = -15;
                        rotation = 0;
                    }
                    // todo attack calc trigger here
                }
            }
        }
    }
}
