package info.quadtree.ld40.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.constants.NumberConstantsImpl;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.Util;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(1024, 768);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                Util.defaultFloatFormatter = new Util.FloatFormatter() {
                        @Override
                        public String formatFloat(float f) {
                                NumberFormat fmt = NumberFormat.getFormat("#0.0");
                                return fmt.format(f);
                        }
                };
                return new LD40();

        }
}