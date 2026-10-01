package week03.practice01;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPractice {
    private JFrame frame;
    private JTextField txtId;
    private JPasswordField txtPassword;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new LoginPractice().frame.setVisible(true));
    }

    public LoginPractice() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("실습1 - 로그인");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblId = new JLabel("아이디");
        lblId.setBounds(75, 55, 70, 30);
        frame.getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setBounds(155, 55, 180, 30);
        frame.getContentPane().add(txtId);

        JLabel lblPw = new JLabel("비밀번호");
        lblPw.setBounds(75, 100, 70, 30);
        frame.getContentPane().add(lblPw);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(155, 100, 180, 30);
        frame.getContentPane().add(txtPassword);

        JButton btnLogin = new JButton("로그인");
        btnLogin.setBounds(105, 165, 100, 35);
        frame.getContentPane().add(btnLogin);

        JButton btnCancel = new JButton("취소");
        btnCancel.setBounds(225, 165, 100, 35);
        frame.getContentPane().add(btnCancel);

        btnLogin.addActionListener(e -> login());
        txtPassword.addActionListener(e -> login());
        btnCancel.addActionListener(e -> clearForm());
    }

    private void login() {
        String id = txtId.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "아이디를 입력하세요.");
            txtId.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "비밀번호를 입력하세요.");
            txtPassword.requestFocus();
            return;
        }

        if ("java".equals(id) && "1234".equals(password)) {
            JOptionPane.showMessageDialog(frame, "로그인 성공");
        } else {
            JOptionPane.showMessageDialog(frame,
                    "아이디 또는 비밀번호가 올바르지 않습니다.",
                    "로그인 실패",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtPassword.setText("");
        txtId.requestFocus();
    }
}
