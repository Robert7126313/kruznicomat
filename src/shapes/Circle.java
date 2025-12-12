package shapes;

public class Circle {
    private final int x;
    private final int y;
    private final int diameter;

    public Circle(int x, int y, int diameter){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
    }

    // --- gettery ---
    public int getX() { return x; }
    public int getY() { return y; }
    public int getDiameter() { return diameter; }


}
