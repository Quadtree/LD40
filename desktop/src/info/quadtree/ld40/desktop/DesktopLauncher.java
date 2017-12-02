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
		};

		TexturePacker.processIfModified("../../raw_assets/", ".", "default");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 768;
		new LwjglApplication(new LD40(), config);
	}
}
