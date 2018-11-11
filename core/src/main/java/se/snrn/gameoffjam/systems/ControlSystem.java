package se.snrn.gameoffjam.systems;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.TweenComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import se.snrn.gameoffjam.BulletFactory;
import se.snrn.gameoffjam.components.CameraComponent;
import se.snrn.gameoffjam.components.ControlComponent;

import static se.snrn.gameoffjam.GameOffJam.*;

public class ControlSystem extends IteratingSystem {

    private final ComponentMapper<CameraComponent> camm;
    private ComponentMapper<ControlComponent> cm;
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<VelocityComponent> vm;

    public ControlSystem() {
        super(Family.all(ControlComponent.class, TransformComponent.class).get());
        cm = ComponentMapper.getFor(ControlComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        vm = ComponentMapper.getFor(VelocityComponent.class);
        camm = ComponentMapper.getFor(CameraComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        ControlComponent controlComponent = cm.get(entity);
        TransformComponent transformComponent = tm.get(entity);
        VelocityComponent velocityComponent = vm.get(entity);
        CameraComponent cameraComponent = camm.get(entity);


        if (controlComponent.isLeft()) {
            cameraComponent.setOffset(cameraComponent.getOffset() + 5);
            if (cameraComponent.getOffset() == -48) {
                velocityComponent.setSpeed(SPEED, 0);
            } else {
                velocityComponent.setSpeed(0, 0);
            }
        }

        if (controlComponent.isRight()) {
            cameraComponent.setOffset(cameraComponent.getOffset() - 5);
            if (cameraComponent.getOffset() == -WIDTH) {
                velocityComponent.setSpeed(SPEED, 0);

            } else {
                velocityComponent.setSpeed(SPEED * 2, 0);
            }
        }

        if (!controlComponent.isLeft() && !controlComponent.isRight()) {
            velocityComponent.setSpeed(SPEED, velocityComponent.speed.y);
        }

        if (controlComponent.isUp()) {
            velocityComponent.setSpeed(velocityComponent.speed.x, SPEED);
        }
        if (controlComponent.isDown()) {
            velocityComponent.setSpeed(velocityComponent.speed.x, -SPEED);
        }

        if (!controlComponent.isDown() && !controlComponent.isUp()) {
            velocityComponent.setSpeed(velocityComponent.speed.x, 0);
        }
        if (controlComponent.isJump() && transformComponent.position.y <= -HEIGHT / 2f) {
            Tween tweenPos = Tween.to(entity, K2EntityTweenAccessor.POSITION_Y, 0.5f)
                    .ease(TweenEquations.easeOutCubic)
                    .targetRelative(150)
                    .repeatYoyo(1, 0);
            entity.add(TweenComponent.create(getEngine())
                    .addTween(tweenPos));
            controlComponent.setJump(false);

        }

        if (controlComponent.isAttack()) {
            getEngine().addEntity(BulletFactory.create(getEngine(), transformComponent.position.x, transformComponent.position.y));
            controlComponent.setAttack(false);
        }
    }
}
