package com.mygdx.game.character;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Character extends Actor {

    public float x;
    public float y;

    public float runningSpeed;

    public State state = State.Idle;
    public State overrideState = null;

    public Class aClass;
    public Direction direction = Direction.Right;

    private float speed;
    private float jumpHeight;

    public Character(float x,
                     float y,
                     Class aClass) {
        this.x = x;
        this.y = y;
        this.aClass = aClass;
        this.speed = aClass.speed;
        this.jumpHeight = aClass.jumpHeight;
    }

    public float getSpeed() {
        return speed; // add modifiers later
    }

    public float getJumpHeight() {
        return jumpHeight;
    }

    public enum State {
        Idle,
        Running,
        Falling,
        Squatting,
        Landing,
        JumpingAnticipation,
        Jumping
    }

    public enum Class {
        Rogue(0.5f, 10f);

        public float speed;
        public float jumpHeight;

        Class(float speed,
              float jumpHeight) {
            this.speed = speed;
            this.jumpHeight = jumpHeight;
        }
    }

    public enum Direction {
        Left,
        Right
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        System.out.println(getActions().size);
    }
}
