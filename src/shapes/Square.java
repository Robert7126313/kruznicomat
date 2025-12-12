package shapes;

public class Square {
    private final int x;
    private final int y;
    private final int size;

    public Square(int x, int y, int size){
        this.x = x;
        this.y = y;
        this.size = size;
    }

    // --- gettery ---
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
}
