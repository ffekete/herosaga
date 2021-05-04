package com.mygdx.game.character;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Character extends Actor {

    public float x;
    public float y;

    public State state = State.Idle;
    public State overrideState = null;

    public Class aClass;
    public Direction direction = Direction.Right;

    private float speed;

    public Character(float x,
                     float y,
                     Class aClass) {
        this.x = x;
        this.y = y;
        this.aClass = aClass;
        this.speed = aClass.speed;
    }

    public float getSpeed() {
        return speed; // add modifiers later
    }

    public enum State {
        Idle,
        Running,
        Falling,
        Squatting,
        Landing
    }

    public enum Class {
        Rogue(0.5f);

        public float speed;

        Class(float speed) {
            this.speed = speed;
        }
    }

    public enum Direction {
        Left,
        Right
    }
}
