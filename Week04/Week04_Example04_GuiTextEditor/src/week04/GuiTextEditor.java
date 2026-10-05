package week04;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class GuiTextEditor extends JFrame {
    private final JTextArea textArea = new JTextArea();
    private final JLabel status = new JLabel("파일을 선택하세요.");

    public GuiTextEditor() {
        setTitle("간단한 텍스트 편집기");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton openButton = new JButton("열기");
        JButton saveButton = new JButton("저장");
        JButton clearButton = new JButton("지우기");

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(openButton);
        top.add(saveButton);
        top.add(clearButton);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        openButton.addActionListener(e -> openFile());
        saveButton.addActionListener(e -> saveFile());
        clearButton.addActionListener(e -> textArea.setText(""));
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            textArea.setText("");
            String line;
            while ((line = br.readLine()) != null) {
                textArea.append(line + System.lineSeparator());
            }
            status.setText("열기 완료: " + file.getName());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(textArea.getText());
            status.setText("저장 완료: " + file.getName());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuiTextEditor().setVisible(true));
    }
}
