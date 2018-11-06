package se.snrn.gameoffjam.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.ParticleEmitterComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.strongjoshua.console.LogLevel;
import se.snrn.gameoffjam.Type;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.GameOffJam.console;

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
                Type otherType = tm.get(otherEntity).getType();

                switch (entityType) {
                    case PLAYER:
                        break;
                    case BULLET:
                        switch (otherType) {
                            case ENEMY:
//                                getEngine().removeEntity(otherEntity);
                                getEngine().removeEntity(entity);
                                otherEntity.add(
                                        ParticleEmitterComponent.create(getEngine())
                                                .setParticleImage(new TextureRegion(new Texture(Gdx.files.internal("test.png"))))
                                                .setDuration(0.2f)
                                                .setParticleLifespans(0.1f, 0.5f)
                                                .setAngleRange(0, 360)
                                                .setSpawnRate(10)
                                                .setSpeed(100, 100)
                                                .setShouldFade(true)
                                                .setSpawnRange(1, 1)
                                );
                                otherEntity.remove(TextureComponent.class);
                                otherEntity.remove(BoundsComponent.class);
                                console.log(entityType + " hit " + otherType, LogLevel.DEFAULT);
                                break;
                        }
                        break;
                    case ENEMY:
                        switch (otherType) {
                            case PLAYER:
                            case BULLET:
                                getEngine().removeEntity(otherEntity);
                                //getEngine().removeEntity(entity);
                                console.log(entityType + " hit " + otherType, LogLevel.DEFAULT);
                                break;
                        }
                        break;
                }
            }
        }
    }
}
