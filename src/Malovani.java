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

//--------------------------------------------------------------------
        //PŘIDÁNÍ TLAČÍTEK ZMĚNY VYKRESLEOVÁNÍ GEOMETRICKÝCH OBRAZCŮ
        JRadioButton circleTool = new JRadioButton("Kružnice", true);
        JRadioButton ellipseTool = new JRadioButton("Elipsa");
        JRadioButton squareTool = new JRadioButton("Čtverec");
        JRadioButton bezierTool = new JRadioButton("Bézierova křivka");

        ButtonGroup tools = new ButtonGroup();
        tools.add(circleTool);
        tools.add(ellipseTool);
        tools.add(squareTool);
        tools.add(bezierTool);

        topPanel.add(circleTool);
        topPanel.add(ellipseTool);
        topPanel.add(squareTool);
        topPanel.add(bezierTool);

        circleTool.addActionListener(e -> drawPanel.setTool(Tool.CIRCLE));
        ellipseTool.addActionListener(e -> drawPanel.setTool(Tool.ELLIPSE));
        squareTool.addActionListener(e -> drawPanel.setTool(Tool.SQUARE));
        bezierTool.addActionListener(e -> drawPanel.setTool(Tool.BEZIER));
//--------------------------------------------------------------------
        // Přidání změny barvy
        JButton colorButton = new JButton("Změnit barvu");
        topPanel.add(colorButton);

        // Náhled aktuální barvy
        JPanel colorPreview = new JPanel();
        colorPreview.setPreferredSize(new Dimension(18, 18));
        colorPreview.setBackground(Color.BLACK);
        colorPreview.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        topPanel.add(colorPreview);
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(
                    this,
                    "Vyber barvu",
                    drawPanel.getCurrentColor()
            );
            if (newColor != null) {
                drawPanel.setCurrentColor(newColor);
                colorPreview.setBackground(newColor);
                colorPreview.repaint();
            }
        });
//--------------------------------------------------------------------
    //Přidání zobrazení souřadnic myši
       JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); //lepší zarovnání
       JLabel coordsLabel = new JLabel("x: -, y: -");
       statusPanel.add(coordsLabel);
       add(statusPanel, BorderLayout.SOUTH);


    //JLabel coordsLabel = new JLabel("x: -, y: -");
    //add(coordsLabel, BorderLayout.SOUTH); //původní umístění

        drawPanel.setCoordsLabel(coordsLabel);
//--------------------------------------------------------------------

    }
}
