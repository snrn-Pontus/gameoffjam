package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

public class CameraComponent implements Component, Pool.Poolable {



    public static CameraComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(CameraComponent.class);
        } else {
            return new CameraComponent();
        }
    }


    @Override
    public void reset() {

    }
}
