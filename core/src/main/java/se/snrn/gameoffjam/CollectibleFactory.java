package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.kitten2d.ashley.components.*;
import se.snrn.gameoffjam.components.CleanUpComponent;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.GameOffJam.PPM;

public class CollectibleFactory {

    public static Entity create(Engine engine, float x, float y) {
        Entity collectible = engine.createEntity();


        collectible
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(new Texture(Gdx.files.internal("images/bullet.png")))))
                .add(TransformComponent.create(engine).setPosition(x, y).setScale(PPM, PPM))
                .add(TypeComponent.create(engine).setType(Type.COLLECTIBLE))
                .add(CleanUpComponent.create(engine))
                .add(BoundsComponent.create(engine).setBounds(x, y, 64,64));
        System.out.println("create");
        return collectible;
    }
}
