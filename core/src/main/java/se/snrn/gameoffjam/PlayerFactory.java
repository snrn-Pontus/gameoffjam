package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import se.snrn.gameoffjam.components.CameraComponent;
import se.snrn.gameoffjam.components.ControlComponent;
import se.snrn.gameoffjam.components.PlayerComponent;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.GameOffJam.PIXELS_PER_METER;
import static se.snrn.gameoffjam.GameOffJam.SPEED;

public class PlayerFactory {

    public static Entity create(Engine engine, float x, float y) {
        Entity player = engine.createEntity();


        player
                .add(ControlComponent.create(engine))
                .add(TransformComponent.create(engine).setPosition(x, y).setScale(PIXELS_PER_METER, PIXELS_PER_METER))
                .add(CameraComponent.create(engine).setOffset(-64))
                .add(PlayerComponent.create(engine))
                .add(VelocityComponent.create(engine).setSpeed(SPEED, 0))
                .add(TypeComponent.create(engine).setType(Type.PLAYER))
                .add(BoundsComponent.create(engine).setBounds(-PIXELS_PER_METER, -PIXELS_PER_METER, 32, 32))
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(new Texture(Gdx.files.internal("ship.png")))));

        return player;
    }
}
