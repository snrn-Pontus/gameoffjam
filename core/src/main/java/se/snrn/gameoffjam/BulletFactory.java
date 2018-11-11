package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.kitten2d.ashley.components.*;
import se.snrn.gameoffjam.components.TimedComponent;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.GameOffJam.PPM;

public class BulletFactory {
    public static Entity create(Engine engine, float x, float y) {



        Entity bullet = engine.createEntity();
        bullet
                .add(VelocityComponent.create(engine).setSpeed(512, 0))
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(new Texture(Gdx.files.internal("test.png")))))
                .add(BoundsComponent.create(engine).setBounds(x+48, y, 32, 8))
                .add(TimedComponent.create(engine).setLifespan(3.5f))
                .add(TypeComponent.create(engine).setType(Type.BULLET))
                .add(TransformComponent.create(engine).setPosition(x+48, y).setScale(PPM,8));
        return bullet;
    }
}
