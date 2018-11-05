package se.snrn.gameoffjam.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import se.snrn.gameoffjam.components.ControlComponent;
import se.snrn.gameoffjam.components.TimedComponent;

public class TimedSystem extends IntervalIteratingSystem {

    private ComponentMapper<TimedComponent> tm;

    public TimedSystem() {
        super(Family.all(TimedComponent.class).get(), 1);
        tm = ComponentMapper.getFor(TimedComponent.class);

    }


    @Override
    protected void processEntity(Entity entity) {
        TimedComponent timedComponent = tm.get(entity);
        timedComponent.setLifespan(timedComponent.getLifespan() - 1);

        if(timedComponent.getLifespan() <= 0){
            getEngine().removeEntity(entity);
        }
    }
}
