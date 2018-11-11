package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import se.snrn.gameoffjam.components.MapSegmentComponent;

import java.util.Random;

import static se.snrn.gameoffjam.GameOffJam.*;

public class FloorFactory {

    public static Entity create(Engine engine, float x, float y) {
        Entity e = engine.createEntity();


        e.add(MapSegmentComponent.create(engine));

        e.add(TransformComponent.create(engine).setPosition(x + (SEGMENT_WIDTH / 2f), y).setScale(PIXELS_PER_METER, PIXELS_PER_METER));

        e.add(BoundsComponent.create(engine).setBounds(x + (SEGMENT_WIDTH / 2f), y, SEGMENT_WIDTH, 32));

        Random random = new Random();


        engine.addEntity(ObstacleFactory.create(engine, x + random.nextFloat() * SEGMENT_WIDTH, y + 64 + random.nextFloat() * HEIGHT));
        engine.addEntity(ObstacleFactory.create(engine, x + random.nextFloat() * SEGMENT_WIDTH, y + 64 + random.nextFloat() * HEIGHT));
        engine.addEntity(ObstacleFactory.create(engine, x + random.nextFloat() * SEGMENT_WIDTH, y + 64 + random.nextFloat() * HEIGHT));

        return e;

    }
}
