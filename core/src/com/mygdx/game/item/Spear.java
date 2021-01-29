package com.mygdx.game.item;

import com.mygdx.game.graph.AtlasUnpacker;

public class Spear extends Weapon {

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
}
