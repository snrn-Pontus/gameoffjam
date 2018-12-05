package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import se.snrn.gameoffjam.components.CleanUpComponent;

import java.util.Random;

import static se.snrn.gameoffjam.GameOffJam.*;

public class BackgroundFactory {
    public static Entity create(Engine engine, float x, float y, float speedModifier, Texture texture) {

        Random random = new Random();


        float height = texture.getHeight();
        float width = texture.getWidth();

        Entity background = engine.createEntity();
        background.add(TransformComponent.create(engine).setPosition(x + random.nextFloat() * SEGMENT_WIDTH, y + 32).setScale(PIXELS_PER_METER, PIXELS_PER_METER));
        background.add(TextureComponent.create(engine).setRegion(new TextureRegion(texture)));
        background.add(BoundsComponent.create(engine).setBounds(-32, -32, width, height));
        background.add(VelocityComponent.create(engine).setSpeed(-BACKGROUND_SPEED * speedModifier, 0));
        background.add(CleanUpComponent.create(engine));
        engine.addEntity(background);


        return background;

    }
}
