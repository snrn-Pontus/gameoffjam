package se.snrn.gameoffjam.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.SkeletonBounds;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.SkeletonRendererDebug;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import se.snrn.gameoffjam.components.*;

/******************************************************************************
 * Spine Runtimes Software License v2.5
 *
 * Copyright (c) 2013-2016, Esoteric Software
 * All rights reserved.
 *
 * You are granted a perpetual, non-exclusive, non-sublicensable, and
 * non-transferable license to use, install, execute, and perform the Spine
 * Runtimes software and derivative works solely for personal or internal
 * use. Without the written permission of Esoteric Software (see Section 2 of
 * the Spine Software License Agreement), you may not (a) modify, translate,
 * adapt, or develop new applications using the Spine Runtimes or otherwise
 * create derivative works or improvements of the Spine Runtimes or (b) remove,
 * delete, alter, or obscure any trademarks or any copyright, trademark, patent,
 * or other intellectual property or proprietary rights notices on or in the
 * Software, including any copy thereof. Redistributions in binary or source
 * form must include this license and terms.
 *
 * THIS SOFTWARE IS PROVIDED BY ESOTERIC SOFTWARE "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL ESOTERIC SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES, BUSINESS INTERRUPTION, OR LOSS OF
 * USE, DATA, OR PROFITS) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *****************************************************************************/


public class SpineSystem extends IteratingSystem {
    private final ComponentMapper<AnimationStateComponent> asm;
    private final ComponentMapper<SkeletonComponent> sm;
    private final ComponentMapper<TransformComponent> tm;
    private final ComponentMapper<ControlComponent> cm;
    private final ComponentMapper<AttachmentComponent> am;
    private final ComponentMapper<ShooterComponent> shoterm;


    private Array<Entity> renderQueue;


    private SpriteBatch batch;
    private SkeletonRenderer skeletonRenderer;


    private OrthographicCamera camera;
    SkeletonRendererDebug debugRenderer;
    private SkeletonBounds bounds;

    public SpineSystem(SpriteBatch batch, OrthographicCamera camera) {
        super(Family.all(AnimationStateComponent.class, SkeletonComponent.class).get());
        this.batch = batch;
        this.camera = camera;
        skeletonRenderer = new SkeletonRenderer();
        skeletonRenderer.setPremultipliedAlpha(true);
        asm = ComponentMapper.getFor(AnimationStateComponent.class);
        sm = ComponentMapper.getFor(SkeletonComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        cm = ComponentMapper.getFor(ControlComponent.class);
        am = ComponentMapper.getFor(AttachmentComponent.class);
        shoterm = ComponentMapper.getFor(ShooterComponent.class);
        renderQueue = new Array<>();

        debugRenderer = new SkeletonRendererDebug();

        //debugRenderer.setScale(0.3f);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        debugRenderer.getShapeRenderer().setProjectionMatrix(camera.combined);


        for (Entity entity : renderQueue) {
            batch.begin();

            AnimationStateComponent animationStateComponent = asm.get(entity);
            SkeletonComponent skeletonComponent = sm.get(entity);
            TransformComponent transformComponent = tm.get(entity);
            AttachmentComponent attachmentComponent = am.get(entity);

            if (attachmentComponent != null) {
                attachmentComponent.getSkeleton().setPosition(transformComponent.position.x,transformComponent.position.y);
                attachmentComponent.getState().update(deltaTime);
                attachmentComponent.getState().apply(attachmentComponent.getSkeleton());
                attachmentComponent.getSkeleton().updateWorldTransform();
            }


            skeletonComponent.getSkeleton().setPosition(transformComponent.position.x, transformComponent.position.y);
            //skeletonComponent.getSkeleton().getRootBone().setScale(0.3f, 0.3f);
            skeletonComponent.getSkeleton().getRootBone().setRotation(transformComponent.rotation);
            animationStateComponent.getState().update(deltaTime);
            animationStateComponent.getState().apply(skeletonComponent.getSkeleton());
            skeletonComponent.getSkeleton().updateWorldTransform();

            bounds = new SkeletonBounds();

            bounds.update(skeletonComponent.getSkeleton(),true);


            skeletonRenderer.draw(batch, skeletonComponent.getSkeleton());

            batch.end();

            debugRenderer.draw(skeletonComponent.getSkeleton());

            //if (attachmentComponent != null) {
            //    debugRenderer.draw(attachmentComponent.getSkeleton());
            //}
        }

        renderQueue.clear();
    }
}