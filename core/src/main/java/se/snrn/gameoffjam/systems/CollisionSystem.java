package se.snrn.gameoffjam.systems;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.ParticleEmitterComponent;
import com.roaringcatgames.kitten2d.ashley.components.ParticleSpawnType;
import com.roaringcatgames.kitten2d.ashley.components.TweenComponent;
import com.strongjoshua.console.LogLevel;
import se.snrn.gameoffjam.Type;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.GameOffJam.console;

public class CollisionSystem extends IteratingSystem {

    private ComponentMapper<BoundsComponent> bm;
    private ComponentMapper<TypeComponent> tm;

    public CollisionSystem() {
        super(Family.all(BoundsComponent.class, TypeComponent.class).get());
        bm = ComponentMapper.getFor(BoundsComponent.class);
        tm = ComponentMapper.getFor(TypeComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        BoundsComponent boundsComponent = bm.get(entity);
        TypeComponent typeComponent = tm.get(entity);

        for (final Entity otherEntity : getEntities()) {
            BoundsComponent otherBounds = bm.get(otherEntity);

            if (otherBounds != null &&
                    boundsComponent != null &&
                    boundsComponent != otherBounds &&
                    boundsComponent.bounds.overlaps(otherBounds.bounds)) {
                Type entityType = typeComponent.getType();
                Type otherType = tm.get(otherEntity).getType();

                switch (entityType) {
                    case PLAYER:
                        break;
                    case BULLET:
                        switch (otherType) {
                            case ENEMY:
                                getEngine().removeEntity(entity);
                                otherEntity
                                        .add(
                                                ParticleEmitterComponent.create(getEngine())
                                                        .setParticleImage(new TextureRegion(new Texture(Gdx.files.internal("test.png"))))
                                                        .setDuration(0.2f)
                                                        .setParticleLifespans(0.1f, 0.5f)
                                                        .setAngleRange(0, 360)
                                                        .setSpawnRate(10)
                                                        .setSpeed(300, 300)
                                                        .setShouldFade(true)
                                        )
                                        .add(TweenComponent.create(getEngine())
                                                .addTween(Tween.to(otherEntity, K2EntityTweenAccessor.ROTATION, 0.5f)
                                                        .target(720)
                                                )
                                                .addTween(Tween.to(otherEntity, K2EntityTweenAccessor.SCALE, 0.5f)
                                                        .target(0.01f).setCallback(new TweenCallback() {
                                                            @Override
                                                            public void onEvent(int type, BaseTween<?> source) {
                                                                if (type == COMPLETE) {
                                                                    getEngine().removeEntity(otherEntity);
                                                                }

                                                            }
                                                        }))
                                        );
                                otherEntity.remove(BoundsComponent.class);
                                console.log(entityType + " hit " + otherType, LogLevel.DEFAULT);
                                break;
                        }
                        break;
                    case ENEMY:
                        switch (otherType) {
                            case PLAYER:
                            case BULLET:
                                getEngine().removeEntity(otherEntity);
                                //getEngine().removeEntity(entity);
                                console.log(entityType + " hit " + otherType, LogLevel.DEFAULT);
                                break;
                        }
                        break;
                }
                break;
            }
        }
    }
}
