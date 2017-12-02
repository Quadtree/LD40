package info.quadtree.ld40.info.quadtree.ld40.actor;

public class LongCargo extends Cargo {
    public LongCargo(float x, float y) {
        super("panel1", x, y, 3, 0.5f);
    }

    @Override
    public float getScore() {
        return 40;
    }
}
