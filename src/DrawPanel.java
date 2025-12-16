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

    private Point mousePos = null;   // poslední pozice myši v panelu
    private JLabel coordsLabel;


    //------------------změna barvy obrazce----------------
    private Color currentColor = Color.BLACK;

    public void setCurrentColor(Color color) {
        if (color != null) {
            this.currentColor = color;
        }
    }

    public Color getCurrentColor() {
        return currentColor;
    }
    //----------------------------------------------

    //-------- dočasně rozpracované body (0–4 body)----------------
    private final List<Point> bezierPoints = new ArrayList<>();

    private Tool currentTool = Tool.CIRCLE; //defaultní nástroj/geometrický obrazec

    public void setTool(Tool tool) {
        this.currentTool = tool;
    }

    public DrawPanel(){
        setBackground(Color.WHITE);
        setOpaque(true);

//        ellipses.add(new Ellipse(100, 100, 120, 60)); //zkouška elipsy


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

        // sledování pohybu myši-------------
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePos = e.getPoint();

                if (coordsLabel != null) {
                    coordsLabel.setText("x: " + mousePos.x + ", y: " + mousePos.y);
                }

                repaint();
            }
        //------------------------------
        // přetažení myši-------------
            @Override
            public void mouseDragged(MouseEvent e) {
                mousePos = e.getPoint();

                if (coordsLabel != null) {
                    coordsLabel.setText("x: " + mousePos.x + ", y: " + mousePos.y);
                }


                repaint();
            }
        });
        //------------------------------
        // detekce opuštění panelu myší-------------
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                mousePos = null;

                if (coordsLabel != null) {
                    coordsLabel.setText("x: -, y: -");
                }


                repaint();
            }
        });
        //------------------------------
    }

    public void setCoordsLabel(JLabel label) {
        this.coordsLabel = label;
    }

    //----- metody pro přidání jednotlivých obrazců -----
    /**
     * Přidá kružnici na zadanou pozici. Hodnota průměru bude načtena z okna
     * @param x souřadnice X kliknutí
     * @param y souřadnice Y kliknutí
     */

    // Přidá kružnici na zadanou pozici. Hodnota velikosti bude načtena z okna
    private void addCircle(int x, int y) {
        SwingUtilities.invokeLater(() ->{
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if(topFrame instanceof Malovani owner){
//                String text = owner.diameterField.getText().trim();
//                int diameter = Integer.parseInt(text);

                int diameter = (Integer) owner.sizeSpinner.getValue();



                circles.add(new Circle(
                        x - diameter / 2,
                        y-diameter / 2,
                        diameter,
                        currentColor
                ));

                repaint(); // překreslí panel
            }
        });
    }

    // Přidá elipsu na zadanou pozici. Hodnota velikosti bude načtena z okna
    private void addEllipse(int x, int y) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame instanceof Malovani owner) {

//            String text = owner.diameterField.getText().trim();
//            int diameter = Integer.parseInt(text);

            int diameter = (Integer) owner.sizeSpinner.getValue();

            int width = diameter;
            int height = diameter / 2;

            ellipses.add(new Ellipse(
                    x - width / 2,
                    y - height / 2,
                    width,
                    height,
                    currentColor
            ));

            repaint(); // překreslí panel
        }
    }

    // Přidá čtverec na zadanou pozici. Hodnota velikosti bude načtena z okna
    private void addSquare(int x, int y) {
        SwingUtilities.invokeLater(() -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof Malovani owner) {
//                String text = owner.diameterField.getText().trim();
//                int size = Integer.parseInt(text); //nahrazeno spinnerem
                int size = (Integer) owner.sizeSpinner.getValue(); // velikost čtverce


                squares.add(new Square(
                        x - size / 2,
                        y - size / 2,
                        size,
                        currentColor
                ));
                repaint(); // překreslí panel
            }
        });
    }

    // Přidá bod pro Bézierovu křivku
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

    // Výpočet bodu na Bézierově křivce pro parametr t v rozsahu [0, 1]
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

//--------------------------------------------------
//--------------------MAZÁNÍ-----------------------

    public void clear() {
        circles.clear();
        ellipses.clear();
        squares.clear();
        beziers.clear();
        bezierPoints.clear();
        repaint();
    }
