package se.snrn.gameoffjam;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import se.snrn.gameoffjam.components.ControlComponent;

public class InputManager extends InputAdapter {

    private final int left = Input.Keys.LEFT;
    private final int right = Input.Keys.RIGHT;
    private final int attack = Input.Keys.CONTROL_LEFT;
    private ControlComponent controlComponent;

    public InputManager(ControlComponent controlComponent) {

        this.controlComponent = controlComponent;

    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == left) {
            controlComponent.setLeft(true);
        }
        if (keycode == right) {
            controlComponent.setRight(true);
        }
        if (keycode == attack) {
            controlComponent.setAttack(true);
        }
        return false;

    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == left) {
            controlComponent.setLeft(false);
        }
        if (keycode == right) {
            controlComponent.setRight(false);
        }
        if (keycode == attack) {
            controlComponent.setAttack(false);
        }
        return false;
    }
}
