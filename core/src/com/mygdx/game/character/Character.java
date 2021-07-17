package com.mygdx.game.character;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Character extends Actor {

    public Vector2 position;

    public State state = State.Idle;

    public Direction direction = Direction.Right;

    public Vector2 velocity = new Vector2();
    public Vector2  acceleration = new Vector2();
    Rectangle bounds = new Rectangle();

    public Character(float x,
                     float y) {

        this.position = new Vector2(x, y);
    }

    public enum State {
        Idle,
        Running,
        Jumping
    }

    public enum Direction {
        Left,
        Right
    }

    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta);
    }

}
