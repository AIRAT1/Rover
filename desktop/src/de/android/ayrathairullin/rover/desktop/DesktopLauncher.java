package de.android.ayrathairullin.rover.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.android.ayrathairullin.rover.GameCallback;
import de.android.ayrathairullin.rover.Rover;

public class DesktopLauncher {
	public static void main(String[] args) {
		new DesktopLauncher();
	}

	public DesktopLauncher() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Rover(callback), config);
	}

	private GameCallback callback = new GameCallback() {
		@Override
		public void sendMessage(int message) {
			System.out.println("DesktopLauncher sendMessage: " + message);
		}
	};
}
