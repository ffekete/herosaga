package com.mygdx.game.character;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.controls.JumpState;
import com.mygdx.game.physics.Physics;

public class Character extends Actor {

    public float x;
    public float y;
    public Physics physics = new Physics();
    public JumpState jumpState = JumpState.NoJump;

    public float runningSpeed;
    public float jumpHeight;

    public State state = State.Idle;

    public Class aClass;
    public Direction direction = Direction.Right;

    public Character(float x,
                     float y,
                     Class aClass) {
        this.x = x;
        this.y = y;
        this.aClass = aClass;
    }

    public enum State {
        Idle,
        Running,
        Falling,
        FallingThrough,
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
