package com.mygdx.game.stage.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.mygdx.game.character.Character;
import com.mygdx.game.character.CharacterAnimationRenderer;

public class LandingAction extends TemporalAction {

    private Character character;

    public LandingAction(Character character) {
        super(0.25f);

        this.character = character;

        CharacterAnimationRenderer.I.resetAnimation(this.character);
        this.character.overrideState = Character.State.Landing;
    }

    @Override
    protected void update(float percent) {
        if (percent >= 0.95f) {
            this.character.overrideState = null;
        }
    }
}
