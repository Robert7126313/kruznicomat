package shapes;

import java.awt.Color;
import java.awt.Point;

public class Ellipse {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;

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

    public void setColor(Color color) {
        if (color != null) this.color = color;
    }

}
