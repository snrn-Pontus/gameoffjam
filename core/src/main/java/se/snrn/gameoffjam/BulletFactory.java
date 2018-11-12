package se.snrn.gameoffjam;

import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.*;
import se.snrn.gameoffjam.components.TimedComponent;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.GameOffJam.PPM;
import static se.snrn.gameoffjam.GameOffJam.SPEED;

public class BulletFactory {
    public static Entity create(Engine engine, TransformComponent transformComponent) {
        float x = transformComponent.position.x;
        float y = transformComponent.position.y;

        Vector2 bulletVelocity = new Vector2(SPEED * 2, 0);
        Vector2 bulletSpawnPoint = new Vector2(48, 0);

        bulletVelocity.rotate(transformComponent.rotation);
        bulletSpawnPoint.rotate(transformComponent.rotation);


        Entity bullet = engine.createEntity();
        bullet
                .add(VelocityComponent.create(engine).setSpeed(bulletVelocity.x, bulletVelocity.y))
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(new Texture(Gdx.files.internal("test.png")))))
                .add(BoundsComponent.create(engine).setBounds(x, y, PPM, 8))
                .add(TimedComponent.create(engine).setLifespan(3.5f))
                .add(TweenComponent.create(engine)
                        .addTween(
                                Tween.to(bullet, K2EntityTweenAccessor.COLOR, 0.5f)
                                        .target(255, 0, 0)
                                        .repeatYoyo(-1, 0))
                )
                .add(TypeComponent.create(engine).setType(Type.BULLET))
                .add(TransformComponent.create(engine)
                        .setPosition(
                                transformComponent.position.x + bulletSpawnPoint.x,
                                transformComponent.position.y + bulletSpawnPoint.y
                        )
                        .setScale(PPM/2f, 4)
                        .setRotation(transformComponent.rotation));
        return bullet;
    }
}
