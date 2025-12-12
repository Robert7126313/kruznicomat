import shapes.BezierCurve;
import shapes.Circle;
import shapes.Ellipse;
import shapes.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

// panel který bude uchovávat nakreslené kružnice
public class DrawPanel extends JPanel {
    //seznam všech kružnic, ellips, čtverců a Bézierových křivek
    private final List<Circle> circles = new ArrayList<>();
    private final List<Ellipse> ellipses = new ArrayList<>();
    private final List<Square> squares = new ArrayList<>();
    private final List<BezierCurve> beziers = new ArrayList<>();

    //změna barvy obrazce
    private Color currentColor = Color.MAGENTA;

    public void setCurrentColor(Color color) {
        if (color != null) {
            this.currentColor = color;
        }
    }


    // dočasně rozpracované body (0–4 body)
    private final List<Point> bezierPoints = new ArrayList<>();

    private Tool currentTool = Tool.CIRCLE; //defaultní nástroj/geometrický obrazec

    public void setTool(Tool tool) {
        this.currentTool = tool;
    }

    public DrawPanel(){
        setBackground(Color.WHITE);
        setOpaque(true);

//        ellipses.add(new Ellipse(100, 100, 120, 60));


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { //přidána přepínací logika
                switch (currentTool) {
                    case CIRCLE -> addCircle(e.getX(), e.getY());
                    case ELLIPSE -> addEllipse(e.getX(), e.getY());
                    case SQUARE -> addSquare(e.getX(), e.getY());
                    case BEZIER -> addBezierPoint(e.getX(), e.getY());
                }
//                addCircle(e.getX(),e.getY());
            }
        });
    }

    /**
     * Přidá kružnici na zadanou pozici. Hodnota průměru bude načtena z okna
     * @param x souřadnice X kliknutí
     * @param y souřadnice Y kliknutí
     */
    private void addCircle(int x, int y) {
        SwingUtilities.invokeLater(() ->{
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if(topFrame instanceof Malovani owner){
                String text = owner.diameterField.getText().trim();
                int diameter = Integer.parseInt(text);

                circles.add(new Circle(
                        x - diameter / 2,
                        y-diameter / 2,
                        diameter,
                        currentColor
                ));

                repaint();
            }
        });
    }

    private void addEllipse(int x, int y) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame instanceof Malovani owner) {

            String text = owner.diameterField.getText().trim();
            int diameter = Integer.parseInt(text);

            int width = diameter;
            int height = diameter / 2;

            ellipses.add(new Ellipse(
                    x - width / 2,
                    y - height / 2,
                    width,
                    height,
                    currentColor
            ));

            repaint();
        }
    }

    private void addSquare(int x, int y) {
        SwingUtilities.invokeLater(() -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof Malovani owner) {
                String text = owner.diameterField.getText().trim();
                int size = Integer.parseInt(text);

                squares.add(new Square(
                        x - size / 2,
                        y - size / 2,
                        size,
                        currentColor
                ));
                repaint();
            }
        });
    }

    private void addBezierPoint(int x, int y) {
        bezierPoints.add(new Point(x, y));

        if (bezierPoints.size() == 4) {
            beziers.add(new BezierCurve(
                    bezierPoints.get(0),
                    bezierPoints.get(1),
                    bezierPoints.get(2),
                    bezierPoints.get(3),
                    currentColor
            ));
            bezierPoints.clear(); // začneme novou křivku
        }

        repaint();
    }



    public void clear() {
        circles.clear();
        ellipses.clear();
        squares.clear();
        beziers.clear();
        bezierPoints.clear();
        repaint();
    }

    private Point bezierPoint(Point p0, Point p1, Point p2, Point p3, double t) {
        double u = 1.0 - t;

        double x =
                (u*u*u) * p0.x +
                        (3*u*u*t) * p1.x +
                        (3*u*t*t) * p2.x +
                        (t*t*t) * p3.x;

        double y =
                (u*u*u) * p0.y +
                        (3*u*u*t) * p1.y +
                        (3*u*t*t) * p2.y +
                        (t*t*t) * p3.y;

        return new Point((int)Math.round(x), (int)Math.round(y));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g.create();
        graphics2D.setColor(currentColor);

        //kreslení obrazců

        for (Circle c: circles) {
            graphics2D.drawOval(c.getX(), c.getY(), c.getDiameter(), c.getDiameter());
        }

        for (Ellipse e : ellipses) {
            graphics2D.drawOval(e.getX(), e.getY(), e.getWidth(), e.getHeight());
        }

        for (Square s : squares) {
            graphics2D.drawRect(s.getX(), s.getY(), s.getSize(), s.getSize());
        }

        //BEZIEROVY KŘIVKY---------------------------------

        for (Point p : bezierPoints) {
            graphics2D.fillOval(p.x - 3, p.y - 3, 6, 6);
        }

        for (BezierCurve b : beziers) {
            Point prev = b.getP0();
            int steps = 100;

            for (int i = 1; i <= steps; i++) {
                double t = i / (double) steps;
                Point cur = bezierPoint(b.getP0(), b.getP1(), b.getP2(), b.getP3(), t);
                graphics2D.drawLine(prev.x, prev.y, cur.x, cur.y);
                prev = cur;
            }
        }
        graphics2D.setColor(Color.LIGHT_GRAY);
        for (BezierCurve b : beziers) {
            graphics2D.drawLine(b.getP0().x, b.getP0().y, b.getP1().x, b.getP1().y); //nahrazeno gettery
            graphics2D.drawLine(b.getP1().x, b.getP1().y, b.getP2().x, b.getP2().y);
            graphics2D.drawLine(b.getP2().x, b.getP2().y, b.getP3().x, b.getP3().y);
        }
//--------------------------------------------------



        graphics2D.dispose();
    }
}
