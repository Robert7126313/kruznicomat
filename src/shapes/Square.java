package shapes;

import java.awt.*;

public class Square {
    private final int x;
    private final int y;
    private final int size;
    private final Color color;
    private final boolean filled;

    public Square(int x, int y, int size, Color color, boolean filled){
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.filled = filled;
    }

    // --- gettery ---
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
    public Color getColor() { return color; }
    public boolean isFilled() { return filled; }
}
