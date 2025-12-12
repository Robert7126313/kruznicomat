package shapes;

import java.awt.*;

public class Square {
    private final int x;
    private final int y;
    private final int size;

    public Square(int x, int y, int size, Color color){
        this.x = x;
        this.y = y;
        this.size = size;
    }

    // --- gettery ---
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
}
