package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

public class MapSegmentComponent implements Component, Pool.Poolable {



    public static MapSegmentComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(MapSegmentComponent.class);
        } else {
            return new MapSegmentComponent();
        }
    }


    @Override
    public void reset() {

    }
}
