package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.roaringcatgames.kitten2d.ashley.components.BodyComponent;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import se.snrn.gameoffjam.components.ControlComponent;

public class PlayerFactory {

    public static Entity create(Engine engine, World world) {
        Entity player = engine.createEntity();

        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(0, 0);

        bodyDef.type = BodyDef.BodyType.KinematicBody;

        FixtureDef fixtureDef = new FixtureDef();

        PolygonShape polygonShape = new PolygonShape();

        polygonShape.setAsBox(32, 32);

        fixtureDef.shape = polygonShape;

        //Body body = world.createBody(bodyDef);

        //body.createFixture(fixtureDef);


        player
                .add(ControlComponent.create(engine))
                .add(TransformComponent.create(engine).setPosition(0, 0).setScale(32, 32))
                //.add(BodyComponent.create(engine).setBody(body))
                .add(BoundsComponent.create(engine).setBounds(-32,-32,64,64))
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(new Texture(Gdx.files.internal("test.png")))));

        return player;
    }
}
