package com.mygdx.game.map.tiles;

import java.util.Arrays;

public enum Adjacency {

    NONE(0),
    LEFT(1),
    UP(2),
    LEFT_UP(3),
    RIGHT(4),
    LEFT_RIGHT(5),
    UP_RIGHT(6),
    LEFT_UP_RIGHT(7),
    DOWN(8),
    LEFT_DOWN(9),
    UP_DOWN(10),
    LEFT_UP_DOWN(11),
    RIGHT_DOWN(12),
    LEFT_RIGHT_DOWN(13),
    UP_RIGHT_DOWN(14),
    ALL(15);


    private int number;

    Adjacency(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public static Adjacency fromNumber(int n) {
        return Arrays.stream(Adjacency.values())
                .filter(v -> v.number == n)
                .findFirst()
                .get();
    }
}