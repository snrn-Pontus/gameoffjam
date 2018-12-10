package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.components.*;
import se.snrn.gameoffjam.components.TimedComponent;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.ScreenManager.BULLET_SPEED;
import static se.snrn.gameoffjam.ScreenManager.PPM;


public class WreckFactory {
    public static void create(Engine engine, TransformComponent transformComponent) {
        float x = transformComponent.position.x;
        float y = transformComponent.position.y;

        Vector2 bulletVelocity = new Vector2(BULLET_SPEED / 2f, 0);


        for (int i = 0; i <= 9; i++) {
            float random = MathUtils.random(0, 180);
            bulletVelocity.setAngle(random);
            Entity bullet = engine.createEntity();
            Texture texture = new Texture(Gdx.files.internal("images/robot/part_" + i + ".png"));
            float width = texture.getWidth();
            float height = texture.getHeight();
            bullet
                    .add(VelocityComponent.create(engine).setSpeed(bulletVelocity.x, bulletVelocity.y))
                    .add(TextureComponent.create(engine).setRegion(new TextureRegion(texture)))
                    .add(BoundsComponent.create(engine).setBounds(x, y, width / (PPM / 2f), height / (PPM / 2f)))
                    .add(TimedComponent.create(engine).setLifespan(3.5f))
                    .add(ParticleEmitterComponent.create(engine)
                            .setParticleImage(new TextureRegion(new Texture(Gdx.files.internal("images/wreck.png"))))
                            .setDuration(0.2f)
                            .setParticleMinMaxScale(PPM, PPM)
                            .setShouldLoop(true)
                            .setParticleLifespans(0.1f, 0.5f)
                            .setAngleRange(170, 190)
                            .setSpawnRate(10)
                            .setZIndex(-1)
                            .setSpeed(30, 100)
                            .setShouldFade(true))
                    .add(TypeComponent.create(engine).setType(Type.WRECK))
                    .add(RotationComponent.create(engine).setRotationSpeed(150).setTargetRotation(360))
                    .add(TransformComponent.create(engine)
                            .setPosition(
                                    transformComponent.position.x,
                                    transformComponent.position.y
                            )
                            .setScale(8, 8)
                            .setRotation(transformComponent.rotation));

            engine.addEntity(bullet);
        }


    }
}
