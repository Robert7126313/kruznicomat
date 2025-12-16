import javax.swing.*;
import java.awt.*;

public class Malovani extends JFrame {
//    protected final JTextField diameterField;
    private final DrawPanel drawPanel;
    private final JButton clearButton;

    protected final JSpinner sizeSpinner;



    public Malovani(){
        super("Kružnicomat - klikni a kresli");
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Průměr v px: "));
//        diameterField = new JTextField("50",5);
//        topPanel.add(diameterField); //odstraněno, nahrazeno spinnerem

        SpinnerNumberModel sizeModel = new SpinnerNumberModel(50, 1, 1000, 1);
        sizeSpinner = new JSpinner(sizeModel);
        topPanel.add(sizeSpinner);


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

        topPanel.add(new JLabel("Velikost v px: "));
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
//-------------------------------Přidání zobrazení souřadnic myši-------------------------------------

        //vytvornění panelu se souřadnicemi
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8)); //padding panelu

        //labela se souřadnicemi
        JLabel coordsLabel = new JLabel("x: -, y: -");
        statusPanel.add(coordsLabel);

        //přidání panelu do okna
        add(statusPanel, BorderLayout.SOUTH);
        drawPanel.setCoordsLabel(coordsLabel);

//--------------------------------------------------------------------
// Přidání nastavení kroku mřížky a zobrazení mřížky
        SpinnerNumberModel gridModel = new SpinnerNumberModel(10, 1, 200, 1);
        JSpinner gridSpinner = new JSpinner(gridModel);

        // Snap to grid
        JCheckBox snapCheck = new JCheckBox("Snap");
        topPanel.add(snapCheck);

        topPanel.add(new JLabel("Krok mřížky:"));
        topPanel.add(gridSpinner);

        gridSpinner.addChangeListener(e ->
                drawPanel.setGridStep((Integer) gridSpinner.getValue())
        );

        // Přidání mřížky-----
        JCheckBox gridCheck = new JCheckBox("Mřížka");
        topPanel.add(gridCheck);

        gridCheck.addActionListener(e -> {
            boolean on = gridCheck.isSelected();
            drawPanel.setShowGrid(on);

            gridSpinner.setEnabled(on);
            snapCheck.setEnabled(on);

            if (!on) {
                snapCheck.setSelected(false);
                drawPanel.setSnapToGrid(false);
            }
        });


        setVisible(true);

    //--------------------------------------------------------------------
    // inicializace stavů mřížky a snapu
        // výchozí stav: mřížka vypnutá


        // výchozí stav: snap jde použít jen když je grid zapnutý
        snapCheck.setEnabled(gridCheck.isSelected());

        // když uživatel klikne na Snap
        snapCheck.addActionListener(e ->
                drawPanel.setSnapToGrid(snapCheck.isSelected())
        );

    //--------------------------------------------------------------------

        JButton undoButton = new JButton("Undo");
        topPanel.add(undoButton);
        undoButton.addActionListener(e -> drawPanel.undo());

        //----------------------------------------------------

        // ------------------- Přidání ukládání do PNG souboru -----------------------
        JButton saveButton = new JButton("Uložit PNG");
        topPanel.add(saveButton);

        saveButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Uložit jako PNG");
            chooser.setSelectedFile(new java.io.File("kruznicomat.png"));

            int result = chooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                java.io.File file = chooser.getSelectedFile();
                drawPanel.saveToPng(file);
            }
        });
//----------------------------------------------------

    }
}
