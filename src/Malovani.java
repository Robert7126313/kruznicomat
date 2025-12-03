import javax.swing.*;
import java.awt.*;

public class Malovani extends JFrame {
    protected final JTextField diameterField;
    private final DrawPanel drawPanel;
    private final JButton clearButton;

    public Malovani(){
        super("Kružnicomat - klikni a kresli");
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Průměr v px: "));
        diameterField = new JTextField("50",5);
        topPanel.add(diameterField);

        clearButton = new JButton("Vymazat");
        topPanel.add(clearButton);

        drawPanel = new DrawPanel();
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(drawPanel,BorderLayout.CENTER);

        clearButton.addActionListener(e -> drawPanel.clear());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
