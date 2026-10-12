package week04;

import java.awt.*;
import javax.swing.*;

public class SwingComponentsDemo extends JFrame {

    public SwingComponentsDemo() {
        setTitle("Swing Components Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        setContentPane(content);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField name = new JTextField(22);
        JPasswordField pw = new JPasswordField(22);

        JRadioButton male = new JRadioButton("남");
        JRadioButton female = new JRadioButton("여");
        ButtonGroup gender = new ButtonGroup();
        gender.add(male);
        gender.add(female);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderPanel.add(male);
        genderPanel.add(female);

        JComboBox<String> grade =
                new JComboBox<>(new String[] {"일반", "VIP", "VVIP"});

        JSpinner age =
                new JSpinner(new SpinnerNumberModel(20, 10, 100, 1));

        JCheckBox agree = new JCheckBox("이메일 수신 동의");

        JList<String> interests =
                new JList<>(new String[] {"Java", "DB", "Web", "AI"});
        interests.setVisibleRowCount(4);
        interests.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane interestScroll = new JScrollPane(interests);
        interestScroll.setPreferredSize(new Dimension(260, 85));

        int row = 0;
        addFormRow(form, gbc, row++, "이름", name);
        addFormRow(form, gbc, row++, "비밀번호", pw);
        addFormRow(form, gbc, row++, "성별", genderPanel);
        addFormRow(form, gbc, row++, "등급", grade);
        addFormRow(form, gbc, row++, "나이", age);
        addFormRow(form, gbc, row++, "수신", agree);
        addFormRow(form, gbc, row++, "관심분야", interestScroll);

        // Keep the form near the top even when the window becomes taller.
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        form.add(Box.createVerticalGlue(), gbc);

        JButton show = new JButton("입력값 확인");
        show.setPreferredSize(new Dimension(140, 34));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.add(show);

        show.addActionListener(e -> {
            String selectedGender =
                    male.isSelected() ? "남"
                    : female.isSelected() ? "여"
                    : "선택 안 함";

            JOptionPane.showMessageDialog(
                    this,
                    "이름: " + name.getText()
                    + "\n성별: " + selectedGender
                    + "\n등급: " + grade.getSelectedItem()
                    + "\n나이: " + age.getValue()
                    + "\n이메일 수신 동의: " + (agree.isSelected() ? "예" : "아니오")
                    + "\n관심분야: " + interests.getSelectedValuesList()
            );
        });

        content.add(form, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(520, 480));
        setSize(620, 520);
        setLocationRelativeTo(null);
    }

    private void addFormRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String labelText,
            Component component) {

        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        form.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(component, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new SwingComponentsDemo().setVisible(true));
    }
}
