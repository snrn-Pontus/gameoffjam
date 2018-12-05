package se.snrn.gameoffjam.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import se.snrn.gameoffjam.GameOffJam;

/** Launches the desktop (LWJGL) application. */
public class DesktopLauncher {
    public static void main(String[] args) {
        createApplication();
    }

    private static LwjglApplication createApplication() {
        return new LwjglApplication(new GameOffJam(), getDefaultConfiguration());
    }

    private static LwjglApplicationConfiguration getDefaultConfiguration() {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.title = "GameOffJam";
        configuration.width = GameOffJam.WIDTH;
        configuration.height = GameOffJam.HEIGHT;
        //for (int size : new int[] { 128, 64, 32, 16 }) {
        //    configuration.addIcon("libgdx" + size + ".png", FileType.Internal);
        //}
        return configuration;
    }
}