package se.snrn.gameoffjam.systems;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.strongjoshua.console.LogLevel;
import se.snrn.gameoffjam.CollectibleFactory;
import se.snrn.gameoffjam.Type;
import se.snrn.gameoffjam.components.PlayerComponent;
import se.snrn.gameoffjam.components.TimedComponent;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.GameOffJam.PPM;
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
    protected void processEntity(final Entity entity, float deltaTime) {

        BoundsComponent boundsComponent = bm.get(entity);
        TypeComponent typeComponent = tm.get(entity);

        for (final Entity otherEntity : getEntities()) {
            final BoundsComponent otherBounds = bm.get(otherEntity);

            if (otherBounds != null &&
                    boundsComponent != null &&
                    boundsComponent != otherBounds &&
                    boundsComponent.bounds.overlaps(otherBounds.bounds)) {
                Type entityType = typeComponent.getType();
                Type otherType = tm.get(otherEntity).getType();

                switch (entityType) {
                    case PLAYER:
                        switch (otherType) {
                            case COLLECTIBLE:
                                System.out.println("hit");
                                otherEntity
                                        .add(
                                                FollowerComponent.create(getEngine())
                                                        .setTarget(entity)
                                                        .setMode(FollowMode.MOVETOSTICKY)
                                                        .setFollowSpeed(250)
                                        )
                                        .add(TweenComponent.create(getEngine())
                                                .addTween(Tween.to(otherEntity, K2EntityTweenAccessor.SCALE, 0.25f)
                                                        .target(8,8)
                                                        .repeatYoyo(1, 0)
                                                        .ease(TweenEquations.easeInOutCirc)
                                                        .setCallback(new TweenCallback() {
                                                            @Override
                                                            public void onEvent(int type, BaseTween<?> source) {
                                                                if (type == COMPLETE) {
                                                                    entity.getComponent(PlayerComponent.class).addScore(100);
                                                                }
                                                            }
                                                        })))
                                        .add(TimedComponent.create(getEngine()).setLifespan(2));
                                otherEntity.remove(BoundsComponent.class);
                                break;
                        }
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
                                                                    getEngine().addEntity(CollectibleFactory.create(getEngine(), otherEntity.getComponent(TransformComponent.class).position.x, otherEntity.getComponent(TransformComponent.class).position.y));
                                                                    getEngine().removeEntity(otherEntity);
                                                                }

                                                            }
                                                        }))
                                        );
                                otherEntity.remove(BoundsComponent.class);
                                getEngine().removeEntity(entity);
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
