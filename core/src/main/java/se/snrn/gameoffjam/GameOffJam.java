package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.kiwi.util.gdx.AbstractApplicationListener;
import com.github.czyzby.kiwi.util.gdx.asset.Disposables;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActorConsumer;
import com.github.czyzby.lml.util.Lml;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.roaringcatgames.kitten2d.ashley.systems.*;
import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.GUIConsole;
import se.snrn.gameoffjam.components.ControlComponent;
import se.snrn.gameoffjam.systems.CameraSystem;
import se.snrn.gameoffjam.systems.CollisionSystem;
import se.snrn.gameoffjam.systems.ControlSystem;
import se.snrn.gameoffjam.systems.UISystem;

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
    private InputManager inputManager;
    private GUIConsole console;


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
        engine.addSystem(new ControlSystem());
        engine.addSystem(new RenderingSystem(batch, camera, 32));
        engine.addSystem(new CameraSystem(camera));
        engine.addSystem(new Box2DPhysicsDebugSystem(world, camera));
        engine.addSystem(new DebugSystem(camera, Input.Keys.TAB));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BoundsSystem());


        engine.addSystem(new UISystem((Label) parser.getActorsMappedByIds().get("distance")));
        //engine.addSystem(new GravitySystem(new Vector2(0f,-9.8f)));
        engine.addSystem(new CollisionSystem());


        Entity player = PlayerFactory.create(engine, world,0,-HEIGHT/2f);
        engine.addEntity(player);

        //Entity player2 = PlayerFactory.create(engine, world,250,0);
        //engine.addEntity(player2);


        FloorFactory.create(engine, world, 0, (-HEIGHT / 2f)-64);

        inputManager = new InputManager(player.getComponent(ControlComponent.class));

        Gdx.input.setInputProcessor(inputManager);
        console = new GUIConsole();
        console.setDisplayKeyID(Input.Keys.NUM_1);
        console.setCommandExecutor(new CommandExecutor() {
            @Override
            protected void setConsole(Console c) {
                super.setConsole(c);
            }

            public void testString(String str) {
                console.log(str);
            }
        });


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
        console.draw();
    }

    @Override
    public void dispose() {
        // Null-safe disposing utility method:
        Disposables.disposeOf(stage, skin);
    }
}