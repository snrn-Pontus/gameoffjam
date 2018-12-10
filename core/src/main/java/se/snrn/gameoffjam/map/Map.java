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
import se.snrn.gameoffjam.BackgroundFactory;
import se.snrn.gameoffjam.EnemyFactory;
import se.snrn.gameoffjam.components.MapSegmentComponent;

import java.util.Arrays;

import static se.snrn.gameoffjam.GameOffJam.*;
import static se.snrn.gameoffjam.ScreenManager.*;


public class Map {
    public static final int ROBOT = 1;
    public static final int MOUNTAIN = 32;
    public static final int BUSH = 33;
    private static JsonReader jsonReader = new JsonReader();

    public Map(String path, Engine engine, float x, float y) {
        JsonValue parsedMap = load(path);
        int[][] map = new int[32][9];
        for (int i = 0; i < parsedMap.size; i++) {
            map[i] = parsedMap.get(i).asIntArray();
        }
        System.out.println(Arrays.toString(map[0]));

        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[j].length; i++) {
                float x1 = x + j * 80 + 40;
                float y1 = y + i * 80 + 40;
                switch (map[j][i]) {
                    case ROBOT: {
                        engine.addEntity(EnemyFactory.create(engine, x1, y1));
                        break;
                    }
                    case MOUNTAIN: {
                        BackgroundFactory.create(engine, x1, y1, 0.5f, 2, new Texture("images/mountain.png"));
                        break;
                    }
                    case BUSH: {
                        BackgroundFactory.create(engine, x1, y1, 1f, 1, new Texture("images/bush.png"));
                        break;
                    }
                }
            }
        }
        Entity background = engine.createEntity();
        background.add(MapSegmentComponent.create(engine));

        background.add(TransformComponent.create(engine).setPosition(x + (SEGMENT_WIDTH / 2f), y).setScale(PPM, PPM));
        background.add(VelocityComponent.create(engine).setSpeed(-BACKGROUND_SPEED, 0));
        background.add(BoundsComponent.create(engine).setBounds(x + (SEGMENT_WIDTH / 2f), y, SEGMENT_WIDTH, 32));

        engine.addEntity(background);
    }

    public static JsonValue load(String path) {
        return jsonReader.parse(Gdx.files.internal(path));
    }


}
