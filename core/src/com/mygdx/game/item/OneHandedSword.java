package com.mygdx.game.item;

import com.mygdx.game.graph.AtlasUnpacker;

public class OneHandedSword extends Weapon {

    public OneHandedSword() {
        super(null, null, null);
    }

    public OneHandedSword(String name, String id, String description) {
        super(name, id, description);
        super.textureRegion = AtlasUnpacker.I.atlas.findRegion(String.format("items/weapons/%s", id));
    }

    @Override
    public Item cloneItem() {
        return new OneHandedSword(this.name, this.id, this.description);
    }




}
