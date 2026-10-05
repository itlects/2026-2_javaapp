package week04;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class ProfileFileManager extends JFrame {
    private final JTextField idField = new JTextField(14);
    private final JTextField nameField = new JTextField(14);
    private final JComboBox<String> majorBox = new JComboBox<>(new String[]{"컴퓨터소프트웨어", "AI", "정보통신"});
    private final JTextArea introArea = new JTextArea(8, 30);
    private final JLabel status = new JLabel("프로필을 작성하세요.");

    public ProfileFileManager() {
        setTitle("학생 프로필 파일 관리");
        setSize(560, 470);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("학번")); form.add(idField);
        form.add(new JLabel("이름")); form.add(nameField);
        form.add(new JLabel("학과")); form.add(majorBox);

        JButton save = new JButton("저장");
        JButton load = new JButton("불러오기");
        JButton reset = new JButton("초기화");
        JPanel buttons = new JPanel();
        buttons.add(save); buttons.add(load); buttons.add(reset);

        JPanel north = new JPanel(new BorderLayout());
        north.add(form, BorderLayout.CENTER);
        north.add(new JLabel("자기소개"), BorderLayout.SOUTH);

        add(north, BorderLayout.NORTH);
        add(new JScrollPane(introArea), BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        south.add(buttons, BorderLayout.NORTH);
        south.add(status, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        save.addActionListener(e -> saveProfile());
        load.addActionListener(e -> loadProfile());
        reset.addActionListener(e -> resetForm());
    }

    private void saveProfile() {
        if (idField.getText().isBlank() || nameField.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "학번과 이름은 필수입니다.");
            return;
        }
        File dir = new File("data");
        dir.mkdirs();
        File file = new File(dir, idField.getText().trim() + ".txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(idField.getText().trim()); bw.newLine();
            bw.write(nameField.getText().trim()); bw.newLine();
            bw.write((String) majorBox.getSelectedItem()); bw.newLine();
            bw.write(introArea.getText());
            status.setText("저장 완료: " + file.getName());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadProfile() {
        JFileChooser chooser = new JFileChooser(new File("data"));
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try (BufferedReader br = new BufferedReader(new FileReader(chooser.getSelectedFile()))) {
            idField.setText(br.readLine());
            nameField.setText(br.readLine());
            majorBox.setSelectedItem(br.readLine());
            introArea.setText("");
            String line;
            while ((line = br.readLine()) != null) introArea.append(line + "\n");
            status.setText("불러오기 완료");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void resetForm() {
        idField.setText("");
        nameField.setText("");
        majorBox.setSelectedIndex(0);
        introArea.setText("");
        status.setText("초기화 완료");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProfileFileManager().setVisible(true));
    }
}
