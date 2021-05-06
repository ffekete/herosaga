package com.mygdx.game.stage.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.mygdx.game.character.Character;

public class JumpingAnticipationAction extends TemporalAction {

    private Character character;

    public JumpingAnticipationAction(float duration,
                                     Character character) {
        super(duration);
        this.character = character;
    }

    @Override
    protected void update(float percent) {
        if (percent >= 0.95f) {
            this.character.overrideState = Character.State.Jumping;
        }
    }
}
