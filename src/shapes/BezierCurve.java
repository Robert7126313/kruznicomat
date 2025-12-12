package shapes;

import java.awt.Point;

public class BezierCurve {
    public final Point p0;
    public final Point p1;
    public final Point p2;
    public final Point p3;

    public BezierCurve(Point p0, Point p1, Point p2, Point p3) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
}
