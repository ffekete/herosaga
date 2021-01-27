package com.mygdx.game;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class Packer {
    public static void main (String[] args) throws Exception {
        TexturePacker.process("core/assets/", "core/assets/atlas", "pack");
    }
}
