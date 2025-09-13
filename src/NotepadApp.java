//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class NotepadApp extends JFrame {

    private final JTextArea textArea;
    private final JFileChooser fileChooser;

    public NotepadApp() {
        // Frame setup
        setTitle("Simple Notepad");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Text area with scroll
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // File chooser
        fileChooser = new JFileChooser();

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createFormatMenu());
        menuBar.add(createHelpMenu());

        setJMenuBar(menuBar);
    }

    // ===== Menus =====
    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(_ -> openFile());

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(_ -> saveFile());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(_ -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        return fileMenu;
    }

    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");

        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(_ -> textArea.cut());

        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(_ -> textArea.copy());

        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(_ -> textArea.paste());

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        return editMenu;
    }

    private JMenu createFormatMenu() {
        JMenu formatMenu = new JMenu("Format");

        JMenuItem fontItem = new JMenuItem("Font...");
        fontItem.addActionListener(_ -> chooseFont());

        JMenuItem colorItem = new JMenuItem("Text Color...");
        colorItem.addActionListener(_ -> chooseColor());

        formatMenu.add(fontItem);
        formatMenu.add(colorItem);

        return formatMenu;
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(_ -> JOptionPane.showMessageDialog(
                this,
                "Simple Notepad\nCreated by [Rasadhika s16723]",
                "About",
                JOptionPane.INFORMATION_MESSAGE
        ));

        helpMenu.add(aboutItem);
        return helpMenu;
    }

    // ===== File Methods =====
    private void openFile() {
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                textArea.read(reader, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error opening file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                textArea.write(writer);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===== Format Methods =====
    private void chooseFont() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String selectedFont = (String) JOptionPane.showInputDialog(
                this,
                "Choose Font:",
                "Font Chooser",
                JOptionPane.PLAIN_MESSAGE,
                null,
                fonts,
                textArea.getFont().getFamily()
        );

        if (selectedFont != null) {
            textArea.setFont(new Font(selectedFont, Font.PLAIN, textArea.getFont().getSize()));
        }
    }

    private void chooseColor() {
        Color color = JColorChooser.showDialog(this, "Choose Text Color", textArea.getForeground());
        if (color != null) {
            textArea.setForeground(color);
        }
    }

    // ===== Main =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NotepadApp().setVisible(true));
    }
}
