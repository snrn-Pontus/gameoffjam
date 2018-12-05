package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.esotericsoftware.spine.attachments.SkeletonAttachment;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import se.snrn.gameoffjam.components.*;

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
//                .add(ParticleEmitterComponent.create(engine)
//                        .setParticleImage(new TextureRegion(new Texture(Gdx.files.internal("test.png"))))
//                        .setDuration(10f)
//                        .setParticleMinMaxScale(0.75f, 1.25f)
//                        .setShouldLoop(true)
//                        .setParticleLifespans(0.1f, 0.5f)
//                        .setAngleRange(80, 100)
//                        .setSpawnRate(30)
//                        .setSpeed(30, 100)
//                        .setShouldFade(true))
                .add(VelocityComponent.create(engine).setSpeed(SPEED, 0))
                .add(TypeComponent.create(engine).setType(Type.PLAYER))
                .add(BoundsComponent.create(engine).setOffset(10, 10).setBounds(-PIXELS_PER_METER, -PIXELS_PER_METER, 122.39703f, 137.958f));


        final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("spine/llama.atlas"));


        SkeletonJson json = new SkeletonJson(atlas);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("spine/llama.json"));


        Skeleton skeleton = new Skeleton(skeletonData);


        skeleton.setPosition(x, y);
        skeleton.updateWorldTransform();


        SkeletonAttachment skeletonAttachment = new SkeletonAttachment("heli");


        final TextureAtlas heliAtlas = new TextureAtlas(Gdx.files.internal("spine/heli.atlas"));


        SkeletonJson heliJson = new SkeletonJson(heliAtlas);
        SkeletonData heliSkeletonData = heliJson.readSkeletonData(Gdx.files.internal("spine/heli.json"));


        Skeleton heliSkeleton = new Skeleton(heliSkeletonData);

        heliSkeleton.getRootBone().setScale(0.3f, 0.3f);

        AnimationStateData heliStateData = new AnimationStateData(heliSkeletonData);


        AnimationState heliState = new AnimationState(heliStateData);

        heliState.setAnimation(0, "animation", true);

        skeletonAttachment.setSkeleton(heliSkeleton);
        Slot slot = skeleton.findSlot("attachment");
        slot.setAttachment(skeletonAttachment);
        Bone attachmentBone = slot.getBone();

        skeleton.setBonesToSetupPose();

        skeleton.getRootBone().setScale(0.3f, 0.3f);

        player.add(AttachmentComponent.create(engine).setSkeleton(heliSkeleton).setState(heliState));

        player.add(SkeletonComponent.create(engine).setSkeleton(skeleton));

        AnimationStateData stateData = new AnimationStateData(skeletonData);

        AnimationState state = new AnimationState(stateData);
        state.setAnimation(0, "animation", true);

        player.add(AnimationStateComponent.create(engine).setState(state));


        return player;
    }
}
