package se.snrn.gameoffjam.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;

public class CollisionSystem extends IteratingSystem {

    private ComponentMapper<BoundsComponent> bm;

    public CollisionSystem() {
        super(Family.all(BoundsComponent.class).get());
        bm = ComponentMapper.getFor(BoundsComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        BoundsComponent boundsComponent = bm.get(entity);

        for (Entity otherEntity : getEntities()) {
            if (boundsComponent != bm.get(otherEntity) && boundsComponent.bounds.overlaps(bm.get(otherEntity).bounds)) {
                System.out.println("overlap");
            }
        }
    }
}
