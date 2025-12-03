import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

// panel který bude uchovávat nakreslené kružnice
public class DrawPanel extends JPanel {
    //seznam všech kružnic
    private final List<Circle> circles = new ArrayList<>();

    public DrawPanel(){
        setBackground(Color.WHITE);
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addCircle(e.getX(),e.getY());
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
            if(topFrame instanceof Malovani){
                Malovani owner = (Malovani) topFrame;
                String text = owner.diameterField.getText().trim();
                int diameter = Integer.parseInt(text);
                circles.add(new Circle(x - diameter / 2, y-diameter / 2, diameter));
                repaint();
            }
        });
    }

    public void clear() {
        circles.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g.create();
        graphics2D.setColor(Color.BLUE);
        for (Circle c: circles) {
            graphics2D.drawOval(c.x,c.y,c.diameter,c.diameter);
        }
        graphics2D.dispose();
    }
}
