package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.roaringcatgames.kitten2d.ashley.components.*;
import se.snrn.gameoffjam.components.CameraComponent;
import se.snrn.gameoffjam.components.ControlComponent;
import se.snrn.gameoffjam.components.PlayerComponent;
import se.snrn.gameoffjam.components.TypeComponent;

public class ObstacleFactory {

    public static Entity create(Engine engine, World world, float x, float y) {
        Entity player = engine.createEntity();

        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(x, y);

        bodyDef.type = BodyDef.BodyType.DynamicBody;

        FixtureDef fixtureDef = new FixtureDef();

        PolygonShape polygonShape = new PolygonShape();

        polygonShape.setAsBox(32, 32);

        fixtureDef.shape = polygonShape;

        //Body body = world.createBody(bodyDef);

        //body.createFixture(fixtureDef);


        player
                .add(TransformComponent.create(engine).setPosition(x, y).setScale(32, 32))
                //.add(BodyComponent.create(engine).setBody(body))
                .add(TypeComponent.create(engine).setType(Type.ENEMY))
                .add(BoundsComponent.create(engine).setBounds(-32, -32, 64, 64))
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(new Texture(Gdx.files.internal("test.png")))));

        return player;
    }
}
