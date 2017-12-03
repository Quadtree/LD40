package info.quadtree.ld40.info.quadtree.ld40.actor;

public class Pipe extends Cargo {
    public Pipe(float x, float y) {
        super("pipe1", x, y, 300 / 30f, 16f / 30f);
    }

    @Override
    public float getScore() {
        return 40;
    }
}
