package com.mygdx.game.character;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Character extends Actor {

    public float x;
    public float y;

    public float runningSpeed;
    public float jumpHeight;

    public State state = State.Idle;
    public State overrideState = null;

    public Class aClass;
    public Direction direction = Direction.Right;

    private float maxRunningSpeed;
    private float maxJumpHeight;

    public Character(float x,
                     float y,
                     Class aClass) {
        this.x = x;
        this.y = y;
        this.aClass = aClass;
        this.maxRunningSpeed = aClass.speed;
        this.maxJumpHeight = aClass.jumpHeight;
    }

    public float getMaxRunningSpeed() {
        return maxRunningSpeed; // add modifiers later
    }

    public float getMaxJumpHeight() {
        return maxJumpHeight;
    }

    public enum State {
        Idle,
        Running,
        Falling,
        Squatting,
        Landing,
        JumpingAnticipation,
        Jumping,
        Climbing
    }

    public enum Class {
        Rogue(0.5f, 2f);

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
}
