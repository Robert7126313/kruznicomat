import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Malovani extends JFrame {
    private final DrawPanel drawPanel;

    // Spinner na velikost (px) – používá se ve DrawPanel přes owner.sizeSpinner.getValue()
    protected final JSpinner sizeSpinner;

    public Malovani() {
        super("Kružnicomat - klikni a kresli");

        // ---------- Střed aplikace: kreslící panel ----------
        drawPanel = new DrawPanel();

        // ---------- HORNÍ LIŠTA: rozdělíme do 3 sekcí (vlevo / střed / vpravo) ----------
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // ---------- Velikost (spinner) ----------
        // (pozn.: "průměr" je zavádějící pro čtverec, proto "Velikost")
        SpinnerNumberModel sizeModel = new SpinnerNumberModel(50, 1, 1000, 1);
        sizeSpinner = new JSpinner(sizeModel);

        // Zúžení textového pole uvnitř spinneru (lepší UI)
        if (sizeSpinner.getEditor() instanceof JSpinner.NumberEditor ne) {
            ne.getTextField().setColumns(4);
        }

        // ---------- Výběr nástroje (JComboBox) ----------
        JComboBox<Tool> toolCombo = new JComboBox<>(Tool.values());
        toolCombo.setSelectedItem(Tool.CIRCLE);

        toolCombo.addActionListener(e -> {
            Tool selected = (Tool) toolCombo.getSelectedItem();
            if (selected != null) drawPanel.setTool(selected);
        });

        // ---------- Levý panel: nástroj + velikost ----------
        leftPanel.add(new JLabel("Nástroj:"));
        leftPanel.add(toolCombo);

        leftPanel.add(new JLabel("Velikost (px):"));
        leftPanel.add(sizeSpinner);

        // ---------- Barva: tlačítko + náhled ----------
        JButton colorButton = new JButton("Změnit barvu");

        JPanel colorPreview = new JPanel();
        colorPreview.setPreferredSize(new Dimension(18, 18));
        colorPreview.setBackground(Color.BLACK);
        colorPreview.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

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

        // ---------- Střední panel: barva ----------
        centerPanel.add(colorButton);
        centerPanel.add(colorPreview);

        // ---------- Nastavení mřížky ----------
        SpinnerNumberModel gridModel = new SpinnerNumberModel(10, 1, 200, 1);
        JSpinner gridSpinner = new JSpinner(gridModel);

        // Zúžení textového pole uvnitř spinneru (lepší UI)
        if (gridSpinner.getEditor() instanceof JSpinner.NumberEditor ne2) {
            ne2.getTextField().setColumns(3);
        }

        // Snap to grid
        JCheckBox snapCheck = new JCheckBox("Snap");

        // Přidání mřížky
        JCheckBox gridCheck = new JCheckBox("Mřížka");

        // výchozí stavy: mřížka vypnutá => gridSpinner a snap disabled
        gridSpinner.setEnabled(false);
        snapCheck.setEnabled(false);

        gridSpinner.addChangeListener(e ->
                drawPanel.setGridStep((Integer) gridSpinner.getValue())
        );

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

        // když uživatel klikne na Snap
        snapCheck.addActionListener(e ->
                drawPanel.setSnapToGrid(snapCheck.isSelected())
        );

        // ---------- Akční tlačítka ----------
        JButton undoButton = new JButton("Undo");
        JButton saveButton = new JButton("Uložit PNG");
        JButton clearButton = new JButton("Vymazat");

        undoButton.addActionListener(e -> drawPanel.undo());

        clearButton.addActionListener(e -> drawPanel.clear());

        // ------------------- Přidání ukládání do PNG souboru -----------------------
        saveButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Uložit jako PNG");
            chooser.setSelectedFile(new File("kruznicomat.png"));

            int result = chooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                drawPanel.saveToPng(file);
            }
        });

        // ---------- Pravý panel: mřížka + akce ----------
        rightPanel.add(gridCheck);

        rightPanel.add(new JLabel("Krok:"));
        rightPanel.add(gridSpinner);

        rightPanel.add(snapCheck);

        rightPanel.add(undoButton);
        rightPanel.add(saveButton);
        rightPanel.add(clearButton);



        // ---------- Layout okna ----------
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);

        // ------------------------------- Přidání zobrazení souřadnic myši -------------------------------------
        // vytvoření panelu se souřadnicemi
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8)); // padding panelu

        // labela se souřadnicemi
        JLabel coordsLabel = new JLabel("x: -, y: -");
        statusPanel.add(coordsLabel);

        // přidání panelu do okna
        add(statusPanel, BorderLayout.SOUTH);
        drawPanel.setCoordsLabel(coordsLabel);
        // ------------------------------------------------------------------------------------------------------

        // ---------- Okno ----------
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
