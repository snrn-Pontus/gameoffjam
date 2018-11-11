package se.snrn.gameoffjam.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import se.snrn.gameoffjam.components.PlayerComponent;

import java.text.DecimalFormat;

public class UISystem extends IteratingSystem {

    private final DecimalFormat formatter;
    private ComponentMapper<TransformComponent> tm;
    private Label distance;

    public UISystem(Label distance) {
        super(Family.all(TransformComponent.class, PlayerComponent.class).get());
        this.distance = distance;
        tm = ComponentMapper.getFor(TransformComponent.class);
         formatter = new DecimalFormat("#0.00");

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent transformComponent = tm.get(entity);


        if (distance != null) {
            distance.setText(formatter.format(transformComponent.position.x / 10f) + "");
        }

    }
}
