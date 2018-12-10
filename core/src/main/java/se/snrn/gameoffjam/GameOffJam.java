package se.snrn.gameoffjam;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.kiwi.util.gdx.asset.Disposables;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActorConsumer;
import com.github.czyzby.lml.util.Lml;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.systems.*;
import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.GUIConsole;
import se.snrn.gameoffjam.components.ControlComponent;
import se.snrn.gameoffjam.components.PlayerComponent;
import se.snrn.gameoffjam.map.Map;
import se.snrn.gameoffjam.systems.AnimationSystem;
import se.snrn.gameoffjam.systems.*;

import static se.snrn.gameoffjam.ScreenManager.*;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class GameOffJam extends ScreenAdapter {
    /**
     * Default application size.
     */


    private Stage stage;
    private Skin skin;
    private PooledEngine engine;
    private InputManager inputManager;
    public static GUIConsole console;
    private Laser laser;
    private ScreenManager screenManager;

    public GameOffJam(ScreenManager screenManager) {
        super();
        this.screenManager = screenManager;
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


        screenManager.addInputProcessor(stage);


        engine = new PooledEngine();

        engine.addSystem(new RenderingSystem(screenManager.getBatch(), screenManager.getCamera(), PIXELS_PER_METER));
        engine.addSystem(new ControlSystem());
        engine.addSystem(new CameraSystem(screenManager.getCamera()));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new TweenSystem());
        engine.addSystem(new TimedSystem());
        engine.addSystem(new FadingSystem());
        engine.addSystem(new DebugSystem(screenManager.getCamera(), Input.Keys.TAB));
        engine.addSystem(new UISystem(
                (Label) parser.getActorsMappedByIds().get("distance"),
                (Label) parser.getActorsMappedByIds().get("score"))
        );
        //engine.addSystem(new GravitySystem(new Vector2(0f,-9.8f)));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new ParticleSystem());
        engine.addSystem(new SpineSystem(screenManager.getBatch(), screenManager.getCamera()));
        engine.addSystem(new RotationSystem());
        engine.addSystem(new MapSegmentSystem(screenManager.getCamera()));
        engine.addSystem(new CleanUpSystem(screenManager.getCamera()));
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new FollowerSystem(Family.all(PlayerComponent.class).get()));
        engine.addSystem(new MultiBoundsSystem());
        engine.addSystem(new LightningSystem(screenManager.getBatch()));

        Entity player = PlayerFactory.create(engine, 0, HEIGHT / 2f);
        engine.addEntity(player);

        Entity background = engine.createEntity();
        background
                .add(TextureComponent.create(engine).setRegion(new TextureRegion(new Texture(Gdx.files.internal("images/sky.png")))))
                .add(TransformComponent.create(engine).setScale(PPM, PPM).setPosition(WIDTH / 2f - 64, HEIGHT / 2f, 10));

        engine.addEntity(background);

        screenManager.getCamera().position.set(WIDTH / 2f - 64, HEIGHT / 2f, 0);


        inputManager = new InputManager(player.getComponent(ControlComponent.class));


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


        stage.setDebugAll(true);

        new Map("segment.json", engine, 0, 16);
        new Map("segment.json", engine, SEGMENT_WIDTH, 16);

        laser = new Laser();

        screenManager.addInputProcessor(stage);
        screenManager.addInputProcessor(inputManager);

    }

    @Override
    public void show() {
        super.show();
        screenManager.addInputProcessor(stage);
        screenManager.addInputProcessor(inputManager);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
        engine.update(deltaTime);
        stage.act(deltaTime);
        stage.draw();
        console.draw();
        screenManager.getBatch().begin();
        laser.render(screenManager.getBatch());
        screenManager.getBatch().end();
    }

    @Override
    public void dispose() {
        // Null-safe disposing utility method:
        Disposables.disposeOf(stage, skin);
    }
}