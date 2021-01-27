package com.mygdx.game.store;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.*;
import com.mygdx.game.item.Armor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ActorStoreActorDTO {

    public Float x, y;
    public String id;
    public String name;
    public String gender;
    public String race;
    public String armor;
    public HashMap<String, String> bodyPartColours;
    public HashMap<String, Integer> attributes;
    public String kit;
    public HashMap<String, Integer> skills;

}
