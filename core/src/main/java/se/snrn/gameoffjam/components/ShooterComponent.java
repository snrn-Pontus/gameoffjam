package se.snrn.gameoffjam.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.attachments.PointAttachment;


public class ShooterComponent implements Component, Pool.Poolable {

    private PointAttachment attachment;
    private Bone bone;

    public static ShooterComponent create(Engine engine) {
        if (engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(ShooterComponent.class);
        } else {
            return new ShooterComponent();
        }
    }

    public PointAttachment getAttachment() {
        return attachment;
    }

    public ShooterComponent setAttachment(PointAttachment attachment) {
        this.attachment = attachment;
        return this;
    }

    @Override
    public void reset() {
        this.attachment = null;
        this.bone = null;
    }

    public Bone getBone() {
        return bone;
    }

    public ShooterComponent setBone(Bone bone) {
        this.bone = bone;
        return this;
    }
}
