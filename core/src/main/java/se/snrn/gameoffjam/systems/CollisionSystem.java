package se.snrn.gameoffjam.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import se.snrn.gameoffjam.Type;
import se.snrn.gameoffjam.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {

    private ComponentMapper<BoundsComponent> bm;
    private ComponentMapper<TypeComponent> tm;

    public CollisionSystem() {
        super(Family.all(BoundsComponent.class, TypeComponent.class).get());
        bm = ComponentMapper.getFor(BoundsComponent.class);
        tm = ComponentMapper.getFor(TypeComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        BoundsComponent boundsComponent = bm.get(entity);
        TypeComponent typeComponent = tm.get(entity);

        for (Entity otherEntity : getEntities()) {
            if (boundsComponent != bm.get(otherEntity) && boundsComponent.bounds.overlaps(bm.get(otherEntity).bounds)) {
                Type entityType = typeComponent.getType();
                Type otherType = tm.get(entity).getType();

                System.out.println(entityType);
                System.out.println(otherType);
                if(entityType == Type.BULLET && otherType != Type.PLAYER){
                    getEngine().removeEntity(entity);
                }
                if(entityType == Type.ENEMY){
                    getEngine().removeEntity(entity);
                }
            }
        }
    }
}
