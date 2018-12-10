package se.snrn.gameoffjam.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import se.snrn.gameoffjam.Lightning;
import se.snrn.gameoffjam.components.LightningComponent;

public class LightningSystem extends IteratingSystem {

    private ComponentMapper<LightningComponent> lm;
    private ComponentMapper<TransformComponent> tm;
    private SpriteBatch batch;
    private Vector2 tmp = new Vector2();

    public LightningSystem(SpriteBatch batch) {
        super(Family.all(LightningComponent.class).get());
        this.batch = batch;
        lm = ComponentMapper.getFor(LightningComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        Lightning.setTexture(new Texture(Gdx.files.internal("test.png")));
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        LightningComponent lightningComponent = lm.get(entity);
        TransformComponent transformComponent = tm.get(entity);

        tmp.set(transformComponent.position.x, transformComponent.position.y);
        batch.begin();
        Lightning.drawSphereLightning(batch, tmp, lightningComponent.getThinkness(), lightningComponent.getBolts(), lightningComponent.getRadius(), lightningComponent.getSegments());
        batch.end();
    }
}
