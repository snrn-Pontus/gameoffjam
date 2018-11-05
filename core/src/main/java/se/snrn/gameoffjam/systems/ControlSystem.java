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
import se.snrn.gameoffjam.components.ControlComponent;

import static se.snrn.gameoffjam.GameOffJam.HEIGHT;

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


        //if (controlComponent.isLeft()) {
        //    entity.add(VelocityComponent.create(getEngine()).setSpeed(-64, 0));
        //transformComponent.setPosition(transformComponent.position.x - 8 * deltaTime, transformComponent.position.y);
        //}
        if (controlComponent.isRight()) {
            entity.add(VelocityComponent.create(getEngine()).setSpeed(256, 0));
            //transformComponent.setPosition(transformComponent.position.x + 8 * deltaTime, transformComponent.position.y);
        }

        if (controlComponent.isJump() && transformComponent.position.y <= -HEIGHT / 2f) {
            //entity.add(VelocityComponent.create(getEngine()).setSpeed(64, 128));


            Tween tweenPos = Tween.to(entity, K2EntityTweenAccessor.POSITION_Y, 0.5f)
                    .ease(TweenEquations.easeInOutSine)
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

        if (velocityComponent != null && controlComponent.isJump() && transformComponent.position.y > (-HEIGHT / 2f) + 100) {
            //entity.add(VelocityComponent.create(getEngine()).setSpeed(64, -128));
            //controlComponent.setJump(false);
        }
        if (velocityComponent != null && !controlComponent.isJump() && transformComponent.position.y <= -HEIGHT / 2f) {
            //entity.add(VelocityComponent.create(getEngine()).setSpeed(64, 0));

        }
    }
}
