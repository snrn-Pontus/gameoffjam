package se.snrn.gameoffjam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Laser Class
 *
 * @author Trenton Shaffer
 */
public class Laser {

    public Vector2 position = new Vector2();
    public float distance;
    public Color color = new Color(Color.RED);
    public Color rayColor = new Color(Color.WHITE);
    public float degrees;
    public Sprite begin1, begin2, mid1, mid2, end1, end2;

    public Laser() {

        Texture texLaserS1 = new Texture(Gdx.files.internal("laser/beamstart1.png"));
        Texture texLaserS2 = new Texture(Gdx.files.internal("laser/beamstart2.png"));
        Texture texLaserM1 = new Texture(Gdx.files.internal("laser/beammid1.png"));
        Texture texLaserM2 = new Texture(Gdx.files.internal("laser/beammid2.png"));
        Texture texLaserE1 = new Texture(Gdx.files.internal("laser/beamend1.png"));
        Texture texLaserE2 = new Texture(Gdx.files.internal("laser/beamend2.png"));


        this.begin1 = new Sprite(texLaserS1);
        this.begin2 = new Sprite(texLaserS2);
        this.mid1 = new Sprite(texLaserM1);
        this.mid2 = new Sprite(texLaserM2);
        this.end1 = new Sprite(texLaserE1);
        this.end2 = new Sprite(texLaserE2);
        this.position.set(50, 50);
        this.distance = 400;
    }

    public void render(SpriteBatch batch) {
        begin1.setColor(color);
        begin2.setColor(rayColor);
        mid1.setColor(color);
        mid2.setColor(rayColor);
        end1.setColor(color);
        end2.setColor(rayColor);

        mid1.setSize(mid1.getWidth(), distance);
        mid2.setSize(mid1.getWidth(), distance);

        begin1.setPosition(position.x, position.y);
        begin2.setPosition(position.x, position.y);

        mid1.setPosition(begin1.getX(), begin1.getY() + begin1.getHeight());
        mid2.setPosition(begin1.getX(), begin1.getY() + begin1.getHeight());

        end1.setPosition(begin1.getX(), begin1.getY() + begin1.getHeight() + mid1.getHeight());
        end2.setPosition(begin1.getX(), begin1.getY() + begin1.getHeight() + mid1.getHeight());

        begin1.setOrigin(begin1.getWidth() / 2, 0);
        begin2.setOrigin(begin1.getWidth() / 2, 0);


        mid1.setOrigin(mid1.getWidth() / 2, -begin1.getHeight());
        mid2.setOrigin(mid2.getWidth() / 2, -begin1.getHeight());
        end1.setOrigin(mid1.getWidth() / 2, -begin1.getHeight() - mid1.getHeight());
        end2.setOrigin(mid2.getWidth() / 2, -begin1.getHeight() - mid2.getHeight());


        begin1.setRotation(degrees);
        begin2.setRotation(degrees);
        mid1.setRotation(degrees);
        mid2.setRotation(degrees);
        end1.setRotation(degrees);
        end2.setRotation(degrees);

        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

        begin1.draw(batch);
        begin2.draw(batch);


        mid1.draw(batch);

        mid2.draw(batch);

        end1.draw(batch);
        end2.draw(batch);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


    }
}
