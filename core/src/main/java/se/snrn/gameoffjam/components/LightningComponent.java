package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;


public class LightningComponent implements Component, Pool.Poolable {


    private float thinkness = 0.5f;
    private int bolts = 1;
    private int radius = 32;
    private int segments = 32;

    public static LightningComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(LightningComponent.class);
        } else {
            return new LightningComponent();
        }
    }


    public float getThinkness() {
        return thinkness;
    }

    public LightningComponent setThinkness(float thinkness) {
        this.thinkness = thinkness;
        return this;
    }

    public int getBolts() {
        return bolts;
    }

    public LightningComponent setBolts(int bolts) {
        this.bolts = bolts;
        return this;
    }

    public int getRadius() {
        return radius;
    }

    public LightningComponent setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public int getSegments() {
        return segments;
    }

    public LightningComponent setSegments(int segments) {
        this.segments = segments;
        return this;
    }

    @Override
    public void reset() {
        thinkness = 0.5f;
        bolts = 1;
        radius = 32;
        segments = 32;
    }

}