//--------------------------------------------------

    @Override
    protected void paintComponent(Graphics g) { //překreslovací logika, "vykreslení stavu"
        super.paintComponent(g);

//--------------------------------------------------
        // ---- kreslení mřížky ----
        if (showGrid) {
            int step = 10; // zatím fixně 10 px

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(0, 0, 0, 30)); // jemná průhledná

            int w = getWidth();
            int h = getHeight();

            for (int x = 0; x <= w; x += step) {
                g2.drawLine(x, 0, x, h);
            }
            for (int y = 0; y <= h; y += step) {
                g2.drawLine(0, y, w, y);
            }

            g2.dispose();
        }
// --------------------------------------------------

        Graphics2D graphics2D = (Graphics2D) g.create();
        //graphics2D.setColor(currentColor); //nastavení barvy pro kreslení - zkouška

        //KRUŽNICE---------------------------------
        for (Circle c: circles) {
            graphics2D.setColor(c.getColor());
            graphics2D.drawOval(c.getX(), c.getY(), c.getDiameter(), c.getDiameter());
        }

        //ELIPSA---------------------------------
        for (Ellipse e : ellipses) {
            graphics2D.setColor(e.getColor());
            graphics2D.drawOval(e.getX(), e.getY(), e.getWidth(), e.getHeight());
        }


        //ČTVEREC---------------------------------
        for (Square s : squares) {
            graphics2D.setColor(s.getColor());
            graphics2D.drawRect(s.getX(), s.getY(), s.getSize(), s.getSize());
        }

        //BEZIEROVY KŘIVKY---------------------------------
        for (Point p : bezierPoints) {
            graphics2D.setColor(Color.RED);
            graphics2D.fillOval(p.x - 3, p.y - 3, 6, 6);
        }

        for (BezierCurve b : beziers) {
            graphics2D.setColor(b.getColor());
            Point prev = b.getP0();
            int steps = 100;

            for (int i = 1; i <= steps; i++) {
                double t = i / (double) steps;
                Point cur = bezierPoint(b.getP0(), b.getP1(), b.getP2(), b.getP3(), t);
                graphics2D.drawLine(prev.x, prev.y, cur.x, cur.y);
                prev = cur;
            }
        }
        // pomocné čáry mezi řídicími body Bézierových křivek---------------------------------
        graphics2D.setColor(Color.LIGHT_GRAY);
        for (BezierCurve b : beziers) {
            graphics2D.drawLine(b.getP0().x, b.getP0().y, b.getP1().x, b.getP1().y); //nahrazeno gettery
            graphics2D.drawLine(b.getP1().x, b.getP1().y, b.getP2().x, b.getP2().y);
            graphics2D.drawLine(b.getP2().x, b.getP2().y, b.getP3().x, b.getP3().y);
        }
        //--------------------------------------------------



        // ---- náhled objektu pod myší ----
        if (mousePos != null) {
            int d = readDiameterSafe();

            Color previewColor = new Color(
                    currentColor.getRed(),
                    currentColor.getGreen(),
                    currentColor.getBlue(),
                    120
            );

            Stroke oldStroke = graphics2D.getStroke();
            graphics2D.setColor(previewColor);
            graphics2D.setStroke(new BasicStroke(
                    2f,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND,
                    10f,
                    new float[]{8f, 6f},
                    0f
            ));

            int x = mousePos.x;
            int y = mousePos.y;

            switch (currentTool) {
                case CIRCLE -> {
                    int r = d / 2;
                    graphics2D.drawOval(x - r, y - r, d, d);
                }
                case ELLIPSE -> {
                    int w = d;
                    int h = d / 2;
                    graphics2D.drawOval(x - w / 2, y - h / 2, w, h);
                }
                case SQUARE -> {
                    int s = d;
                    graphics2D.drawRect(x - s / 2, y - s / 2, s, s);
                }
                default -> {}
            }

            graphics2D.setStroke(oldStroke);
        }
// --------------------------------------------------

        graphics2D.dispose();
    }


// bezpečné načtení průměru z textového pole-----------------
    private int readDiameterSafe() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame instanceof Malovani owner) {
            try {
                return (Integer) owner.sizeSpinner.getValue(); //Math.max(1, Integer.parseInt(owner.diameterField.getText().trim())); //bylo nahrazeno spinnerem
            } catch (NumberFormatException e) {
                return 50;
            }
        }
        return 50;
    }
//--------------------------------------------------
//--------------------ZOBRAZENÍ MŘÍŽKY-----------------------
    private boolean showGrid = false;

    public void setShowGrid(boolean show) {
        this.showGrid = show;
        repaint();
    }

//--------------------------------------------------


}
