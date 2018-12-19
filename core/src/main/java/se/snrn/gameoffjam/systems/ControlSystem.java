package se.snrn.gameoffjam.systems;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.TweenComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import se.snrn.gameoffjam.factories.BulletFactory;
import se.snrn.gameoffjam.components.ControlComponent;
import se.snrn.gameoffjam.components.ShooterComponent;

import static se.snrn.gameoffjam.ScreenManager.*;

public class ControlSystem extends IteratingSystem {

    private ComponentMapper<ControlComponent> cm;
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<VelocityComponent> vm;

    public ControlSystem() {
        super(Family.all(ControlComponent.class, TransformComponent.class).get());
        cm = ComponentMapper.getFor(ControlComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        vm = ComponentMapper.getFor(VelocityComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        ControlComponent controlComponent = cm.get(entity);
        TransformComponent transformComponent = tm.get(entity);
        VelocityComponent velocityComponent = vm.get(entity);




        if (controlComponent.isLeft()) {
            if (transformComponent.position.x > 32) {
                velocityComponent.setSpeed(-BACKGROUND_SPEED, 0);
            } else {
                velocityComponent.setSpeed(SPEED, 0);
            }
        }

        if (controlComponent.isRight()) {
            if (transformComponent.position.x < WIDTH - 192) {
                velocityComponent.setSpeed(BACKGROUND_SPEED, 0);
            } else {
                velocityComponent.setSpeed(SPEED, 0);
            }

        }

        if (!controlComponent.isLeft() && !controlComponent.isRight()) {
            velocityComponent.setSpeed(SPEED, velocityComponent.speed.y);
        }

        if (controlComponent.isUp()) {
            if(transformComponent.position.y < HEIGHT -90) {
                velocityComponent.setSpeed(velocityComponent.speed.x, BACKGROUND_SPEED);
            } else {
                velocityComponent.setSpeed(velocityComponent.speed.x, SPEED);
            }
            Tween tweenPos = Tween.to(entity, K2EntityTweenAccessor.ROTATION, 0.5f)
                        .ease(TweenEquations.easeOutCubic)
                        .target(MAX_TILT);
            entity.add(TweenComponent.create(getEngine())
                        .addTween(tweenPos));
        }
        if (controlComponent.isDown()) {
            if(transformComponent.position.y > 80) {
                velocityComponent.setSpeed(velocityComponent.speed.x, -BACKGROUND_SPEED);
            } else {
                velocityComponent.setSpeed(velocityComponent.speed.x, SPEED);
            }
            Tween tweenPos = Tween.to(entity, K2EntityTweenAccessor.ROTATION, 0.5f)
                    .ease(TweenEquations.easeOutCubic)
                    .target(-MAX_TILT);
            entity.add(TweenComponent.create(getEngine())
                    .addTween(tweenPos));
        }

        if (!controlComponent.isDown() && !controlComponent.isUp()) {
            velocityComponent.setSpeed(velocityComponent.speed.x, 0);
            Tween tweenPos = Tween.to(entity, K2EntityTweenAccessor.ROTATION, 0.5f)
                    .ease(TweenEquations.easeOutCubic)
                    .target(0);
            entity.add(TweenComponent.create(getEngine())
                    .addTween(tweenPos));
        }




        if (controlComponent.isAttack()) {
            ShooterComponent shooterComponent = entity.getComponent(ShooterComponent.class);

            if (shooterComponent != null) {
                Vector2 point = new Vector2();
                shooterComponent.getAttachment().computeWorldPosition(shooterComponent.getBone(), point);
                float rotation = shooterComponent.getAttachment().computeWorldRotation(shooterComponent.getBone());
                getEngine().addEntity(BulletFactory.createFromPoint(getEngine(), point,rotation));

            }
            //getEngine().addEntity(BulletFactory.create(getEngine(), transformComponent));
            controlComponent.setAttack(false);
        }
    }
}
