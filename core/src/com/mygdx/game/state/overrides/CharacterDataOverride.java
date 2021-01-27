package com.mygdx.game.state.overrides;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.item.Armor;
import com.mygdx.game.state.Override;
import com.mygdx.game.store.ActorStore;
import com.mygdx.game.store.ItemStore;

public class CharacterDataOverride implements Override {

    public String armorId;
    public Float x;
    public Float y;
    public String actorId;

    @java.lang.Override
    public void override() {
        AbstractActor actor = ActorStore.I.get(actorId);
        actor.x = x == null ? actor.x : x;
        actor.y = y == null ? actor.y : y;
        if(armorId != null) {
            Armor armor = (Armor) ItemStore.I.get(armorId);
            actor.armor = armor.cloneItem();
        }

        actor.loadAnimation();
    }
}
