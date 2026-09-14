package week02;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Ex09LoginValidation extends JFrame {
    private final JTextField idField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);

    public Ex09LoginValidation() {
        super("로그인 검증");
        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(25, 30, 15, 30));
        form.add(new JLabel("아이디"));
        form.add(idField);
        form.add(new JLabel("비밀번호"));
        form.add(passwordField);

        JButton loginButton = new JButton("로그인");
        loginButton.addActionListener(e -> login());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);

        add(form, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(loginButton);
        setSize(420, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void login() {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (id.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 모두 입력하세요.", "입력 확인", JOptionPane.WARNING_MESSAGE);
        } else if (id.equals("java") && password.equals("1234")) {
            JOptionPane.showMessageDialog(this, "로그인 성공");
        } else {
            JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호가 올바르지 않습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            passwordField.requestFocus();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ex09LoginValidation().setVisible(true));
    }
}
