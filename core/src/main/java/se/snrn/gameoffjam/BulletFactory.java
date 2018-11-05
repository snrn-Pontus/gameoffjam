package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.kitten2d.ashley.components.*;
import se.snrn.gameoffjam.components.TimedComponent;

import static se.snrn.gameoffjam.GameOffJam.PPM;

public class BulletFactory {
    public static Entity create(Engine engine, float x, float y) {
        Entity bullet = engine.createEntity();
        bullet
                .add(VelocityComponent.create(engine).setSpeed(512, 0))
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(new Texture(Gdx.files.internal("test.png")))))
                .add(BoundsComponent.create(engine).setBounds(0, 0, 64, 8))
                .add(TimedComponent.create(engine).setLifespan(3f))
                .add(TransformComponent.create(engine).setPosition(x, y).setScale(PPM,8));
        return bullet;
    }
}
