package week03.example04;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginEventExample {
    private JFrame frame;
    private JTextField txtId;
    private JPasswordField txtPassword;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new LoginEventExample().frame.setVisible(true));
    }

    public LoginEventExample() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("예제4 - 로그인");
        frame.setBounds(100, 100, 430, 280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblId = new JLabel("아이디");
        lblId.setBounds(70, 55, 60, 30);
        frame.getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setBounds(145, 55, 170, 30);
        frame.getContentPane().add(txtId);

        JLabel lblPw = new JLabel("비밀번호");
        lblPw.setBounds(70, 100, 70, 30);
        frame.getContentPane().add(lblPw);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(145, 100, 170, 30);
        frame.getContentPane().add(txtPassword);

        JButton btnLogin = new JButton("로그인");
        btnLogin.setBounds(155, 160, 100, 35);
        frame.getContentPane().add(btnLogin);

        btnLogin.addActionListener(e -> login());
        txtPassword.addActionListener(e -> login());
    }

    private void login() {
        String id = txtId.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "아이디와 비밀번호를 모두 입력하세요.",
                    "입력 확인",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (id.equals("java") && password.equals("1234")) {
            JOptionPane.showMessageDialog(frame, "로그인 성공");
        } else {
            JOptionPane.showMessageDialog(frame,
                    "아이디 또는 비밀번호가 올바르지 않습니다.",
                    "로그인 실패",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
