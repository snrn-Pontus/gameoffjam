package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;
import se.snrn.gameoffjam.effects.Laser;


public class LaserComponent implements Component, Pool.Poolable {


    private Laser laser;

    public static LaserComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(LaserComponent.class);
        } else {
            return new LaserComponent();
        }
    }


    public Laser getLaser() {
        return laser;
    }

    public LaserComponent setLaser(Laser laser) {
        this.laser = laser;
        return this;
    }

    @Override
    public void reset() {
      laser = null;
    }

}
