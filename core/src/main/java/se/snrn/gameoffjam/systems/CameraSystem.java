package se.snrn.gameoffjam.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import se.snrn.gameoffjam.components.CameraComponent;

public class CameraSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> tm;

    private OrthographicCamera camera;


    public CameraSystem(OrthographicCamera camera) {
        super(Family.all(CameraComponent.class).get());
        this.camera = camera;
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent transformComponent = tm.get(entity);


        camera.update(true);


        camera.position.set(transformComponent.position.x,-64,0);
    }
}
