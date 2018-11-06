package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

public class DeathComponent implements Component, Pool.Poolable {



    public static DeathComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(DeathComponent.class);
        } else {
            return new DeathComponent();
        }
    }


    @Override
    public void reset() {

    }
}
