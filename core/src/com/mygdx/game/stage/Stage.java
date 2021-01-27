package com.mygdx.game.stage;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.item.Armor;
import com.mygdx.game.store.ActorStore;
import com.mygdx.game.store.ItemStore;

import java.util.ArrayList;
import java.util.List;

public class Stage {

    public List<AbstractActor> actors;
    public String id;
    public String tileset;

    private List<StageDTO> actorData;

    public void load() {
        actors = new ArrayList<>();
        actorData.forEach(actorData -> {
            AbstractActor actor = ActorStore.I.get(actorData.id);
            actor.x = actorData.x;
            actor.y = actorData.y;
            actor.armor = actorData.armorId != null ? ((Armor) ItemStore.I.get(actorData.armorId)).cloneItem(): actor.armor;
            actors.add(actor);
        });
    }
}
