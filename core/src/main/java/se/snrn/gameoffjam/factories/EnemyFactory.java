package se.snrn.gameoffjam.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.roaringcatgames.kitten2d.ashley.components.*;
import se.snrn.gameoffjam.GameScreen;
import se.snrn.gameoffjam.Type;
import se.snrn.gameoffjam.components.AnimationStateComponent;
import se.snrn.gameoffjam.components.CleanUpComponent;
import se.snrn.gameoffjam.components.SkeletonComponent;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.ScreenManager.BACKGROUND_SPEED;
import static se.snrn.gameoffjam.ScreenManager.PPM;
import static se.snrn.gameoffjam.map.Map.*;


public class EnemyFactory {

    public static Entity create(final Engine engine, float x, float y, int robotType) {
        final Entity enemy = engine.createEntity();

        final TransformComponent transformComponent = TransformComponent.create(engine)
                .setPosition(x, y)
                .setScale(PPM, PPM);

        enemy
                .add(transformComponent)
                .add(TypeComponent.create(engine).setType(Type.ENEMY))
                .add(CleanUpComponent.create(engine));


        switch (robotType) {
            case ROBOT_ENEMY: {
                addSpine(engine, x, y, enemy, transformComponent, "spine/robot_enemy/robot_enemy", "shoot");
                break;
            }
            case ROCKET_ENEMY: {
                addSpine(engine, x, y, enemy, transformComponent, "spine/rocket_enemy/rocket_enemy", "animation");
                break;
            }
            case LASER_ENEMY: {
                addSpine(engine, x, y, enemy, transformComponent, "spine/laser_enemy/laser_enemy", "animation");
                break;
            }
        }

        addBehaviour(engine, enemy, robotType);


        engine.addEntity(enemy);
        return enemy;
    }

    private static void addBehaviour(final Engine engine, Entity enemy, int robotType) {
        TweenComponent tweenComponent = TweenComponent.create(engine);
//        tweenComponent.addTween(Tween.to(enemy, K2EntityTweenAccessor.POSITION_Y, 3f)
//                .ease(TweenEquations.easeInExpo)
//                .repeatYoyo(-1, 0)
//                .target(50));
        if (robotType == ROCKET_ENEMY) {
            enemy.add(FollowerComponent.create(engine)
                    .setTarget(GameScreen.player)
                    .setFollowSpeed(BACKGROUND_SPEED)
                    .setMode(FollowMode.MOVETO)
                    .setMatchParentRotation(false));
        } else {
            enemy.add(VelocityComponent.create(engine).setSpeed(-BACKGROUND_SPEED, 0));
        }


        // Laser laser = new Laser();
        // laser.degrees = 90;
        // enemy.add(LaserComponent.create(engine).setLaser(laser));
    }

    private static void addSpine(final Engine engine, float x, float y, Entity enemy, final TransformComponent transformComponent, String path, String animation) {
        final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(path + ".atlas"));


        SkeletonJson json = new SkeletonJson(atlas);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(path + ".json"));


        Skeleton skeleton = new Skeleton(skeletonData);


        skeleton.setPosition(x, y);
        skeleton.updateWorldTransform();

        float scale = 0.3f;
        skeleton.getRootBone().setScale(scale, scale);


        //skeleton.findBone("root").setScale(scale, scale);


        enemy.add(SkeletonComponent.create(engine).setSkeleton(skeleton));

        AnimationStateData stateData = new AnimationStateData(skeletonData);

        AnimationState state = new AnimationState(stateData);
        state.setAnimation(0, animation, true);

        state.addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                super.event(entry, event);
                engine.addEntity(BulletFactory.createEnemyBullet(engine, transformComponent));
            }
        });


        enemy
                .add(BoundsComponent.create(engine)
                        .setBounds(-32, -32, skeletonData.getWidth() * scale, skeletonData.getHeight() * scale))
                .add(AnimationStateComponent.create(engine).setState(state));
    }
}
