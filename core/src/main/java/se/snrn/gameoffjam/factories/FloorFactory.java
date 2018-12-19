package se.snrn.gameoffjam.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import se.snrn.gameoffjam.components.MapSegmentComponent;

import java.util.Random;

import static se.snrn.gameoffjam.ScreenManager.*;

public class FloorFactory {
    public static Entity create(Engine engine, float x, float y) {
        Entity e = engine.createEntity();


        e.add(MapSegmentComponent.create(engine));

        e.add(TransformComponent.create(engine).setPosition(x + (SEGMENT_WIDTH / 2f), y).setScale(PIXELS_PER_METER, PIXELS_PER_METER));
        e.add(VelocityComponent.create(engine).setSpeed(-BACKGROUND_SPEED, 0));
        e.add(BoundsComponent.create(engine).setBounds(x + (SEGMENT_WIDTH / 2f), y, SEGMENT_WIDTH, 32));


        Random random = new Random();

        BackgroundFactory.create(engine, x, y+32, 0.5f, new Texture("images/mountain.png"));
        BackgroundFactory.create(engine, x, y+32, 0.5f, new Texture("images/mountain.png"));
        BackgroundFactory.create(engine, x, y+32, 0.5f, new Texture("images/mountain.png"));

        BackgroundFactory.create(engine, x, y+32, 1f, new Texture("images/bush.png"));
        BackgroundFactory.create(engine, x, y+32, 1f, new Texture("images/bush.png"));
        BackgroundFactory.create(engine, x, y+32, 1f, new Texture("images/bush.png"));

        engine.addEntity(EnemyFactory.create(engine, x + random.nextFloat() * SEGMENT_WIDTH, y + 64 + random.nextFloat() * HEIGHT));
        engine.addEntity(EnemyFactory.create(engine, x + random.nextFloat() * SEGMENT_WIDTH, y + 64 + random.nextFloat() * HEIGHT));
        engine.addEntity(EnemyFactory.create(engine, x + random.nextFloat() * SEGMENT_WIDTH, y + 64 + random.nextFloat() * HEIGHT));



        return e;

    }

}
