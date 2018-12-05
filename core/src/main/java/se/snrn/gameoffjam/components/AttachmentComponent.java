package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Skeleton;


public class AttachmentComponent implements Component, Pool.Poolable {

    private AnimationState state;
    private Skeleton skeleton;

    public static AttachmentComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(AttachmentComponent.class);
        } else {
            return new AttachmentComponent();
        }
    }

    public AttachmentComponent setState(AnimationState state) {
        this.state = state;
        return this;
    }

    public AnimationState getState() {
        return state;
    }

    public AttachmentComponent setSkeleton(Skeleton skeleton) {
        this.skeleton = skeleton;
        return this;
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }

    @Override
    public void reset() {
        this.state = null;
        this.skeleton = null;
    }
}
