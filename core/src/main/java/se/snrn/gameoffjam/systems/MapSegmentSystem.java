package se.snrn.gameoffjam.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import se.snrn.gameoffjam.FloorFactory;
import se.snrn.gameoffjam.components.MapSegmentComponent;

import java.util.ArrayDeque;
import java.util.Queue;

import static se.snrn.gameoffjam.GameOffJam.SEGMENT_WIDTH;

public class MapSegmentSystem extends IteratingSystem {

    private final ComponentMapper<TransformComponent> tm;
    private final ComponentMapper<BoundsComponent> bm;
    private OrthographicCamera camera;

    private Queue<Entity> mapSegments;

    public MapSegmentSystem(OrthographicCamera camera) {
        super(Family.all(MapSegmentComponent.class, TransformComponent.class).get());
        this.camera = camera;
        tm = ComponentMapper.getFor(TransformComponent.class);
        bm = ComponentMapper.getFor(BoundsComponent.class);
        mapSegments = new ArrayDeque<Entity>();
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = tm.get(entity);
        BoundsComponent boundsComponent = bm.get(entity);

        if (transformComponent.position.x < camera.position.x && !camera.frustum.boundsInFrustum(transformComponent.position.x, transformComponent.position.y, transformComponent.position.z,
                boundsComponent.bounds.width / 2, boundsComponent.bounds.height / 2, 1)) {
            getEngine().removeEntity(entity);
            System.out.println("removed");

            System.out.println(transformComponent.position);
            getEngine().addEntity(FloorFactory.create(getEngine(), transformComponent.position.x + (SEGMENT_WIDTH * 1.5f), transformComponent.position.y));
        }

    }

}
