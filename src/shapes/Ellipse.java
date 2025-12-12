package shapes;

import java.awt.*;

public class Ellipse {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Color color;

    public Ellipse(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    // --- gettery ---
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Color getColor() { return color; }

}
