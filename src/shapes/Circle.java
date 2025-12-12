package shapes;

import java.awt.*;

public class Circle {
    private final int x;
    private final int y;
    private final int diameter;
    private final Color color;

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


}
