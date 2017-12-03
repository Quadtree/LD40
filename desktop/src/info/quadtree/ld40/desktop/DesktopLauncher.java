package info.quadtree.ld40.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.Util;

public class DesktopLauncher {
	public static void main (String[] arg) {

		Util.defaultFloatFormatter = new Util.FloatFormatter() {
			@Override
			public String formatFloat(float f) {
				return String.format("%.1f", f);
			}

			@Override
			public void log(String msg) {
				System.err.println("UL: " + msg);
			}
		};

		TexturePacker.Settings s = new TexturePacker.Settings();
		s.maxHeight = 2048;
		s.maxWidth = 2048;
		TexturePacker.processIfModified(s, "../../raw_assets/", ".", "default");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 768;
		new LwjglApplication(new LD40(), config);
	}
}
