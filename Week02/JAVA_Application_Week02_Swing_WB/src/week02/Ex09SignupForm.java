package week02;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Ex09SignupForm {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Ex09SignupForm::createAndShowGui);
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("회원가입");
        frame.setLayout(new BorderLayout(10, 10));
        JTextField idField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField passwordConfirmField = new JPasswordField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JComboBox<String> deptCombo = new JComboBox<>(
            new String[] {"컴퓨터소프트웨어과", "AI융합과", "전자과"});

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 5, 25));
        addRow(formPanel, "아이디", idField);
        addRow(formPanel, "비밀번호", passwordField);
        addRow(formPanel, "비밀번호 확인", passwordConfirmField);
        addRow(formPanel, "이름", nameField);
        addRow(formPanel, "학과", deptCombo);
        addRow(formPanel, "이메일", emailField);

        JCheckBox agreeCheck = new JCheckBox("개인정보 수집 및 이용에 동의합니다.");
        JButton signupButton = new JButton("가입");
        JButton resetButton = new JButton("초기화");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(signupButton);
        buttonPanel.add(resetButton);
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 15, 25));
        southPanel.add(agreeCheck, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        signupButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String password = new String(passwordField.getPassword());
            String passwordConfirm = new String(passwordConfirmField.getPassword());
            String name = nameField.getText().trim();
            if (id.isEmpty() || password.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "아이디, 비밀번호, 이름은 필수입니다.");
            } else if (!password.equals(passwordConfirm)) {
                JOptionPane.showMessageDialog(frame, "비밀번호가 서로 다릅니다.");
            } else if (!agreeCheck.isSelected()) {
                JOptionPane.showMessageDialog(frame, "개인정보 수집에 동의해 주세요.");
            } else {
                JOptionPane.showMessageDialog(frame,
                    name + "님, 회원가입 화면 입력이 완료되었습니다.");
            }
        });

        resetButton.addActionListener(e -> {
            idField.setText("");
            passwordField.setText("");
            passwordConfirmField.setText("");
            nameField.setText("");
            emailField.setText("");
            deptCombo.setSelectedIndex(0);
            agreeCheck.setSelected(false);
            idField.requestFocus();
        });

        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);
        frame.setSize(540, 440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void addRow(JPanel panel, String label, java.awt.Component component) {
        panel.add(new JLabel(label));
        panel.add(component);
    }
}
