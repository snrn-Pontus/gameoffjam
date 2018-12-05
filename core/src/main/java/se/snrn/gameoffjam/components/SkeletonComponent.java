package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;
import com.esotericsoftware.spine.Skeleton;


public class SkeletonComponent implements Component, Pool.Poolable {

    private Skeleton skeleton;

    public static SkeletonComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(SkeletonComponent.class);
        } else {
            return new SkeletonComponent();
        }
    }

    public SkeletonComponent setSkeleton(Skeleton skeleton) {
        this.skeleton = skeleton;
        return this;
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }

    @Override
    public void reset() {
        this.skeleton = null;
    }
}
