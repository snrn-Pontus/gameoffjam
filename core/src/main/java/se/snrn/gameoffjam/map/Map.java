package se.snrn.gameoffjam.map;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import se.snrn.gameoffjam.components.MapSegmentComponent;
import se.snrn.gameoffjam.factories.BackgroundFactory;
import se.snrn.gameoffjam.factories.EnemyFactory;

import static se.snrn.gameoffjam.ScreenManager.*;


public class Map {
    public static final int ROBOT_ENEMY = 1;
    public static final int ROCKET_ENEMY = 2;
    public static final int LASER_ENEMY = 3;
    public static final int MOUNTAIN = 32;
    public static final int BUSH = 33;
    public static final int TREE = 34;
    public static final int CLOUD = 35;
    public static final int HILL = 36;
    private static JsonReader jsonReader = new JsonReader();

    public Map(String path, Engine engine, float x, float y) {
        JsonValue parsedMap = load(path);
        int[][] map = new int[32][9];
        for (int i = 0; i < parsedMap.size; i++) {
            map[i] = parsedMap.get(i).asIntArray();
        }

        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[j].length; i++) {
                float x1 = x + j * 80 + 40;
                float y1 = y + i * 80 + 40;
                createMapItem(engine, map[j][i], x1, y1);
            }
        }
        Entity background = engine.createEntity();
        background.add(MapSegmentComponent.create(engine));

        background.add(TransformComponent.create(engine).setPosition(x + (SEGMENT_WIDTH / 2f), y).setScale(PPM, PPM));
        background.add(VelocityComponent.create(engine).setSpeed(-BACKGROUND_SPEED, 0));
        background.add(BoundsComponent.create(engine).setBounds(x + (SEGMENT_WIDTH / 2f), y, SEGMENT_WIDTH, 32));

        engine.addEntity(background);
    }


    private void createMapItem(Engine engine, int i, float x1, float y1) {
        switch (i) {
            case ROBOT_ENEMY: {
                EnemyFactory.create(engine, x1, y1, i);
                break;
            }
            case ROCKET_ENEMY: {
                EnemyFactory.create(engine, x1, y1, i);
                break;
            }
            case LASER_ENEMY: {
                EnemyFactory.create(engine, x1, y1, i);
                break;
            }
            case MOUNTAIN: {
                BackgroundFactory.create(engine, x1, y1, 10, new Texture("images/mountain.png"));
                break;
            }
            case BUSH: {
                BackgroundFactory.create(engine, x1, y1, 2, new Texture("images/bush.png"));
                break;
            }
            case TREE: {
                BackgroundFactory.create(engine, x1, y1, 4, new Texture("images/trees/tree1.png"));
                break;
            }
            case CLOUD: {
                BackgroundFactory.create(engine, x1, y1, 7, new Texture("images/clouds/cloud1.png"));
                break;
            }
            case HILL: {
                BackgroundFactory.create(engine, x1, y1, 5, new Texture("images/hills/hill1.png"));
                break;
            }
        }
    }

    public static JsonValue load(String path) {
        return jsonReader.parse(Gdx.files.internal(path));
    }


}
