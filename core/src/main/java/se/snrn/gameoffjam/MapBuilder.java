package se.snrn.gameoffjam;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.kiwi.util.gdx.asset.Disposables;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActorConsumer;
import com.github.czyzby.lml.vis.util.VisLmlParserBuilder;
import com.kotcrab.vis.ui.layout.VerticalFlowGroup;
import com.kotcrab.vis.ui.widget.Draggable;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.strongjoshua.console.GUIConsole;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import static se.snrn.gameoffjam.ScreenManager.HEIGHT;
import static se.snrn.gameoffjam.ScreenManager.WIDTH;
import static se.snrn.gameoffjam.map.Map.*;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */

public class MapBuilder extends ScreenAdapter implements Screen {
    /**
     * Default application size.
     */


    private Stage stage;
    private Skin skin;
    private PooledEngine engine;
    public static GUIConsole console;

    private LmlParser parser;
    private Color selectedColor = Color.RED;
    private VisImage selectedColorActor;
    private SelectBox<String> files;
    private ScreenManager screenManager;


    public MapBuilder(ScreenManager screenManager) {
        this.screenManager = screenManager;
        stage = new Stage(new FitViewport(WIDTH, HEIGHT));

        skin = new Skin(Gdx.files.internal("ui/skin.json"));

        final VisLmlParserBuilder visLmlParserBuilder = new VisLmlParserBuilder();

        visLmlParserBuilder.skin(skin);
        visLmlParserBuilder
                .action("save", new ActorConsumer<Void, TextButton>() {
                    @Override
                    public Void consume(TextButton actor) {
                        saveMap();
                        return null;
                    }
                })
                .action("paint", new ActorConsumer<Void, VisImage>() {
                    @Override
                    public Void consume(VisImage actor) {
                        actor.setColor(selectedColor);
                        return null;
                    }
                })
                .action("setColor", new ActorConsumer<Void, VisImage>() {
                    @Override
                    public Void consume(VisImage actor) {
                        selectedColor = actor.getColor();
                        if (selectedColorActor != null) {
                            selectedColorActor.setScale(1);
                        }
                        actor.setScale(1.5f);
                        selectedColorActor = actor;
                        return null;
                    }
                })
                .action("load", new ActorConsumer<Void, TextButton>() {
                    @Override
                    public Void consume(TextButton actor) {
                        loadMap();
                        return null;
                    }
                })

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
                });

        visLmlParserBuilder.build();

        parser = visLmlParserBuilder.build();


        // Parsing actors defined in main.lml template and adding them to stage:
        parser.fillStage(stage, Gdx.files.internal("ui/templates/editor.lml"));
        // Note: there are less verbose and more powerful ways of using LML. See other LML project templates.


        stage.setDebugAll(true);


        files = (SelectBox<String>) parser.getActorsMappedByIds().get("files");


        files.setItems(listMaps());

        screenManager.addInputProcessor(stage);
    }

    public Array<String> listMaps() {
        Array<String> results = new Array<String>();
        File[] files = new File("./maps/").listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    results.add(file.getName());
                }
            }
        }
        return results;
    }

    private void loadMap() {
        parser.fillStage(stage, "editor.lml");
        JsonReader jsonReader = new JsonReader();
        VisTextField name = (VisTextField) parser.getActorsMappedByIds().get("name");
        JsonValue parse = jsonReader.parse(Gdx.files.internal("maps/" + files.getSelected()));
        VerticalFlowGroup map = (VerticalFlowGroup) parser.getActorsMappedByIds().get("paintmap");


        int[][] mapArray = new int[32][9];
        for (int i = 0; i < parse.size; i++) {
            mapArray[i] = parse.get(i).asIntArray();
        }


        int x = 0;
        int y = 0;
        for (Actor child : map.getChildren()) {
            System.out.println(x + ":" + y);
            Container<VisImage> container = (Container<VisImage>) child;
            VisImage image = container.getActor();
            if (mapArray[x][y] == ROBOT) {
                image.setColor(Color.RED);
            } else if (mapArray[x][y] == BUSH) {
                image.setColor(Color.GREEN);
            } else if (mapArray[x][y] == MOUNTAIN) {
                image.setColor(Color.BLUE);
            } else if (mapArray[x][y] == TREE) {
                image.setColor(Color.YELLOW);
            }
            y++;

            if (y > 8) {
                y = 0;
                x++;
            }
        }

        screenManager.switchScreens("gameScreen");

    }

    private void saveMap() {

        VerticalFlowGroup map = (VerticalFlowGroup) parser.getActorsMappedByIds().get("paintmap");

        int[][] mapArray = new int[32][9];

        int x = 0;
        int y = 0;
        for (Actor child : map.getChildren()) {
            System.out.println(x + ":" + y);
            Container<VisImage> container = (Container<VisImage>) child;
            VisImage image = container.getActor();
            if (image.getColor().equals(Color.RED)) {
                mapArray[x][y] = ROBOT;
            } else if (image.getColor().equals(Color.GREEN)) {
                mapArray[x][y] = BUSH;
            } else if (image.getColor().equals(Color.BLUE)) {
                mapArray[x][y] = MOUNTAIN;
            } else if (image.getColor().equals(Color.YELLOW)) {
                mapArray[x][y] = TREE;
            }
            y++;

            if (y > 8) {
                y = 0;
                x++;
            }
        }


        VisTextField name = (VisTextField) parser.getActorsMappedByIds().get("name");

        try {
            JsonWriter jsonWriter = new JsonWriter(new FileWriter("maps/" + name.getText().toLowerCase() + ".json"));
            jsonWriter.write(Arrays.deepToString(mapArray));
            jsonWriter.flush();
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(Arrays.deepToString(mapArray));
    }

    @LmlAction("drag")
    public Draggable.DragListener getDragListener() {
        return new Draggable.DragListener() {
            @Override
            public boolean onStart(Draggable draggable, Actor actor, float stageX, float stageY) {
                return false;
            }

            @Override
            public void onDrag(Draggable draggable, Actor actor, float stageX, float stageY) {

            }

            @Override
            public boolean onEnd(Draggable draggable, Actor actor, float stageX, float stageY) {
                return false;
            }
        };
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {
        screenManager.addInputProcessor(stage);
    }

    @Override
    public void render(float deltaTime) {
        // AbstractApplicationListener automatically clears the screen with black color.


        stage.act(deltaTime);
        stage.draw();

    }

    @Override
    public void dispose() {
        // Null-safe disposing utility method:
        Disposables.disposeOf(stage, skin);
    }
}