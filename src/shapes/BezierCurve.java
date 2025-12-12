package shapes;

import java.awt.*;

public class BezierCurve {
    private final Point p0;
    private final Point p1;
    private final Point p2;
    private final Point p3;

    public BezierCurve(Point p0, Point p1, Point p2, Point p3, Color color) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    //vzhledem k nemutabilitě třídy Point vracíme kopie

    public Point getP0() { return new Point(p0); }
    public Point getP1() { return new Point(p1); }
    public Point getP2() { return new Point(p2); }
    public Point getP3() { return new Point(p3); }

}
