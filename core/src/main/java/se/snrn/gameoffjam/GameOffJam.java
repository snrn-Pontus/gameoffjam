package se.snrn.gameoffjam;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.kiwi.util.gdx.AbstractApplicationListener;
import com.github.czyzby.kiwi.util.gdx.asset.Disposables;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActorConsumer;
import com.github.czyzby.lml.util.Lml;
import com.roaringcatgames.kitten2d.ashley.systems.Box2DPhysicsDebugSystem;
import com.roaringcatgames.kitten2d.ashley.systems.Box2DPhysicsSystem;
import com.roaringcatgames.kitten2d.ashley.systems.DebugSystem;
import com.roaringcatgames.kitten2d.ashley.systems.RenderingSystem;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class GameOffJam extends AbstractApplicationListener {
    /**
     * Default application size.
     */
    public static final int WIDTH = 1280, HEIGHT = 720;
    public static final int PPM = 32;
    private Stage stage;
    private Skin skin;
    private PooledEngine engine;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private World world;


    @Override
    public void create() {
        stage = new Stage(new FitViewport(WIDTH, HEIGHT));
        skin = new Skin(Gdx.files.internal("ui/skin.json"));

        LmlParser parser = Lml.parser(skin)
                // Adding action for the button listener:
                .action("setClicked", new ActorConsumer<Void, TextButton>() {
                    @Override
                    public Void consume(TextButton actor) {
                        actor.setText("Clicked.");
                        return null;
                    }
                })
                // Adding showing action for the window:
                .action("fadeIn", new ActorConsumer<Action, Window>() {
                    @Override
                    public Action consume(Window actor) {
                        return Actions.fadeIn(1f);
                    }
                }).build();

        // Parsing actors defined in main.lml template and adding them to stage:
        parser.fillStage(stage, Gdx.files.internal("ui/templates/main.lml"));
        // Note: there are less verbose and more powerful ways of using LML. See other LML project templates.

        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();


        world = new World(new Vector2(0, -9.8f), true);

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        viewport = new FitViewport(320, 180, camera);

        engine = new PooledEngine();

        engine.addSystem(new Box2DPhysicsSystem(world));
        engine.addSystem(new RenderingSystem(batch, camera, 32));
        engine.addSystem(new Box2DPhysicsDebugSystem(world, camera));
        engine.addSystem(new DebugSystem(camera, Input.Keys.TAB));

        engine.addEntity(PlayerFactory.create(engine, world));


        FloorFactory.create(engine, world, 0, -HEIGHT/2f);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void render(float deltaTime) {
        // AbstractApplicationListener automatically clears the screen with black color.
        engine.update(deltaTime);
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void dispose() {
        // Null-safe disposing utility method:
        Disposables.disposeOf(stage, skin);
    }
}