package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {



    public static PlayerComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(PlayerComponent.class);
        } else {
            return new PlayerComponent();
        }
    }


    @Override
    public void reset() {

    }
}
