package se.snrn.gameoffjam.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.esotericsoftware.spine.attachments.PointAttachment;
import com.esotericsoftware.spine.attachments.SkeletonAttachment;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import se.snrn.gameoffjam.Type;
import se.snrn.gameoffjam.components.*;

import static se.snrn.gameoffjam.ScreenManager.*;

public class PlayerFactory {


    public static Entity create(Engine engine, float x, float y) {
        Entity player = engine.createEntity();


        player
                .add(ControlComponent.create(engine))
                .add(TransformComponent.create(engine).setPosition(x, y).setScale(PPM, PPM))
                .add(CameraComponent.create(engine).setOffset(-64))
                .add(PlayerComponent.create(engine))
                .add(VelocityComponent.create(engine).setSpeed(SPEED, 0))
                .add(TypeComponent.create(engine).setType(Type.PLAYER))
                .add(BoundsComponent.create(engine).setOffset(10, 10).setBounds(-PPM, -PPM, 122.39703f, 137.958f));


        final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("spine/llama/llama.atlas"));


        SkeletonJson json = new SkeletonJson(atlas);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("spine/llama/llama.json"));


        Skeleton skeleton = new Skeleton(skeletonData);


        skeleton.setPosition(x, y);
        skeleton.updateWorldTransform();




        final TextureAtlas heliAtlas = new TextureAtlas(Gdx.files.internal("spine/attachments/heli.atlas"));


        SkeletonJson heliJson = new SkeletonJson(heliAtlas);
        SkeletonData heliSkeletonData = heliJson.readSkeletonData(Gdx.files.internal("spine/attachments/heli.json"));
        SkeletonAttachment skeletonAttachment = new SkeletonAttachment("heli");


        Skeleton heliSkeleton = new Skeleton(heliSkeletonData);


        AnimationStateData heliStateData = new AnimationStateData(heliSkeletonData);


        AnimationState heliState = new AnimationState(heliStateData);

        heliState.setAnimation(0, "animation", true);

        skeletonAttachment.setSkeleton(heliSkeleton);
        Slot slot = skeleton.findSlot("attachment");
        slot.setAttachment(skeletonAttachment);

        skeleton.setBonesToSetupPose();

        heliSkeleton.getRootBone().setScale(0.3f, 0.3f);

        skeleton.getRootBone().setScale(0.3f, 0.3f);

        player.add(AttachmentComponent.create(engine).setSkeleton(heliSkeleton).setState(heliState));

        player.add(SkeletonComponent.create(engine).setSkeleton(skeleton));

        AnimationStateData stateData = new AnimationStateData(skeletonData);

        AnimationState state = new AnimationState(stateData);
        state.setAnimation(0, "animation", true);

        player.add(AnimationStateComponent.create(engine).setState(state));


        PointAttachment attachment = (PointAttachment) skeleton.getAttachment("shoot", "shoot");
        Bone bone2 = skeleton.findBone("bone3");
        ShooterComponent shooterComponent = ShooterComponent.create(engine)
                .setAttachment(attachment)
                .setBone(bone2);

        player.add(shooterComponent);


        return player;
    }
}
