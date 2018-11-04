package se.snrn.gameoffjam.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import se.snrn.gameoffjam.components.ControlComponent;

public class ControlSystem extends IteratingSystem {

    private ComponentMapper<ControlComponent> cm;
    private ComponentMapper<TransformComponent> tm;

    public ControlSystem() {
        super(Family.all(ControlComponent.class, TransformComponent.class).get());
        cm = ComponentMapper.getFor(ControlComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        ControlComponent controlComponent = cm.get(entity);
        TransformComponent transformComponent = tm.get(entity);


        if (controlComponent.isLeft()) {
            entity.add(VelocityComponent.create(getEngine()).setSpeed(-32,0));
            //transformComponent.setPosition(transformComponent.position.x - 8 * deltaTime, transformComponent.position.y);
        }
        if (controlComponent.isRight()) {
            entity.add(VelocityComponent.create(getEngine()).setSpeed(32,0));
            //transformComponent.setPosition(transformComponent.position.x + 8 * deltaTime, transformComponent.position.y);
        }

    }
}
