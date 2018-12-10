package se.snrn.gameoffjam;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.TweenComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import se.snrn.gameoffjam.components.AnimationStateComponent;
import se.snrn.gameoffjam.components.CleanUpComponent;
import se.snrn.gameoffjam.components.SkeletonComponent;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.ScreenManager.BACKGROUND_SPEED;
import static se.snrn.gameoffjam.ScreenManager.PIXELS_PER_METER;


public class EnemyFactory {

    public static Entity create(final Engine engine, float x, float y) {
        final Entity enemy = engine.createEntity();

        final TransformComponent transformComponent = TransformComponent.create(engine).setPosition(x, y).setScale(PIXELS_PER_METER, PIXELS_PER_METER);
        enemy
                .add(transformComponent)
                .add(TypeComponent.create(engine).setType(Type.ENEMY))
                .add(CleanUpComponent.create(engine))
                .add(VelocityComponent.create(engine).setSpeed(-BACKGROUND_SPEED, 0))
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
        state.setAnimation(0, "shoot", true);

        state.addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                super.event(entry, event);
                engine.addEntity(BulletFactory.createEnemyBullet(engine, transformComponent));
            }
        });

        enemy.add(AnimationStateComponent.create(engine).setState(state));


        return enemy;
    }
}
