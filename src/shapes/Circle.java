package shapes;

import java.awt.*;

public class Circle {
    private final int x;
    private final int y;
    private final int diameter;
    private final Color color;
    private final boolean filled; // přidáno pro určení, zda je kruh vyplněný

    public Circle(int x, int y, int diameter, Color color, boolean filled){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.color = color;
        this.filled = filled;
    }

    // --- gettery ---
    public int getX() { return x; }
    public int getY() { return y; }
    public int getDiameter() { return diameter; }
    public Color getColor() { return color; }
    public boolean isFilled() { return filled; }


}
