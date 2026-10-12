package week04;

import java.awt.*;
import javax.swing.*;

public class GridBagLayoutDemo extends JFrame {

    public GridBagLayoutDemo() {
        setTitle("GridBagLayout Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField id = new JTextField(18);
        JTextField name = new JTextField(18);
        JComboBox<String> grade = new JComboBox<>(new String[]{"일반", "VIP", "VVIP"});
        JTextArea note = new JTextArea(5, 24);

        addRow(form, gbc, 0, "회원번호", id);
        addRow(form, gbc, 1, "이름", name);
        addRow(form, gbc, 2, "등급", grade);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        form.add(new JLabel("메모"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        form.add(new JScrollPane(note), gbc);

        add(form);
        setSize(560, 390);
        setLocationRelativeTo(null);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc,
                        int row, String text, Component component) {
        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(text), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new GridBagLayoutDemo().setVisible(true)
        );
    }
}
