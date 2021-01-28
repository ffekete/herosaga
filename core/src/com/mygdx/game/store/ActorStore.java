package com.mygdx.game.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.actor.*;
import com.mygdx.game.item.Armor;
import com.mygdx.game.item.Equipment;

import java.util.ArrayList;
import java.util.List;

public class ActorStore {

    public static final ActorStore I = new ActorStore();

    public List<AbstractActor> actors = new ArrayList<>();

    private List<ActorStoreActorDTO> actorDTOS = new ArrayList<>();

    public AbstractActor get(String id) {
        return actors.stream().filter(actors -> actors.id.equals(id)).findFirst().orElse(null);
    }

    public void load() {
        Json json1 = new Json();
        actorDTOS = json1.fromJson(actorDTOS.getClass(), Gdx.files.internal("data/actors/actors.json"));

        actorDTOS.forEach(dto -> {
            AbstractActor a = new AbstractActor();

            if(dto.armor != null)
                a.armor = (Armor) ItemStore.I.get( dto.armor);

            if(dto.leftHand != null) {
                a.leftHand = (Equipment) ItemStore.I.get( dto.leftHand);
            }

            if(dto.rightHand != null) {
                a.rightHand = (Equipment) ItemStore.I.get( dto.rightHand);
            }


            dto.attributes.entrySet().forEach(entry -> {
                a.attributes.put(Attributes.valueOf(entry.getKey()), entry.getValue());
            });

            dto.skills.entrySet().forEach(entry -> {
                a.skills.put(Skills.valueOf(entry.getKey()), entry.getValue());
            });

            dto.bodyPartColours.entrySet().forEach(entry -> {
                a.setBodyPartColour(entry.getKey(), entry.getValue());
            });

            a.id = dto.id;
            a.kit = Kit.valueOf(dto.kit);
            a.race = Race.valueOf(dto.race);
            a.gender = Gender.valueOf(dto.gender);
            a.name = dto.name;
            if(dto.x != null)
                a.x = dto.x;
            if(dto.y != null)
                a.y = dto.y;

            actors.add(a);
        });
        actors.forEach(actor -> {
            actor.loadAnimation();
        });

    }

}
