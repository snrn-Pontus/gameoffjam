package se.snrn.gameoffjam.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import se.snrn.gameoffjam.components.LaserComponent;
import se.snrn.gameoffjam.components.LightningComponent;

public class LaserSystem extends IteratingSystem {

    private ComponentMapper<LaserComponent> lm;
    private ComponentMapper<TransformComponent> tm;
    private SpriteBatch batch;
    private Vector2 tmp = new Vector2();

    public LaserSystem(SpriteBatch batch) {
        super(Family.all(LaserComponent.class).get());
        this.batch = batch;
        lm = ComponentMapper.getFor(LaserComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        LaserComponent laserComponent = lm.get(entity);
        TransformComponent transformComponent = tm.get(entity);

        tmp.set(transformComponent.position.x, transformComponent.position.y);
        batch.begin();
        laserComponent.getLaser().position = tmp;
        laserComponent.getLaser().degrees = transformComponent.rotation+90;
        laserComponent.getLaser().render(batch);
        batch.end();
    }
}
