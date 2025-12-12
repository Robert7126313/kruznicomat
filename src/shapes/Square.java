package shapes;

import java.awt.*;

public class Square {
    private final int x;
    private final int y;
    private final int size;
    private final Color color;

    public Square(int x, int y, int size, Color color){
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    // --- gettery ---
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
    public Color getColor() { return color; }
}
