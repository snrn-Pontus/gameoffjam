package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import se.snrn.gameoffjam.components.TimedComponent;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.ScreenManager.BULLET_SPEED;
import static se.snrn.gameoffjam.ScreenManager.PPM;

public class BulletFactory {
    public static Entity create(Engine engine, TransformComponent transformComponent) {
        float x = transformComponent.position.x;
        float y = transformComponent.position.y;

        Vector2 bulletVelocity = new Vector2(BULLET_SPEED, 0);
        Vector2 bulletSpawnPoint = new Vector2(60, 70);

        bulletVelocity.rotate(transformComponent.rotation);
        bulletSpawnPoint.rotate(transformComponent.rotation);

        int random = MathUtils.random(0, 4);

        Entity bullet = engine.createEntity();
        Texture texture = new Texture(Gdx.files.internal("images/drop_" + random + ".png"));
        float width = texture.getWidth();
        float height = texture.getHeight();
        bullet
                .add(VelocityComponent.create(engine).setSpeed(bulletVelocity.x, bulletVelocity.y))
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(texture)))
                .add(BoundsComponent.create(engine).setBounds(x, y, width / (PPM / 2f), height / (PPM / 2f)))
                .add(TimedComponent.create(engine).setLifespan(3.5f))
                .add(TypeComponent.create(engine).setType(Type.BULLET))
                .add(TransformComponent.create(engine)
                        .setPosition(
                                transformComponent.position.x + bulletSpawnPoint.x,
                                transformComponent.position.y + bulletSpawnPoint.y
                        )
                        .setScale(2, 2)
                        .setRotation(transformComponent.rotation));
        return bullet;
    }

    public static Entity createFromPoint(Engine engine, Vector2 vector2, float rotation) {
        float x = vector2.x;
        float y = vector2.y;

        Vector2 bulletVelocity = new Vector2(BULLET_SPEED, 0);
        Vector2 bulletSpawnPoint = new Vector2(0, 0);

        bulletVelocity.rotate(rotation);
        bulletSpawnPoint.rotate(rotation);

        int random = MathUtils.random(0, 4);

        Entity bullet = engine.createEntity();
        Texture texture = new Texture(Gdx.files.internal("images/drop_" + random + ".png"));
        float width = texture.getWidth();
        float height = texture.getHeight();
        bullet
                .add(VelocityComponent.create(engine).setSpeed(bulletVelocity.x, bulletVelocity.y))
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(texture)))
                .add(BoundsComponent.create(engine).setBounds(x, y, width / (PPM / 2f), height / (PPM / 2f)))
                .add(TimedComponent.create(engine).setLifespan(3.5f))
                .add(TypeComponent.create(engine).setType(Type.BULLET))
                .add(TransformComponent.create(engine)
                        .setPosition(
                                vector2.x + bulletSpawnPoint.x,
                                vector2.y + bulletSpawnPoint.y
                        )
                        .setScale(2, 2)
                        .setRotation(rotation));
        return bullet;
    }

    public static Entity createEnemyBullet(Engine engine, TransformComponent transformComponent) {
        float x = transformComponent.position.x;
        float y = transformComponent.position.y;

        Vector2 bulletVelocity = new Vector2(-BULLET_SPEED, 0);
        Vector2 bulletSpawnPoint = new Vector2(0, 35);

        bulletVelocity.rotate(transformComponent.rotation);
        bulletSpawnPoint.rotate(transformComponent.rotation);

        int random = MathUtils.random(0, 4);

        Entity bullet = engine.createEntity();
        Texture texture = new Texture(Gdx.files.internal("images/dart.png"));
        float width = texture.getWidth();
        float height = texture.getHeight();
        bullet
                .add(VelocityComponent.create(engine).setSpeed(bulletVelocity.x, bulletVelocity.y))
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(texture)))
                .add(BoundsComponent.create(engine).setBounds(x, y, width / (PPM / 2f), height / (PPM / 2f)))
                .add(TimedComponent.create(engine).setLifespan(3.5f))
                .add(TypeComponent.create(engine).setType(Type.ENEMY_BULLET))
                .add(TransformComponent.create(engine)
                        .setPosition(
                                transformComponent.position.x + bulletSpawnPoint.x,
                                transformComponent.position.y + bulletSpawnPoint.y
                        )
                        .setScale(4, 4)
                        .setRotation(transformComponent.rotation));
        return bullet;
    }
}
