package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;
import com.esotericsoftware.spine.AnimationState;


public class AnimationStateComponent implements Component, Pool.Poolable {

    private AnimationState state;

    public static AnimationStateComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(AnimationStateComponent.class);
        } else {
            return new AnimationStateComponent();
        }
    }

    public AnimationStateComponent setState(AnimationState state) {
        this.state = state;
        return this;
    }

    public AnimationState getState() {
        return state;
    }

    @Override
    public void reset() {
        this.state = null;
    }
}
