package com.mygdx.game.graph;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AtlasUnpacker {

    public static final AtlasUnpacker I = new AtlasUnpacker();

    public TextureAtlas atlas;

    public void unpack() {
        atlas = new TextureAtlas(Gdx.files.internal("atlas/pack.atlas"));
        TextureAtlas.AtlasRegion region = atlas.findRegion("races/HumanMale");
    }

}
