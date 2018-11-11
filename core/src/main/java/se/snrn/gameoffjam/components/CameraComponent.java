package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;
import net.dermetfan.gdx.math.MathUtils;

import static se.snrn.gameoffjam.GameOffJam.WIDTH;

public class CameraComponent implements Component, Pool.Poolable {


    private float offset;

    public static CameraComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(CameraComponent.class);
        } else {
            return new CameraComponent();
        }
    }


    @Override
    public void reset() {
            offset = -128;
    }

    public float getOffset() {
        return offset;
    }

    public CameraComponent setOffset(float offset) {

        this.offset = MathUtils.clamp(offset,-WIDTH,-48);
        return this;
    }
}
