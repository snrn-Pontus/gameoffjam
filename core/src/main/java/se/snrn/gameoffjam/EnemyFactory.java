package se.snrn.gameoffjam;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.spine.*;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.*;
import se.snrn.gameoffjam.components.AnimationStateComponent;
import se.snrn.gameoffjam.components.CleanUpComponent;
import se.snrn.gameoffjam.components.SkeletonComponent;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.GameOffJam.BACKGROUND_SPEED;
import static se.snrn.gameoffjam.GameOffJam.PIXELS_PER_METER;

public class EnemyFactory {

    public static Entity create(Engine engine, float x, float y) {
        Entity enemy = engine.createEntity();

        enemy
                .add(TransformComponent.create(engine).setPosition(x, y).setScale(PIXELS_PER_METER, PIXELS_PER_METER))
                .add(TypeComponent.create(engine).setType(Type.ENEMY))
                .add(CleanUpComponent.create(engine))
                .add(VelocityComponent.create(engine).setSpeed(-BACKGROUND_SPEED, 0))
                .add(ParticleEmitterComponent.create(engine)
                        .setParticleImage(new TextureRegion(new Texture(Gdx.files.internal("test.png"))))
                        .setDuration(10f)
                        .setParticleMinMaxScale(0.75f, 1.25f)
                        .setShouldLoop(true)
                        .setParticleLifespans(0.1f, 0.5f)
                        .setAngleRange(170, 190)
                        .setSpawnRate(30)
                        .setSpeed(30, 100)
                        .setShouldFade(true))
                .add(TweenComponent.create(engine)
                        .addTween(Tween.to(enemy, K2EntityTweenAccessor.POSITION_Y, 3f)
                                .ease(TweenEquations.easeInExpo)
                                .repeatYoyo(-1, 0)
                                .target(50)))
                .add(BoundsComponent.create(engine).setOffset(-8, 32).setBounds(-32, -32, 102, 120));


        final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("spine/enemy.atlas"));


        SkeletonJson json = new SkeletonJson(atlas);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("spine/enemy.json"));


        Skeleton skeleton = new Skeleton(skeletonData);


        skeleton.setPosition(x, y);
        skeleton.updateWorldTransform();

        skeleton.getRootBone().setScale(0.3f, 0.3f);

        skeleton.findBone("body").setScale(0.3f, 0.3f);

        enemy.add(SkeletonComponent.create(engine).setSkeleton(skeleton));

        AnimationStateData stateData = new AnimationStateData(skeletonData);

        AnimationState state = new AnimationState(stateData);
        state.setAnimation(0, "animation", true);

        enemy.add(AnimationStateComponent.create(engine).setState(state));


        return enemy;
    }
}
