package com.mygdx.game.state;

import java.util.List;
import java.util.Set;

public class State {

    public String stageId;
    public List<Override> overrides;

    public void applyOverrides() {

        // delete conflicting overrides

        for (Override override : overrides) {
            override.override();
        }
    }

}
