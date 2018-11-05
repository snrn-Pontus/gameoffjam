package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

public class TimedComponent implements Component, Pool.Poolable {

    private float lifespan = 3;

    public static TimedComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(TimedComponent.class);
        } else {
            return new TimedComponent();
        }
    }

    public float getLifespan() {
        return lifespan;
    }

    public TimedComponent setLifespan(float lifespan) {
        this.lifespan = lifespan;
        return this;
    }

    @Override
    public void reset() {
        lifespan = 3;
    }
}
