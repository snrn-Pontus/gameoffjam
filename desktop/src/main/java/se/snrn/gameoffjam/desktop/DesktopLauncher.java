package se.snrn.gameoffjam.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import se.snrn.gameoffjam.GameOffJam;
import se.snrn.gameoffjam.MapBuilder;
import se.snrn.gameoffjam.ScreenManager;

/**
 * Launches the desktop (LWJGL) application.
 */
public class DesktopLauncher {
    public static void main(String[] args) {
        createApplication();
    }

    private static LwjglApplication createApplication() {
        //return new LwjglApplication(new GameOffJam(), getDefaultConfiguration());
        return new LwjglApplication(new ScreenManager(), getDefaultConfiguration());
    }

    private static LwjglApplicationConfiguration getDefaultConfiguration() {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.title = "GameOffJam";
        configuration.width = ScreenManager.WIDTH;
        configuration.height = ScreenManager.HEIGHT;
        //for (int size : new int[] { 128, 64, 32, 16 }) {
        //    configuration.addIcon("libgdx" + size + ".png", FileType.Internal);
        //}
        return configuration;
    }
}