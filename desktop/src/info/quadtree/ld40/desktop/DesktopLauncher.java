package info.quadtree.ld40.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.Util;

import java.util.Date;
import java.util.logging.FileHandler;

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

			@Override
			public double getPerfTime() {
				return System.nanoTime() / 1000.0 / 1000.0 / 1000.0;
			}

			@Override
			public void takeScreenshot() {
				byte[] b = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
				Pixmap pm = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
				BufferUtils.copy(b, 0, pm.getPixels(), b.length);
				Gdx.files.external("info.quadtree.ld40").mkdirs();
				FileHandle xf = Gdx.files.external("info.quadtree.ld40/screenshot_" + System.currentTimeMillis() + ".png");
				PixmapIO.writePNG(xf, pm);
				pm.dispose();
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
