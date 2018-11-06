package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;
import se.snrn.gameoffjam.Type;

import static se.snrn.gameoffjam.Type.NONE;

public class TypeComponent implements Component, Pool.Poolable {

    private Type type;

    public static TypeComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(TypeComponent.class);
        } else {
            return new TypeComponent();
        }
    }

    public Type getType() {
        return type;
    }

    public TypeComponent setType(Type type) {
        this.type = type;
        return this;
    }

    @Override
    public void reset() {
        type = NONE;
    }
}
