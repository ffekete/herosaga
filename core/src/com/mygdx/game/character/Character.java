package com.mygdx.game.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Character extends Image {

    CharacterAnimation characterAnimation;

    public Vector2 position;

    public State state = State.Idle;

    public Direction direction = Direction.Right;

    public Vector2 velocity = new Vector2();
    public Vector2 acceleration = new Vector2();

    public Rectangle bounds = new Rectangle();

    public Character(float x,
                     float y,
                     float width,
                     float height,
                     String fileName) {

        this.characterAnimation = new CharacterAnimation(fileName);
        this.position = new Vector2(x, y);
        this.setSize(width, height);
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
        setX(position.x);
        setY(position.y);
        bounds.x = position.x + 6;
        bounds.y = position.y;
        bounds.width = 4;
        bounds.height = 12;
    }

    @Override
    public void draw(Batch batch,
                     float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(characterAnimation.getFrame(state, direction), position.x, position.y, getWidth(), getHeight());
    }
}
