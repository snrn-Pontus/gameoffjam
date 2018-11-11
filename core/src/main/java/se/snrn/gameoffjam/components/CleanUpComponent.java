package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

public class CleanUpComponent implements Component, Pool.Poolable {



    public static CleanUpComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(CleanUpComponent.class);
        } else {
            return new CleanUpComponent();
        }
    }


    @Override
    public void reset() {

    }
}
