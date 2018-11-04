package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.roaringcatgames.kitten2d.ashley.components.BodyComponent;

import static se.snrn.gameoffjam.GameOffJam.WIDTH;

public class FloorFactory {

    public static Entity create(Engine engine, World world, float x, float y){
            Entity e = engine.createEntity();
            // Create our body definition
            BodyDef groundBodyDef =new BodyDef();
            groundBodyDef.type = BodyDef.BodyType.StaticBody;

            // Set its world position
            groundBodyDef.position.set(new Vector2(x,y));
            groundBodyDef.angle = 0f;

            BodyComponent bc = new BodyComponent();
            // Create a body from the defintion and add it to the world
            bc.body = world.createBody(groundBodyDef);

            // Create a polygon shape
            PolygonShape groundBox = new PolygonShape();
            // Set the polygon shape as a box which is twice the size of our view port and 20 high
            // (setAsBox takes half-width and half-height as arguments)

            groundBox.setAsBox(WIDTH/2f, 32);


            // Create a fixture from our polygon shape and add it to our ground body
            bc.body.createFixture(groundBox, 0.0f);


            // Clean up after ourselves
            groundBox.dispose();

            e.add(bc);

            return e;

        }
}
