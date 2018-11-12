package se.snrn.gameoffjam;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.*;
import se.snrn.gameoffjam.components.CleanUpComponent;
import se.snrn.gameoffjam.components.TypeComponent;

import static se.snrn.gameoffjam.GameOffJam.MAX_TILT;
import static se.snrn.gameoffjam.GameOffJam.PPM;

public class EnemyFactory {

    public static Entity create(Engine engine, float x, float y) {
        Entity enemy = engine.createEntity();

        TextureRegion[] animations = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            animations[i] = new TextureRegion(new Texture(Gdx.files.internal("images/ships/mrshow/mrshow_acceleration_" + i + ".png")));

        }
        Animation<TextureRegion> textureRegionAnimation = new Animation<TextureRegion>(0.1f, animations);

        enemy
                .add(AnimationComponent.create(engine).addAnimation("default", textureRegionAnimation))
                .add(StateComponent.create(engine).set("default").setLooping(true))
                .add(TransformComponent.create(engine).setPosition(x, y).setScale(PPM, PPM))
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(new Texture(Gdx.files.internal("images/ships/mrshow/mrshow_acceleration_0.png")))))
                .add(TypeComponent.create(engine).setType(Type.ENEMY))
                .add(CleanUpComponent.create(engine))
                .add(TweenComponent.create(engine)
                .addTween(Tween.to(enemy, K2EntityTweenAccessor.POSITION_Y, 3f)
                        .ease(TweenEquations.easeInExpo)
                        .repeatYoyo(-1,0)
                        .target(50)))
                .add(BoundsComponent.create(engine).setBounds(-32, -32, 64, 64));

        return enemy;
    }
}
