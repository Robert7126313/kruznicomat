package shapes;

import java.awt.*;

public class Circle {
    private int x;
    private int y;
    private final int diameter;
    private Color color;
    public Circle(int x, int y, int diameter, Color color){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.color = color;
    }

    // --- gettery ---
    public int getX() { return x; }
    public int getY() { return y; }
    public int getDiameter() { return diameter; }
    public Color getColor() { return color; }

    public void setColor(Color color) {
        if (color != null) this.color = color;
    }


}
