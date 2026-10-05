package week04;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class StudentMemo extends JFrame {
    private final JTextField nameField = new JTextField(15);
    private final JTextArea memoArea = new JTextArea(12, 35);
    private final JLabel resultLabel = new JLabel("학생 이름과 메모를 입력하세요.");

    public StudentMemo() {
        setTitle("학생 메모 저장");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 400);
        setLocationRelativeTo(null);

        JPanel north = new JPanel();
        north.add(new JLabel("학생 이름"));
        north.add(nameField);

        JButton save = new JButton("메모 저장");
        JButton load = new JButton("메모 불러오기");
        JPanel south = new JPanel();
        south.add(save);
        south.add(load);
        south.add(resultLabel);

        add(north, BorderLayout.NORTH);
        add(new JScrollPane(memoArea), BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        save.addActionListener(e -> saveMemo());
        load.addActionListener(e -> loadMemo());
    }

    private File memoFile() {
        return new File("data/" + nameField.getText().trim() + ".txt");
    }

    private void saveMemo() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "학생 이름을 입력하세요.");
            return;
        }
        File file = memoFile();
        file.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(memoArea.getText());
            resultLabel.setText("저장 완료");
        } catch (IOException e) {
            resultLabel.setText("저장 실패");
        }
    }

    private void loadMemo() {
        File file = memoFile();
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "저장된 메모가 없습니다.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            memoArea.setText("");
            String line;
            while ((line = br.readLine()) != null) memoArea.append(line + "\n");
            resultLabel.setText("불러오기 완료");
        } catch (IOException e) {
            resultLabel.setText("불러오기 실패");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentMemo().setVisible(true));
    }
}
