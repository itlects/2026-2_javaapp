package week04;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class SimpleLogViewer extends JFrame {
    private final JTextArea area = new JTextArea();

    public SimpleLogViewer() {
        setTitle("Simple Log Viewer");
        setSize(650, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton open = new JButton("로그 파일 열기");
        add(open, BorderLayout.NORTH);
        add(new JScrollPane(area), BorderLayout.CENTER);

        open.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
            try (BufferedReader br = new BufferedReader(new FileReader(chooser.getSelectedFile()))) {
                area.setText("");
                String line;
                while ((line = br.readLine()) != null) {
                    area.append(line + System.lineSeparator());
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleLogViewer().setVisible(true));
    }
}
