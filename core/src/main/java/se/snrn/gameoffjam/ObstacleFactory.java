package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.roaringcatgames.kitten2d.ashley.components.*;
import se.snrn.gameoffjam.components.CleanUpComponent;
import se.snrn.gameoffjam.components.TypeComponent;

public class ObstacleFactory {

    public static Entity create(Engine engine, float x, float y) {
        Entity player = engine.createEntity();




        player
                .add(TransformComponent.create(engine).setPosition(x, y).setScale(32, 32))
                .add(TypeComponent.create(engine).setType(Type.ENEMY))
                .add(CleanUpComponent.create(engine))
                .add(BoundsComponent.create(engine).setBounds(-32, -32, 64, 64))
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(new Texture(Gdx.files.internal("test.png")))));

        return player;
    }
}
