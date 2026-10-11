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

        /*
         * 전체 창과 실제 입력 영역 사이에 충분한 좌우 여백을 둔다.
         * 바깥 content는 화면 전체를 사용하고,
         * mainPanel은 중앙에 배치하여 너무 좌우 끝까지 붙지 않도록 한다.
         */
        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(20, 36, 18, 36));
        setContentPane(content);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 12));
        mainPanel.setPreferredSize(new Dimension(540, 450));

        GridBagConstraints outer = new GridBagConstraints();
        outer.gridx = 0;
        outer.gridy = 0;
        outer.weightx = 1.0;
        outer.weighty = 1.0;
        outer.fill = GridBagConstraints.BOTH;
        outer.anchor = GridBagConstraints.CENTER;
        content.add(mainPanel, outer);

        // -------------------------------------------------
        // 1. 기본 정보 입력 영역
        // -------------------------------------------------
        JPanel form = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 4, 5, 4);
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
        introScroll.setPreferredSize(new Dimension(500, 235));
        introPanel.add(introScroll, BorderLayout.CENTER);

        // -------------------------------------------------
        // 3. 버튼 영역
        // -------------------------------------------------
        JButton save = new JButton("저장");
        JButton load = new JButton("불러오기");
        JButton reset = new JButton("초기화");

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 2));
        buttons.add(save);
        buttons.add(load);
        buttons.add(reset);

        JPanel south = new JPanel(new BorderLayout(0, 8));
        south.add(buttons, BorderLayout.NORTH);
        south.add(status, BorderLayout.SOUTH);

        // -------------------------------------------------
        // 4. 전체 화면 배치
        // -------------------------------------------------
        mainPanel.add(form, BorderLayout.NORTH);
        mainPanel.add(introPanel, BorderLayout.CENTER);
        mainPanel.add(south, BorderLayout.SOUTH);

        // -------------------------------------------------
        // 5. 이벤트 처리
        // -------------------------------------------------
        save.addActionListener(e -> saveProfile());
        load.addActionListener(e -> loadProfile());
        reset.addActionListener(e -> resetForm());

        pack();
        setMinimumSize(new Dimension(620, 520));
        setSize(680, 560);
        setLocationRelativeTo(null);
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            Component component) {

        gbc.gridy = row;

        // 라벨 영역: 필요한 폭만 사용
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(64, 24));
        panel.add(label, gbc);

        // 입력 영역: 남는 가로 공간만 자연스럽게 사용
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
