package se.snrn.gameoffjam;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;
import com.roaringcatgames.kitten2d.gdx.helpers.IPreferenceManager;
import com.roaringcatgames.kitten2d.gdx.helpers.K2PreferenceManager;

import java.util.HashMap;

public class ScreenManager implements IGameProcessor, ApplicationListener {

    public static final int WIDTH = 1280, HEIGHT = 720, WORLD_WIDTH = 320, WORLD_HEIGHT = 180;
    public static final int SEGMENT_WIDTH = 1280 * 2;
    public static final int PPM = 32;
    public static final int PIXELS_PER_METER = 32;
    public static final int BULLET_SPEED = 512;
    public static final int SPEED = 0;
    public static final int BACKGROUND_SPEED = 256;
    public static final int MAX_TILT = 20;

    private GameScreen gameScreen;
    private MapBuilder mapBuilder;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private IPreferenceManager preferenceManager;
    private InputMultiplexer inputMultiplexer;
    private ScreenAdapter screen;
    private HashMap<String, ScreenAdapter> screens;


    @Override
    public void addInputProcessor(InputProcessor processor) {
        inputMultiplexer.addProcessor(processor);
    }

    @Override
    public void removeInputProcessor(InputProcessor processor) {
        inputMultiplexer.removeProcessor(processor);
    }

    @Override
    public void pauseBgMusic() {

    }

    @Override
    public void playBgMusic(String musicName) {

    }

    @Override
    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public OrthographicCamera getGUICamera() {
        return null;
    }

    @Override
    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public IPreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    @Override
    public void create() {
        screens = new HashMap<>();
        preferenceManager = new K2PreferenceManager("gameoff");
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        batch = new SpriteBatch();
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        gameScreen = new GameScreen(this);
        mapBuilder = new MapBuilder(this);
        screens.put("gameScreen", gameScreen);
        screens.put("mapBuilder", mapBuilder);
        screen = mapBuilder;
    }

    @Override
    public void switchScreens(String screenName) {
        screen = screens.get(screenName);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
