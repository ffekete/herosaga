package com.mygdx.game.item;

public class Armor extends Item {

    public Armor() {
    }

    public Armor(String name, String id, String description) {
        this.name = name;
        this.id = id;
    }

    public Armor cloneItem() {
        return new Armor(this.name, this.id, this.description);
    }
}
