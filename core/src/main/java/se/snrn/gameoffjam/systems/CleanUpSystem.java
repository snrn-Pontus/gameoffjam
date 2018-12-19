package se.snrn.gameoffjam.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.strongjoshua.console.LogLevel;
import se.snrn.gameoffjam.components.CleanUpComponent;

import static se.snrn.gameoffjam.GameScreen.console;

public class CleanUpSystem extends IteratingSystem {

    private final ComponentMapper<TransformComponent> tm;
    private final ComponentMapper<BoundsComponent> bm;
    private OrthographicCamera camera;

    public CleanUpSystem(OrthographicCamera camera) {
        super(Family.all(CleanUpComponent.class, TransformComponent.class, BoundsComponent.class).get());
        this.camera = camera;
        tm = ComponentMapper.getFor(TransformComponent.class);
        bm = ComponentMapper.getFor(BoundsComponent.class);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = tm.get(entity);
        BoundsComponent boundsComponent = bm.get(entity);

        if (transformComponent.position.x < camera.position.x
                && !camera.frustum.boundsInFrustum(
                transformComponent.position.x,
                transformComponent.position.y,
               0,
                boundsComponent.bounds.width / 2,
                boundsComponent.bounds.height / 2, 1)) {
            getEngine().removeEntity(entity);
            console.log("entity removed", LogLevel.SUCCESS);

        }

    }
}
