package week04;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class ProfileFileManager extends JFrame {

    private final JTextField idField = new JTextField(20);
    private final JTextField nameField = new JTextField(20);
    private final JComboBox<String> majorBox =
            new JComboBox<>(new String[] {
                    "컴퓨터소프트웨어", "AI", "정보통신"
            });

    private final JTextArea introArea = new JTextArea(10, 40);
    private final JLabel status = new JLabel("프로필을 작성하세요.");

    public ProfileFileManager() {
        setTitle("학생 프로필 파일 관리");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBorder(BorderFactory.createEmptyBorder(18, 24, 16, 24));
        setContentPane(content);

        // -------------------------------------------------
        // 1. 기본 정보 입력 영역
        //    GridLayout 대신 GridBagLayout을 사용하여
        //    라벨은 필요한 폭만 사용하고 입력 필드는 확장되도록 구성
        // -------------------------------------------------
        JPanel form = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        addFormRow(form, gbc, 0, "학번", idField);
        addFormRow(form, gbc, 1, "이름", nameField);
        addFormRow(form, gbc, 2, "학과", majorBox);

        // -------------------------------------------------
        // 2. 자기소개 영역
        // -------------------------------------------------
        JPanel introPanel = new JPanel(new BorderLayout(0, 6));
        introPanel.add(new JLabel("자기소개"), BorderLayout.NORTH);

        introArea.setLineWrap(true);
        introArea.setWrapStyleWord(true);

        JScrollPane introScroll = new JScrollPane(introArea);
        introScroll.setPreferredSize(new Dimension(520, 230));
        introPanel.add(introScroll, BorderLayout.CENTER);

        // -------------------------------------------------
        // 3. 버튼 영역
        // -------------------------------------------------
        JButton save = new JButton("저장");
        JButton load = new JButton("불러오기");
        JButton reset = new JButton("초기화");

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        buttons.add(save);
        buttons.add(load);
        buttons.add(reset);

        JPanel south = new JPanel(new BorderLayout(0, 8));
        south.add(buttons, BorderLayout.NORTH);
        south.add(status, BorderLayout.SOUTH);

        // -------------------------------------------------
        // 4. 전체 화면 배치
        // -------------------------------------------------
        content.add(form, BorderLayout.NORTH);
        content.add(introPanel, BorderLayout.CENTER);
        content.add(south, BorderLayout.SOUTH);

        // -------------------------------------------------
        // 5. 이벤트 처리
        // -------------------------------------------------
        save.addActionListener(e -> saveProfile());
        load.addActionListener(e -> loadProfile());
        reset.addActionListener(e -> resetForm());

        pack();
        setMinimumSize(new Dimension(560, 500));
        setSize(640, 540);
        setLocationRelativeTo(null);
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            Component component) {

        gbc.gridy = row;

        // 라벨 영역: 필요한 크기만 사용
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(70, 24));
        panel.add(label, gbc);

        // 입력 영역: 창 크기에 따라 가로로 확장
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(component, gbc);
    }

    private void saveProfile() {
        if (idField.getText().isBlank() || nameField.getText().isBlank()) {
            JOptionPane.showMessageDialog(
                    this,
                    "학번과 이름은 필수입니다."
            );
            return;
        }

        File dir = new File("data");
        dir.mkdirs();

        File file =
                new File(dir, idField.getText().trim() + ".txt");

        try (BufferedWriter bw =
                new BufferedWriter(new FileWriter(file))) {

            bw.write(idField.getText().trim());
            bw.newLine();

            bw.write(nameField.getText().trim());
            bw.newLine();

            bw.write((String) majorBox.getSelectedItem());
            bw.newLine();

            bw.write(introArea.getText());

            status.setText("저장 완료: " + file.getName());

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadProfile() {
        JFileChooser chooser =
                new JFileChooser(new File("data"));

        if (chooser.showOpenDialog(this)
                != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try (BufferedReader br =
                new BufferedReader(
                        new FileReader(chooser.getSelectedFile()))) {

            idField.setText(br.readLine());
            nameField.setText(br.readLine());
            majorBox.setSelectedItem(br.readLine());

            introArea.setText("");

            String line;
            while ((line = br.readLine()) != null) {
                introArea.append(line + "\n");
            }

            status.setText(
                    "불러오기 완료: "
                    + chooser.getSelectedFile().getName()
            );

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
        idField.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new ProfileFileManager().setVisible(true)
        );
    }
}
